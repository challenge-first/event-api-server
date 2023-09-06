package com.example.eventapiserver.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean add(Long eventId, Long memberId, long time) {
        return redisTemplate.opsForZSet()
                .add(String.valueOf(eventId), String.valueOf(memberId), time);
    }
}
