package com.kh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String loginView() {
		return "login";
	}
	
	@GetMapping("/registerView.do")
	public String registerView() {
		return "member_register";
	}
	
}






