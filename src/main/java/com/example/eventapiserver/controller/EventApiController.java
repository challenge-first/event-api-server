package com.example.eventapiserver.controller;

import com.example.eventapiserver.service.EventApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//@RequestMapping("/event-server")
@RestController
public class EventApiController {

    private final EventApiService eventApiService;

    @PostMapping("/events/coupon")
    public ResponseEntity<Map<String,String>> event(@RequestBody EventRequestDto eventRequestDto) {
        long now = System.nanoTime();
        log.info("Controller 요청 접근 = {}, [{}]",eventRequestDto.getMemberId(), now);
        eventApiService.addQueue(eventRequestDto.getEventId(), eventRequestDto.getMemberId(), now);
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("msg","success"));
    }

    @GetMapping("/events/coupon")
    public ResponseEntity<Void> getEvent(@RequestParam Long memberId, @RequestParam Long eventId) {
        eventApiService.getQueue(eventId, memberId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

//    @GetMapping("/events/test")
//    public ResponseEntity<Void> test(@RequestParam("eventId") Long eventId) {
//        eventApiService.requestCreateCoupons(eventId);
//
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }
}
