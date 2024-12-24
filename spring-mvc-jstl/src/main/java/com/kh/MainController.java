package com.kh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
//	@GetMapping("/")
	@GetMapping(path = {"/","/loginView.do"})
	public String loginView() {
		return "login";
	}
	
	@GetMapping("/registerView.do")
	public String registerView() {
		return "member_register";
	}
	
	@PostMapping("/login.do")
	public String login(HttpServletRequest request, HttpSession session) {
		//login.jsp에서 보낸 아이디와 암호를 받아서 sysout으로 출력
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		System.out.println(id + " " + pass);
		//아이디와 암호를 세션에 저장해서 login_result.jsp로 이동
		//login_result.jsp에서는 세션에 저장한 아이디 암호 값을 출력
//		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		session.setAttribute("pass", pass);
		
		return "login_result";
	}
	
}






