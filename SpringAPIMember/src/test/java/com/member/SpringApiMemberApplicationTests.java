package com.member;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.member.dto.BoardMemberDTO;
import com.member.service.MemberService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class SpringApiMemberApplicationTests {

	@Autowired
	private MemberService service;

	//전체 테스트 수행 전에 실행할 메서드 - 반드시 static 처리
	@BeforeAll
	public static void initAll() {
		System.out.println("전체 초기화 코드");
	}
	
	//모든 메서드 실행 후 한번만 실행 - 반드시 static 처리
	@AfterAll
	public static void endTestAll() {
		System.out.println("테스트 종료 후 코드");
	}
	
	//각 테스트 메서드 실행 전에 실행하는 메서드
	@BeforeEach
	public void testBefore(TestInfo info) {
		System.out.println("테스트 메서드 수행전에 수행하는 메서드");
		System.out.println(info.getTestMethod().get().getName() + " - " 
		+ info.getDisplayName());
	}
	
	//각 테스트 메서드 실행 후에 실행하는 메서드
	@AfterEach
	public void testAfter() {
		System.out.println("테스트 메서드 수행후에 수행하는 메서드");
	}
	
	
	@Test
	@Order(1)
	@DisplayName("회원 정보 등록 테스트")
	//TestInfo 테스트할 메서드 정보
	public void insertTest(TestInfo info) {
//		System.out.println(info.getTestMethod().get().getName() + " - " 
//				+ info.getDisplayName());
		System.out.println("회원 정보 등록 테스트 시작");
		BoardMemberDTO dto = 
				new BoardMemberDTO("test02", "123456", "테스트", "테스트", 4);
		int result = 0;
		try {
			result = service.insertMember(dto);
		} catch (Exception e) {
			fail("회원 등록 테스트 실패 - " + e.getMessage());
//			e.printStackTrace();
		}
		//테스트 실패시 해당 테스트 메서드가 강제 종료
		assertEquals(1, result,"회원 등록 테스트 실패");
		System.out.println("회원 정보 등록 테스트 종료");
	}

	@Test
	@Order(2)
	@DisplayName("회원 정보 삭제 테스트")
	public void deleteTest() {
		int result = service.deleteMember("test02");
		assertEquals(1, result, "회원 정보 삭제 실패");
	}
}










