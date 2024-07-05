package com.example.demo.member.entity;

import com.example.demo.utility.password.PasswordHashConverter;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@ToString(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue(Authentication.BASIC_AUTH)
public class BasicAuthentication extends Authentication {

    @Setter
    @Column(nullable = false)
    @Convert(converter = PasswordHashConverter.class) // 비밀번호를 해싱하기 위한 컨버터 지정
    private String password;

    public BasicAuthentication(Member member, String authenticationType, String password) {
        super(member, authenticationType);
        this.password = password;
    }

    public boolean isRightPassword(String plainToCheck) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainToCheck, password); // 비밀번호 검증
    }
}
