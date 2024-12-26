package com.kh;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class RestMainController {

	@GetMapping("/main2")
	public String main() {
		return "index"; 
	}
	
	@GetMapping("/get")
	public RegisterDTO getMethodName() {
		return new RegisterDTO("A0001","김철수","123456",30);
	}
	
}






