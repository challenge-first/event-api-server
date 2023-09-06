package com.example.eventapiserver.controller;

import com.example.eventapiserver.service.EventApiService;
import com.example.eventapiserver.service.UserDuplicatedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EventApiController {

    private final EventApiService eventApiService;

    @PostMapping("/events/coupon")
    public ResponseEntity<Map<String,String>> event(@RequestBody EventRequestDto eventRequestDto) {
        long now = System.nanoTime();
        eventApiService.addQueue(eventRequestDto.getEventId(), eventRequestDto.getMemberId(), now);
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("msg","success"));
    }

    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity<Map<String, String>> duplicatedExceptionHandler(UserDuplicatedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("msg",e.getMessage()));
    }
}
