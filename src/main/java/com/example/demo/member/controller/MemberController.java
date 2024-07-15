package com.example.demo.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.member.controller.form.EmailMatchForm;
import com.example.demo.member.controller.form.EmailPasswordForm;
import com.example.demo.member.controller.form.MemberLoginForm;
import com.example.demo.member.controller.form.MemberRegisterForm;
import com.example.demo.member.controller.form.MemberUpdateForm;
import com.example.demo.member.entity.Member;
import com.example.demo.member.service.MemberLoginResponse;
import com.example.demo.member.service.MemberService;
import com.example.demo.utility.security.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로깅을 위한 Lombok 어노테이션
@RestController // REST API 컨트롤러임을 나타내는 어노테이션
@RequestMapping("/member") // "/member" 경로 아래에서 동작하는 컨트롤러
@RequiredArgsConstructor // 생성자를 통한 의존성 주입을 자동으로 생성해주는 Lombok 어노테이션
public class MemberController {

    private final MemberService memberService;
    private final RedisService redisService;

    // 이메일 검증하기
    @PostMapping("/check-email/{email}")
    public Boolean emailValidation(@PathVariable("email") String email) {
        log.info("emailValidation(): " + email);
        return memberService.emailValidation(email);
    }

    // 닉네임 검증하기
    @PostMapping("/check-nickName/{nickName}")
    public Boolean memberNickNameValidation(@PathVariable("nickName") String nickName) {
        log.info("memberNicknameDuplicateCheck()" + nickName);
        return memberService.memberNicknameValidation(nickName);
    }

    // 회원 가입
    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody MemberRegisterForm form) {
        log.info("signUp(): " + form);
        return memberService.signUp(form.toMemberRegisterRequest());
    }

    // 로그인
    @PostMapping("/sign-in")
    public MemberLoginResponse signIn(@RequestBody MemberLoginForm form) {
        log.info("signIn(): " + form);
        return memberService.signIn(form.toMemberLoginRequest());
    }

    // 이메일과 휴대폰 번호 매칭 확인
    @PostMapping("/emailMatch")
    public Boolean emailMatchPhone(@Validated @RequestBody EmailMatchForm form, BindingResult bindingResult) {
        log.info("MainFormController#emailMatchPhone: {}", form);
        if (bindingResult.hasFieldErrors()) {
            return false;
        }
        return memberService.emailMatch(form.toEmailMatchRequest());
    }

    // 새로운 비밀번호 설정
    @PostMapping("/applyNewPassword")
    public Boolean applyNewPassword(@Validated @RequestBody EmailPasswordForm form) {
        log.info("MainFormController#applyNewPassword: {}", form);
        return memberService.applyNewPassword(form.toEmailPasswordRequest());
    }

    // 로그아웃
    @PostMapping("/logout")
    public void logout(@RequestBody String token) {
        token = token.substring(0, token.length() - 1);
        log.info("logout(): " + token);
        // 로그아웃 로직 필요
        redisService.deleteByKey(token);
    }

    // 회원탈퇴
    @PostMapping("/resign")
    public void resign(@RequestBody String token) {
        // Token 값이 넘어오는거에 따라서 전처리 필요
//        token = token.substring(3, token.length() - 4);
        log.info("resign(): " + token);

        memberService.resign(token);
    }

    // 사용자 정보 가져오기
    @GetMapping("/{userId}")
    public Member getUserById(@PathVariable("userId") Long userId) {
        log.info("getUserById(): " + userId);
        return memberService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateMember(@PathVariable("userId") Long userId, @ModelAttribute MemberUpdateForm form) {
        log.info("updateMember(): userId=" + userId + ", form=" + form);

        boolean isUpdated = memberService.updateMember(userId, form.toMemberUpdateRequest());
        if (isUpdated) {
            return ResponseEntity.ok("Member updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Member update failed");
        }
    }
}
