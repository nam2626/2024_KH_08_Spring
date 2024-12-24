package com.kh;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppContext.class);
		
		Greeting g1 = ctx.getBean("greeter",Greeting.class);
		System.out.println(g1.toString());
		Greeting g2 = ctx.getBean("greeter",Greeting.class);
		System.out.println(g2.toString());
		Greeting g3 = ctx.getBean("greeter",Greeting.class);
		System.out.println(g2.toString());
		Greeting g4 = ctx.getBean("greeter",Greeting.class);
		System.out.println(g2.toString());
		
		//g1과 g2가 정말 같은 객체인지 한번 증명
		System.out.println(g1 == g2);
		
		Person p1 = ctx.getBean("person",Person.class);
		Person p2 = (Person) ctx.getBean("person");
		
		System.out.println(p1);
		System.out.println(p2);
		
//		Person p3 = (Person) ctx.getBean("greeter");
		
	}

}




