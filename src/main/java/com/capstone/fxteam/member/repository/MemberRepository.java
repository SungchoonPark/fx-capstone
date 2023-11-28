package com.capstone.fxteam.member.repository;

import com.capstone.fxteam.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByNickname(String nickname);
}
