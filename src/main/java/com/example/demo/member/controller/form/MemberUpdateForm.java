package com.example.demo.member.controller.form;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.member.service.request.MemberUpdateRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberUpdateForm {
    private MultipartFile profileImage;
    private String email;
    private String nickName;
    private String city;
    private String street;
    private String addressDetail;
    private String zipcode;
    private String gender;

    public MemberUpdateRequest toMemberUpdateRequest() {
        return new MemberUpdateRequest(email, nickName, city, street, addressDetail, zipcode, gender, profileImage);
    }
}

