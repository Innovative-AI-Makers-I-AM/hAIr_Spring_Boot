package com.example.demo.member.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.service.request.EmailMatchRequest;
import com.example.demo.member.service.request.EmailPasswordRequest;
import com.example.demo.member.service.request.MemberLoginRequest;
import com.example.demo.member.service.request.MemberRegisterRequest;
import com.example.demo.member.service.request.MemberUpdateRequest;

public interface MemberService {
    Boolean emailValidation(String email);
    Boolean signUp(MemberRegisterRequest memberRegisterRequest);
    MemberLoginResponse signIn(MemberLoginRequest memberLoginRequest);
    Boolean applyNewPassword(EmailPasswordRequest toEmailPasswordRequest);
    Boolean emailMatch(EmailMatchRequest toEmailMatchRequest);
    void resign(String token);
    Boolean memberNicknameValidation(String nickName);
    Member getUserById(Long userId);
    boolean updateMember(Long userId, MemberUpdateRequest memberUpdateRequest);
}