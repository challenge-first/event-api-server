package com.example.eventapiserver.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EventRequestDto {

    private Long eventId;
    private Long memberId;
}
