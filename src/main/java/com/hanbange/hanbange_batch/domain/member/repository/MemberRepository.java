package com.hanbange.hanbange_batch.domain.member.repository;

import com.hanbange.hanbange_batch.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}