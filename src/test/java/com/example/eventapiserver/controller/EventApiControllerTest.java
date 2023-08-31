package com.example.eventapiserver.controller;

import com.example.eventapiserver.service.EventApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventApiControllerTest {

    @Autowired
    EventApiService service;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void test() throws Exception {
        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(threadCount);
        RestTemplate restTemplate = new RestTemplate();
        for (int i =1; i<= threadCount; i++) {
            long userid = i;
            executorService.submit(() -> {
                try {
                    EventRequestDto request = new EventRequestDto(1L,userid);
                    restTemplate.postForObject("http://localhost:8000/event-server/events/coupon",request,java.util.Map.class);
//                    service.addQueue(request.getEventId(),request.getMemberId());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

//        long count = couponRepository.count();
//        assertThat(count).isEqualTo(1);
    }

}