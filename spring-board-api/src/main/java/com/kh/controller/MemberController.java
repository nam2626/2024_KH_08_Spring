package com.kh.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.kh.service.BoardMemberService;
import com.kh.token.JwtTokenProvider;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {
	private final BoardMemberService memberService;
	private final JwtTokenProvider tokenProvider;
	
	public MemberController(BoardMemberService memberService, JwtTokenProvider tokenProvider) {
		this.memberService = memberService;
		this.tokenProvider = tokenProvider;
	}
	
	
	
}










