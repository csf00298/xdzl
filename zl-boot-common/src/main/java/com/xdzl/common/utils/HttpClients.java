/**
 * 
 */
package com.xdzl.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Http请求构建工具
 * 
 * @author LiuJian
 *
 */
@SuppressWarnings("deprecation")
public class HttpClients {
    private final static Logger logger = LoggerFactory.getLogger(HttpClients.class);
    static class FullTrustStrategy implements TrustStrategy {
        @Override
        public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
            return true;
        }
    }

    /**
     * 返回默认的http Client
     * 
     * @see org.apache.http.impl.client.HttpClients#createDefault();
     * @return default httpClient
     * @see CloseableHttpClient
     */
    public static CloseableHttpClient createDefault() {
        return org.apache.http.impl.client.HttpClients.createDefault();
    }

    /**
     * 可以完成HTTP基本认证的请求的定制客户端 当确定制定请求需要进行基本认证时,请使用
     * {@link #createPreemptiveBasicAuthClient}
     * 来替代此方法,替代方法使用抢先式的认证信息填充,可以有效的减少客户端和服务器间不必要的往返请求.
     * 
     * @see org.apache.http.impl.client.HttpClients#createDefault();
     * @return default httpClient
     * @see CloseableHttpClient
     * 
     * @see #createPreemptiveBasicAuthClient
     */
    public static CloseableHttpClient createBasicAuthClient(String userName, String password) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();

        return client;
    }

    /**
     * 针对默认请求,使用Preemptive 的模式完成HTTP
     * Basic方式的认证填充,对于已知的需要认证的请求,请使用此客户端来避免不必要的握手过程,提升效率
     * 
     * 
     * @param userName
     *            HTTP Basic 认证代码
     * @param password
     *            HTTP Basic 认证密码
     * @param url
     *            至少需要提供 schema://host[:port] 三个要素,对于以上三要素相同的地址,可以共用一个client
     * @see org.apache.http.impl.client.HttpClients#createDefault();
     * @return default httpClient
     * @throws URISyntaxException
     * @see CloseableHttpClient
     */
    public static CloseableHttpClient createPreemptiveBasicAuthClient (String userName, String password, String url) {
        try {
            URI uri = new URI(url);
            HttpHost targetHost = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()), new UsernamePasswordCredentials(userName, password));

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(targetHost, basicAuth);

            // Add AuthCache to the execution context
            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);
            return new PreemptiveAuthentication(context);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("url is has syntax error.", e);
        }

    }

    /**
     * 
     * 实现之前版本的Preemptive Authentication 模式, 根据hc官方文档, hc自身没有提供Preemptive模式,
     * 作为一种替代方案,hc官方提供了一套缓存设置模式.
     * 
     * 此客户端仅支持依赖用户名密码的base64验证方式,用于在请求时,预置basic认证信息到request
     * header中,避免多次c/s交互,影响性能.
     * 
     * 
     * @see {@link ://hc.apache.org/httpcomponents-client-4.5.x/tutorial/html/authentication.html#d5e717}
     *
     */
    static class PreemptiveAuthentication extends CloseableHttpClient {
        /**
         * 
         * @param context
         */
        PreemptiveAuthentication(HttpContext context) {
            this.context = context;
        }

        CloseableHttpClient innerClient = createDefault();
        private HttpContext context;

        @Override
        public HttpParams getParams() {
            return innerClient.getParams();
        }

        @Override
        public ClientConnectionManager getConnectionManager() {
            return innerClient.getConnectionManager();
        }

        @Override
        public void close() throws IOException {
            innerClient.close();
        }

        @Override
        protected CloseableHttpResponse doExecute(org.apache.http.HttpHost target, HttpRequest request, HttpContext context)
                throws IOException, ClientProtocolException {
            return innerClient.execute(target, request, context);
        }

        @Override
        public CloseableHttpResponse execute(final HttpUriRequest request) throws IOException, ClientProtocolException {
            return innerClient.execute(request, context);
        }
    }

    /**
     * 返回完全信任的SSL Client
     * 
     * @see CloseableHttpClient
     * @see SSLContext
     * @return custom config full trusted sslContext http client
     */
    public static CloseableHttpClient createFullTrustedSSLClient() {
        TrustStrategy trustStrategy = new FullTrustStrategy();

        SSLContext sslContext;
        try {
            sslContext = SSLContextBuilder.create().loadTrustMaterial(trustStrategy).build();
            //create by hisensong 2016-7-14 15:30 begin
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(200);
            cm.setDefaultMaxPerRoute(20);
            //create by hisensong 2016-7-14 15:30 end
            CloseableHttpClient httpClient = org.apache.http.impl.client.HttpClients.custom().setSSLContext(sslContext).build();
            return httpClient;
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            // this won't happen
            return null;
        }
    }

    /**
     * 静默关闭httpClient
     * 
     * @param client
     *            待关闭的CloseableHttpClient
     */
    public static void closeQuietly(CloseableHttpClient client) {
        try {
            if (client != null)
                client.close();
        } catch (IOException e) {
            // just ignore
        }
    }

    /**
     * 模拟发送 get请求
     */
    public static String doGet(String url) throws IOException {
        CloseableHttpClient httpclient = org.apache.http.impl.client.HttpClients.createDefault();
        String result = "";
        try {
            //组装url请求参数
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            logger.info("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                }
            } finally {
                httpget.abort();
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
        } catch (Exception e) {
            logger.error("doGet request exception :",e);
           throw  e;
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("doGet close resource IOException :",e);
               throw e;
            }
        }

        return result;
    }

    public static String doPost(String url,String jsonStrData) throws IOException{
        String result = "";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        try{

            HttpEntity param = new StringEntity(jsonStrData, "UTF-8");
            post.setEntity(param);
            post.setHeader("Content-type", "application/json");
            response = client.execute(post);
            //int httpCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            result =  EntityUtils.toString(entity);
        }catch(Exception e){
            logger.error("doPost request exception :",e);
            throw  e;
        }finally {
            post.abort();
            if(response!=null){
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
            client.close();

        }
        return result;
    }

}
