package com.kh;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {
	
	@Bean
	public Greeting greeter() {
		Greeting g = new Greeting(1000, "홍길동");
		return g;
	}
	
}
