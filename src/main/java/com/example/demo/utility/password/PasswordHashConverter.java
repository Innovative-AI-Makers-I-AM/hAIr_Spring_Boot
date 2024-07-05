package com.example.demo.utility.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PasswordHashConverter implements AttributeConverter<String, String> {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return passwordEncoder.encode(attribute); // 비밀번호를 해싱하여 저장
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData; // 해시화된 비밀번호를 그대로 반환
    }
}
