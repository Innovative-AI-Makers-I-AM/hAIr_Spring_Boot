package com.example.demo.member.service;

import com.example.demo.member.entity.Authentication;
import com.example.demo.member.entity.BasicAuthentication;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.AuthenticationRepository;
import com.example.demo.member.repository.MemberProfileRepository;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.request.EmailMatchRequest;
import com.example.demo.member.service.request.EmailPasswordRequest;
import com.example.demo.member.service.request.MemberLoginRequest;
import com.example.demo.member.service.request.MemberRegisterRequest;
import com.example.demo.utility.security.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberRepository memberRepository;
    final private AuthenticationRepository authenticationRepository;
    final private MemberProfileRepository memberProfileRepository;
    final private RedisService redisService;

    @Override
    public Boolean emailValidation(String email) {
        Optional<Member> maybeMember = memberRepository.findByEmail(email);
        if (maybeMember.isPresent()) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean memberNicknameValidation(String nickName) {
        Optional<Member> maybeMemberNickname = memberRepository.findByNickName(nickName);

        if (maybeMemberNickname.isPresent()) {
            return false;
        }
        return true;
    }


    @Override
    public Boolean signUp(MemberRegisterRequest memberRegisterRequest) {
        final Member member = memberRegisterRequest.toMember();
        memberRepository.save(member);

        final BasicAuthentication authentication = new BasicAuthentication(
                member,
                Authentication.BASIC_AUTH,
                memberRegisterRequest.getPassword()
        );
        authenticationRepository.save(authentication);

        return true;
    }

    @Override
    public MemberLoginResponse signIn(MemberLoginRequest memberLoginRequest) {
        Optional<Member> maybeMember =
                memberRepository.findByEmail(memberLoginRequest.getEmail());

        System.out.println("loginRequest: " + memberLoginRequest);
        System.out.println("maybeMember.isPresent(): " + maybeMember.isPresent());

        if (maybeMember.isPresent()) {
            Member member = maybeMember.get();

            System.out.println("Password entered by the user: " + memberLoginRequest.getPassword());
            System.out.println("Password Match Check: " + member.isRightPassword(memberLoginRequest.getPassword()));

            if (!member.isRightPassword(memberLoginRequest.getPassword())) {
                System.out.println("Is it Okay?");
                throw new RuntimeException("Email or passowrd is incorrec!");
            }

            UUID userToken = UUID.randomUUID();

            // redis 처리 필요
            redisService.deleteByKey(userToken.toString());
            redisService.setKeyAndValue(userToken.toString(), member.getMemberId());

            /* (지영) 로그인하면 사용자의 token, memberId, nickname + 여기에 memberType 까지 알려주도록 수정 */
            MemberLoginResponse memberLoginResponse;
            memberLoginResponse = new MemberLoginResponse(userToken.toString(), member.getMemberId(), member.getNickName());

            return memberLoginResponse;
        }

        throw new RuntimeException("가입된 사용자가 아닙니다!");
    }

    @Override
    public Boolean applyNewPassword(EmailPasswordRequest emailPasswordRequest) {
        Optional<Authentication> maybeAuthentication = authenticationRepository.findByEmail(emailPasswordRequest.getEmail());
        if (!maybeAuthentication.isPresent()){
            return false;
        }
        BasicAuthentication authentication = (BasicAuthentication)maybeAuthentication.get();
        authentication.setPassword(emailPasswordRequest.getPassword());
        authenticationRepository.save(authentication);

        return true;
    }

    @Override
    public Boolean emailMatch(EmailMatchRequest toEmailMatchRequest) {
        Optional<Member> maybeMember = memberRepository.findByEmail(toEmailMatchRequest.getEmail());
        if (!maybeMember.isPresent()){
            return false;
        }

        return true;
    }

    public void resign(String token) {
        System.out.println("Token : " + token);
        token = token.substring(1, token.length()-1);
        System.out.println("Token : " + token);
        Long memid = redisService.getValueByKey(token);
        System.out.println("memid : " + memid);
        Optional<Member> maybeMember = memberRepository.findById(memid);
        if (maybeMember.isPresent()) {
            Member member = maybeMember.get();
            memberRepository.delete(member);
            System.out.println("Resigned!");
        } else {
            System.out.println("없는디?");
        }
    }
}