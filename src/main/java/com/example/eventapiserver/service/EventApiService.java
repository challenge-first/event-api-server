package com.example.eventapiserver.service;

import com.example.eventapiserver.adapter.RequestCouponMessage;
import com.example.eventapiserver.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventApiService {

    private final RedisRepository redisRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void addQueue(Long eventId, Long memberId, long now) {
//        log.info("Service 접근 - {} [{}]", memberId, now);
        redisRepository.add(eventId, memberId, now);
//        log.info("대기열 추가 - {} [{}]", memberId, now);
    }

    public void getQueue(Long eventId, Long memberId) {
        log.info("getQueue Running");
    }

    public void requestCreateCoupons(Long eventId) throws JsonProcessingException {
        Set<String> strings = redisRepository.zPopMin(String.valueOf(eventId), 1000L);
        for (String string : strings) {
            log.info(string);
            RequestCouponMessage requestCouponMessage = new RequestCouponMessage(eventId, Long.parseLong(string));
            kafkaTemplate.send("coupon-topic",objectMapper.writeValueAsString(requestCouponMessage));
        }
    }
}
