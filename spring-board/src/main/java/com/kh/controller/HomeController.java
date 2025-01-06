package com.kh.controller;

@Controller
public class HomeController {
  
  @GetMapping("/")
  public String index(){
    return "index";
  }

}
