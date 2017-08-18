package com.xdzl.common.service.redis.impl;

import com.xdzl.common.service.redis.RedisService;
import com.xdzl.common.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired//通过Spring依赖注入 redisTemlpate
    private RedisTemplate<String, String> redisTemplate;

    //往Redis设置值
    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    //从Redis获取值
    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }
    @Override
    public Map<String,String> getMap(String key){
        HashOperations<String, String, String> operations = this.redisTemplate.opsForHash();
        Map<String,String>entries = operations.entries(key);
        return  entries;
    }
    @Override
    public void putMap(String key,Map<String,String> map){
        HashOperations<String, Object, Object> operations = this.redisTemplate.opsForHash();
        operations.putAll(key,map);
    }
    @Override
    public String getHashValue(String key,String hValue){
        HashOperations<String, String, String> operations = this.redisTemplate.opsForHash();
        String o = operations.get(key, hValue);
        return o;
    }

    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    //往Redis设置list类型值
    public <T> boolean setList(String key, List<T> list) {
        String value = JSONUtil.toJson(list);
        return set(key, value);
    }

    //从Redis获取list类型值
    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            List<T> list = JSONUtil.toList(json, clz);
            return list;
        }
        return null;
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public boolean delKey(final String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    //往list头添加元素
    public long lpush(final String key, Object obj) {
        final String value = JSONUtil.toJson(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    //往list尾添加元素
    public long rpush(final String key, Object obj) {
        final String value = JSONUtil.toJson(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    //往list头删除元素
    public String lpop(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res = connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
        });
        return result;
    }
}
