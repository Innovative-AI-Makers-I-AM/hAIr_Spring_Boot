package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m  where m.email = :email")
    Optional<Member> findByEmail(String email);

    @Query("select m from Member m where m.nickName = :nickName")
    Optional<Member> findByNickName(String nickName);



    // FETCH TYPE LAZY에 의해 proxy 참조가 안되서 join fetch로 강제 참조시킴
    // @Query("SELECT m FROM Member m JOIN FETCH m.memberProfile mp JOIN FETCH m.authentications a WHERE m.memberId = :memberId")
    // List<Member> findAllByMemberId(@Param("memberId") Long memberId);

    // Optional<Member> findMemberByMemberId(Long memberId);
}