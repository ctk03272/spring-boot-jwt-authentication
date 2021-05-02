package com.example.springboot.jwt.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springboot.jwt.domain.entity.Member;
import com.example.springboot.jwt.repository.entity.MemberRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
		Optional<Member> member = Optional.ofNullable(memberRepository.findByUser(user));

		if (!member.isPresent()) {
			throw new UsernameNotFoundException(member.get().getName() + "is not found.");
		}

		JwtUserDetails jwtUserDetails = new JwtUserDetails();
		jwtUserDetails.setUsername(member.get().getName());
		jwtUserDetails.setPassword(member.get().getPassword());
		jwtUserDetails.setEnabled(true);
		jwtUserDetails.setAccountNonExpired(true);
		jwtUserDetails.setAccountNonLocked(true);
		jwtUserDetails.setCredentialsNonExpired(true);

		return jwtUserDetails;
	}
}
