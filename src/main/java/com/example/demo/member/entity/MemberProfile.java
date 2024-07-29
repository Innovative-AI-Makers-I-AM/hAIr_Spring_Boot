package com.example.demo.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Getter // Lombok 어노테이션으로 getter 메서드를 자동으로 생성합니다.
@Setter // Lombok 어노테이션으로 setter 메서드를 자동으로 생성합니다.
@AllArgsConstructor // Lombok 어노테이션으로 모든 필드를 매개변수로 갖는 생성자를 자동으로 생성합니다.
@NoArgsConstructor // Lombok 어노테이션으로 기본 생성자를 자동으로 생성합니다.
public class MemberProfile {

    @Id // 이 필드가 엔티티의 기본 키임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 데이터베이스가 자동으로 생성해줌을 나타냅니다.
    private Long memberProfileId;

    @Embedded // Address 클래스가 이 엔티티에 내장됨을 나타냅니다.
    private Address address;

    @OneToOne(fetch = FetchType.LAZY) // 일대일 관계를 나타내며 지연 로딩을 사용합니다.
    @JoinColumn(name = "member_id") // 외래 키 컬럼을 지정합니다.
    private Member member;

    // Address를 매개변수로 받는 생성자
    private MemberProfile(Address address) {
        this.address = address;
    }

    // Member를 매개변수로 받는 생성자
    public MemberProfile(Member member) {
        this.member = member;
    }

    // 정적 팩토리 메서드: 주소 정보를 받아 MemberProfile 인스턴스를 생성
    public static MemberProfile of(String city, String street, String addressDetail, String zipcode) {
        final Address address = Address.of(city, street, addressDetail, zipcode);
        return new MemberProfile(address);
    }

    // Member를 설정하는 메서드
    public void setMember(Member member) {
        this.member = member;
    }
}
