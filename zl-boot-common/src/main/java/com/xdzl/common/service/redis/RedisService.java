package com.xdzl.common.service.redis;



import java.util.List;
import java.util.Map;

/**
 * Created by wfk on 2017/5/22.
 */
public interface RedisService {
    public boolean set(String key, String value);

    public String get(String key);

    public boolean expire(String key, long expire);

    public <T> boolean setList(String key, List<T> list);

    public <T> List<T> getList(String key, Class<T> clz);

    public long lpush(String key, Object obj);

    public long rpush(String key, Object obj);

    public String lpop(String key);


    public boolean delKey(String key);


    /**
     * 根据key获取Map
     * @param key
     * @return
     */
    public Map<String,String> getMap(String key);

    /**
     * 放入一个Map
     * @param key
     * @param map
     */
    public void putMap(String key, Map<String, String> map);

    /**
     * 根据key和hashKey获取hashValue
     * @param key
     * @param hValue
     * @return
     */

    public String getHashValue(String key, String hValue);

}
