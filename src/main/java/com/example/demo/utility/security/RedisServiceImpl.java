package com.example.demo.utility.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    final private StringRedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        try {
            Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().ping();
            System.out.println("Successfully connected to Redis");
        } catch (Exception e) {
            System.err.println("Failed to connect to Redis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setKeyAndValue(String token, Long memberId) {
        String memberIdString = String.valueOf(memberId);
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(token, memberIdString, Duration.ofMinutes(60));
        System.out.println("token : " + token);
        System.out.println("memberId : " + memberId);
        System.out.println("memberIdString : " + memberIdString);
    }

    @Override
    public Long getValueByKey(String token) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String tempMemberId = value.get(token);
        Long memberId;

        if (tempMemberId == null) { memberId = null; }
        else { memberId = Long.parseLong(tempMemberId); }

        return memberId;
    }

    @Override
    public void deleteByKey(String token) {
        redisTemplate.delete(token);

        System.out.println("Deleted!");
    }

    public boolean isRefreshTokenExists(String token) {

        return getValueByKey(token) != null;
    }
}