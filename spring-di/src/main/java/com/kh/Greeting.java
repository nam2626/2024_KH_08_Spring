package com.kh;

public class Greeting {
	private long id;
	private String content;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
		System.out.println("Greeting Constructor");
	}

	@Override
	public String toString() {
		return "Greeting [id=" + id + ", content=" + content + "]";
	}

}
