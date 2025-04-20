package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redis;

    public Boolean tryLock(String key, String val, Duration expiry) {
        return redis.opsForValue().setIfAbsent(key, val, expiry);
    }

    public void put(String key, String val, Duration ttl) {
        redis.opsForValue().set(key, val, ttl);
    }

    public String get(String key) {
        return redis.opsForValue().get(key);
    }

    public Boolean delete(String key) {
        return redis.delete(key);
    }

    public Long incrementWithExpiry(String key, Duration ttl) {
        Long count = redis.opsForValue().increment(key);
        if (count != null && count == 1) {
            redis.expire(key, ttl);
        }
        return count;
    }
}