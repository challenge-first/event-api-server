package com.example.eventapiserver.repository;

import com.example.eventapiserver.controller.EventRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean add(Long eventId, Long memberId, long time) {
        return redisTemplate.opsForZSet()
                .add(String.valueOf(eventId), String.valueOf(memberId), time);
    }

    public Set<String> zPopMin(String eventId, long count) {
        // Set 내에서 순서 보장이 안된다.
        // ex. 1~5등까지는 들고오나 순서가 보장이 안됌
        Set<ZSetOperations.TypedTuple<String>> result = redisTemplate.opsForZSet().popMin(eventId, count);
        if (result != null) {
            Set<String> castedResult = result.stream()
                    .map(typedTuple -> typedTuple.getValue())
                    .collect(Collectors.toSet());
            return castedResult;
        }
        return null;
    }

    public Set<String> zRange(String eventId, long count) {
        return redisTemplate.opsForZSet().range(eventId,0,count);
    }
}
