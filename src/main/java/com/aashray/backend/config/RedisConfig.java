package com.aashray.backend.config;

import com.aashray.backend.model.BinEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // This tries to connect using env vars or default localhost if Redis is set up
        return new LettuceConnectionFactory();
    }

    @Bean
    @Primary
    public RedisTemplate<String, BinEntity> redisTemplate() {
        RedisTemplate<String, BinEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
