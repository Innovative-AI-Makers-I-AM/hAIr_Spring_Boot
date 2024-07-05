package com.example.demo.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = "member") // member 필드를 제외하고 toString 메서드 생성
@NoArgsConstructor            // 매개변수가 없는 생성자
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 상속 전략 사용
@DiscriminatorColumn(name = "authentication_type") // 상속된 클래스의 유형을 구분하는 컬럼 지정
public abstract class Authentication {

    public static final String BASIC_AUTH = "BASIC";

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authenticationId = null;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계를 나타내며 지연 로딩을 사용
    @JoinColumn(name = "member_id") // 외래 키 컬럼을 지정
    private Member member;

    @Column(name = "authentication_type", nullable = false, insertable = false, updatable = false)
    private String authenticationType;

    public Authentication(Member member, String authenticationType) {
        this.member = member;
        this.authenticationType = authenticationType;
    }
}
