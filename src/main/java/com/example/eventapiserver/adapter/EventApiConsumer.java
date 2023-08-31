package com.example.eventapiserver.adapter;

import com.example.eventapiserver.service.EventApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@RequiredArgsConstructor
//@Service
public class EventApiConsumer {

    private final EventApiService eventApiService;
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "${kafka.topic.event.scheduler}", containerFactory = "eventKafkaListenerContainerFactory")
    public void listener(String message) throws JsonProcessingException {
        log.info("eventApiConsumer running!");
        log.info("message = {}", message);
        EventConsumeMessage eventConsumeMessage = objectMapper.readValue(message, EventConsumeMessage.class);
        eventApiService.requestCreateCoupons(eventConsumeMessage.getEventId());
    }
}
