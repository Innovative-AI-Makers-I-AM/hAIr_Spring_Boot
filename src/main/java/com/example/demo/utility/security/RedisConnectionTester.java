package com.example.demo.utility.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionTester implements CommandLineRunner {

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisConnectionTester(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        try (var connection = redisConnectionFactory.getConnection()) {
            connection.ping();
            System.out.println("Successfully connected to Redis");
        } catch (Exception e) {
            System.err.println("Failed to connect to Redis: " + e.getMessage());
        }
    }
}
