package com.example.springboot.jwt.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.springboot.jwt.controller.resource.LoginResult;
import com.example.springboot.jwt.domain.entity.Member;
import com.example.springboot.jwt.security.JwtHelper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * The auth controller to handle login requests
 *
 * @author imesha
 */
@RestController
public class AuthController {

	private final JwtHelper jwtHelper;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	public AuthController(JwtHelper jwtHelper, UserDetailsService userDetailsService,
		PasswordEncoder passwordEncoder) {
		this.jwtHelper = jwtHelper;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/check")
	public ResponseEntity getUser() {
		return ResponseEntity.accepted().build();
	}

	@PostMapping(path = "login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public LoginResult login(
		@RequestParam String username,
		@RequestParam String password) {

		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
		}

		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			Map<String, String> claims = new HashMap<>();
			claims.put("username", username);
			
/*			String authorities = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(","));
			claims.put("authorities", authorities);*/
			claims.put("userId", String.valueOf(1));

			String jwt = jwtHelper.createJwtForClaims(username, claims);
			return new LoginResult(jwt);
		}

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
	}
}
