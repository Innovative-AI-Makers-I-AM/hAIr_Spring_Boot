package com.example.demo.member.service.request;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.member.entity.Address;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberProfile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Getter
@ToString
@RequiredArgsConstructor
public class MemberUpdateRequest {
    private final String email;
    private final String nickName;
    private final String city;
    private final String street;
    private final String addressDetail;
    private final String zipcode;
    private final String gender;
    private final MultipartFile profileImage;

    public Member toMember(Member existingMember) {
        existingMember.setEmail(email);
        existingMember.setNickName(nickName);
        existingMember.setGender(gender);

        if (existingMember.getMemberProfile() == null) {
            existingMember.setMemberProfile(MemberProfile.of(city, street, addressDetail, zipcode));
        } else {
            Address address = existingMember.getMemberProfile().getAddress();
            address.setCity(city);
            address.setStreet("");
            address.setAddressDetail("");
            address.setZipcode("");
        }

        // 프로필이미지는 나중에 하기로..
        // if (profileImage != null && !profileImage.isEmpty()) {
        //     try {
        //         existingMember.getMemberProfile().setProfileImage(profileImage.getBytes());
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        // }

        return existingMember;
    }
}
