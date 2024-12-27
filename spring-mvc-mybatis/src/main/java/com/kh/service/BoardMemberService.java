package com.kh.service;

import org.springframework.stereotype.Service;

import com.kh.mapper.BoardMemberMapper;

@Service
public class BoardMemberService {
	
	private BoardMemberMapper mapper;

	public BoardMemberService(BoardMemberMapper mapper) {
		this.mapper = mapper;
	}
	
	
}
