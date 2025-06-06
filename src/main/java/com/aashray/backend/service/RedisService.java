package com.aashray.backend.service;

import com.aashray.backend.model.BinEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@ConditionalOnProperty(name = "redis.enabled", havingValue = "true")
public class RedisService {

    private final RedisTemplate<String, BinEntity> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, BinEntity> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveBin(Long id, BinEntity bin) {
        redisTemplate.opsForValue().set("bin:" + id, bin, 10, TimeUnit.MINUTES);
    }

    public BinEntity getBin(Long id) {
        return redisTemplate.opsForValue().get("bin:" + id);
    }
}
