package com.demo.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class ActivationServiceImpl implements ActivationService {

    @Autowired
    private RedisTemplate<String, BigDecimal> redisTemplate;

    @Override
    public void saveActivationCode(String username, BigDecimal activationCode) {
        redisTemplate.opsForValue().set(username, activationCode);
        redisTemplate.expire(username, 10, TimeUnit.MINUTES);
    }

    @Override
    public BigDecimal retrieveActivationCode(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @Override
    public boolean isActivationCodeValid(String username) {
        Long expireTime = redisTemplate.getExpire(username);
        return expireTime != null && expireTime > 0;
    }

    @Override
    public void forceDeleteActivationCode(String username) {
        redisTemplate.delete(username);
    }
}
