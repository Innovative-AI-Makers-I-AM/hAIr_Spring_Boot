package com.example.demo.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@Embeddable // 이 클래스가 다른 엔티티 클래스에 내장될 수 있음을 나타냅니다.
@NoArgsConstructor // 기본 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 모든 필드를 매개변수로 갖는 생성자를 자동으로 생성합니다.
public class Address {

    @Column(nullable = false) // 해당 필드가 null 값을 허용하지 않음을 나타냅니다.
    private String city;

    @Column(nullable = false) // 해당 필드가 null 값을 허용하지 않음을 나타냅니다.
    private String street;

    @Column(nullable = false) // 해당 필드가 null 값을 허용하지 않음을 나타냅니다.
    private String addressDetail;

    @Column(nullable = false) // 해당 필드가 null 값을 허용하지 않음을 나타냅니다.
    private String zipcode;

    // 정적 팩토리 메서드: 주소 정보를 받아 Address 인스턴스를 생성
    public static Address of(String city, String street, String addressDetail, String zipcode) {
        return new Address(city, street, addressDetail, zipcode);
    }
}
