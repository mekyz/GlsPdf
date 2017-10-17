/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.util.redisUtil;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 
 * 文  件  名:RedisCache<br/>  
 * 文件描述:redis缓存<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2017年1月11日<br/>
 * 修改内容:<br/>
 */
public class RedisCache implements Cache{
    
    private Jedis redisClient = createReids();  
    /** The ReadWriteLock. */  
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();  
  
    private String id;  
  
    public RedisCache(final String id) {  
        if (id == null) {  
            throw new IllegalArgumentException("Cache instances require an ID");  
        }  
        this.id = id;  
    }  
  
    @Override  
    public String getId() {  
        return this.id;  
    }  
  
    @Override  
    public int getSize() {  
        // 获取 缓存key的大小  
        return Integer.valueOf(redisClient.dbSize().toString());  
    }  
  
    @Override  
    public void putObject(Object key, Object value) {  
        System.err.println("put key" + key);  
        redisClient.set(SerializeUtil.serialize(key.toString()),  
                SerializeUtil.serialize(value));  
    }  
  
    @Override  
    public Object getObject(Object key) {  
        System.out.println("get key");  
        Object value = SerializeUtil.unserialize(redisClient.get(SerializeUtil  
                .serialize(key.toString())));  
        return value;  
    }  
  
    @Override  
    public Object removeObject(Object key) {  
        System.err.println("remove key" + key);  
        return redisClient.expire(SerializeUtil.serialize(key.toString()), 0);  
    }  
  
    // 在进行 insert update delete 时执行  
    @Override  
    public void clear() {  
        System.err.println("clear all data");  
        redisClient.flushDB();  
    }  
  
    @Override  
    public ReadWriteLock getReadWriteLock() {  
        return readWriteLock;  
    }  
  
    protected static Jedis createReids() {  
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "10.27.196.122", 6379);  
        //JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);  
        return pool.getResource();  
    }  
}
