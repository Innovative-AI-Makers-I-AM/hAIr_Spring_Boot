package com.example.demo.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@NoArgsConstructor // 기본 생성자를 자동으로 생성합니다.
public class Member {

    @Id // 이 필드가 엔티티의 기본 키임을 나타냅니다.
    @Getter // getter 메서드를 자동으로 생성합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 데이터베이스가 자동으로 생성해줌을 나타냅니다.
    private Long memberId;

    @Getter // getter 메서드를 자동으로 생성합니다.
    @Column(nullable = false) // 해당 필드가 null 값을 허용하지 않음을 나타냅니다.
    private String email;

    @Getter // getter 메서드를 자동으로 생성합니다.
    @Column(nullable = false) // 해당 필드가 null 값을 허용하지 않음을 나타냅니다.
    private String nickName;

    @Getter
    @Column(nullable = false)
    private String gender;

    @JsonManagedReference
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    // 일대일 관계를 나타냅니다. 지연 로딩을 사용하고, 부모 엔티티에 대한 변경이 자식 엔티티에 전파됩니다.
    private MemberProfile memberProfile;

    @JsonIgnore
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    // 일대다 관계를 나타냅니다. 지연 로딩을 사용하고, 부모 엔티티에 대한 변경이 자식 엔티티에 전파됩니다.
    private Set<Authentication> authentications = new HashSet<>();

    // 생성자: MemberProfile을 포함하는 생성자
    public Member(String email, String nickName, String gender, MemberProfile memberProfile) {
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
        this.memberProfile = memberProfile;
        memberProfile.setMember(this); // 양방향 관계를 설정합니다.
    }

    // 생성자: MemberProfile을 포함하지 않는 생성자
    public Member(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }

    // 사용자가 입력한 비밀번호가 맞는지 확인하는 메서드
    public boolean isRightPassword(String plainToCheck) {
        final Optional<Authentication> maybeBasicAuth = findBasicAuthentication();

        if (maybeBasicAuth.isPresent()) {
            final BasicAuthentication authentication = (BasicAuthentication) maybeBasicAuth.get();
            return authentication.isRightPassword(plainToCheck);
        }

        return false; // 기본 인증 정보가 없으면 false 반환
    }

    // authentications 컬렉션에서 BasicAuthentication 인스턴스를 찾는 메서드
    private Optional<Authentication> findBasicAuthentication () {
        return authentications
                .stream()
                .filter(authentication -> authentication instanceof BasicAuthentication)
                .findFirst();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + memberId +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    // MemberProfile에 대한 getter 메서드
    public MemberProfile getMemberProfile() {
        return this.memberProfile;
    }

    // authentications에 대한 getter 메서드
    public Set<Authentication> getAuthentications() {
        return this.authentications;
    }
}
