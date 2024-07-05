package com.example.demo.member.service.request;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Getter
@ToString
@RequiredArgsConstructor
public class MemberRegisterRequest {
    final private String email;
    final private String password;
    final private String nickName;
    final private String city;
    final private String street;
    final private String addressDetail;
    final private String zipcode;

    public Member toMember () {
        return new Member(
                email,
                nickName,
                MemberProfile.of(city, street, addressDetail, zipcode)
        );
    }
}