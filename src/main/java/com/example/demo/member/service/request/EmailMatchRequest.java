package com.example.demo.member.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class EmailMatchRequest {

    private final String email;
}