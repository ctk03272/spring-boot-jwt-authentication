package com.example.springboot.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.jwt.domain.entity.Member;
import com.example.springboot.jwt.repository.entity.MemberRepository;

@RestController
public class MemberController {
	private final PasswordEncoder passwordEncoder;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	public MemberController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/member/{id}")
	public Member getUser(@PathVariable("id") Long id) {
		return memberRepository.getOne(id);
	}

	@PostMapping("/member")
	public void addMember(@RequestBody Member member) {
		Member member1 = Member.builder().user(member.getUser()).password(passwordEncoder.encode(member.getPassword())).build();
		memberRepository.save(member1);
	}

}
