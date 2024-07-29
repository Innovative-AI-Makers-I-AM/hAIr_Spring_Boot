package com.example.demo.member.service;

import java.util.List;

import com.example.demo.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberWithImagesResponse {
    private Member member;
    private List<String> imageUrls;
}
