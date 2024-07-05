package com.example.demo.member.controller.form;

import com.example.demo.member.service.request.MemberLoginRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberLoginForm {

    private String email;
    private String password;

    public MemberLoginRequest toMemberLoginRequest () {
        return new MemberLoginRequest(email, password);
    }
}