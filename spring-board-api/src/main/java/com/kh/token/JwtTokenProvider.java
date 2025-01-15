package com.kh.token;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.kh.dto.BoardMemberDTO;

import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenProvider {
	//토근 유효시간 설정
	private final long expiredTime = 1000L * 60L * 60L * 1L; // 1시간의 유효시간 설정
	private SecretKey key = Jwts.SIG.HS256.key().build();
	// Key key = Keys.hmacShaKeyFor("256비트 이상인 문자열".getBytes(StandardCharsets.UTF_8));
	
	public String generateJwtToken(BoardMemberDTO member) {
		Date expire = new Date(Calendar.getInstance().getTimeInMillis() + expiredTime);
		
		return Jwts.builder().header().add(createHeader());
	}
	private Map<String, Object> createHeader() {
		Map<String, Object> map = new HashMap<>();
		map.put("typ", "JWT");//토큰 종류
		map.put("alg", "HS256");//암호화에 사용할 알고리즘
		map.put("regDate", System.currentTimeMillis());//생성시간
		
		return map;
	}
}










