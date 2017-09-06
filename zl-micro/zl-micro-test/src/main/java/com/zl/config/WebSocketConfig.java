package com.zl.config;

import com.zl.handler.MyWebSocketHandler;
import com.zl.interceptor.MyHandShakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.*;

/**
 * @Description: 配置WebSocket
 * @Author: CaoXiaoLong create on 2017/8/28 16:06.
 */
@Configuration
@EnableWebSocketMessageBroker //1
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer implements WebSocketConfigurer{

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //my-websocket 就是websocket的端点，客户端需要注册这个端点进行链接，withSockJS允许客户端利用sockjs进行浏览器兼容性处理
        registry.addEndpoint("/my-websocket").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); //设置服务器广播消息的基础路径
        config.setApplicationDestinationPrefixes("/app");  //设置客户端订阅消息的基础路径
        config.setPathMatcher(new AntPathMatcher("."));    //可以已“.”来分割路径，看看类级别的@messageMapping和方法级别的@messageMapping
    }

    /**
     * 处理器和拦截器注册到spring websocket中
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/my-websocket")//添加一个处理器还有定义处理器的处理路径
                .addInterceptors(new MyHandShakeInterceptor())
                .withSockJS();
    }

    /*(1)@EnableWebSocketMessageBroker注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，
         这时候控制器（controller）开始支持@MessageMapping,就像是使用@requestMapping一样。*/
}
