package com.example.eventapiserver.service;

import com.example.eventapiserver.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventApiService {

    private final RedisRepository redisRepository;

    public void addQueue(Long eventId, Long memberId, long now) {
        if (!redisRepository.add(eventId, memberId, now)) {
            throw new UserDuplicatedException("한번만 응모하실 수 있습니다.");
        }
    }
}
