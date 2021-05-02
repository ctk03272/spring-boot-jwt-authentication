package com.example.springboot.jwt.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.jwt.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByUser(String user);
}