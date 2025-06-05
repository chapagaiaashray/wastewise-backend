package com.aashray.backend.config;

import com.aashray.backend.model.BinEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, BinEntity> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, BinEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
