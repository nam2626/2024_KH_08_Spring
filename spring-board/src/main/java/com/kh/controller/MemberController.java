package com.kh.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.dto.BoardMemberDTO;
import com.kh.service.BoardMemberService;


@RequestMapping("/member")
@Controller
public class MemberController {
  
  private BoardMemberService service;

  public MemberController(BoardMemberService service) {
    this.service = service;
  }
  
  @GetMapping("/main")
  public ModelAndView meberMain(ModelAndView view) {
      List<BoardMemberDTO> list = service.selectAllMember();
      view.addObject("list", list);
      view.setViewName("admin_member_main");
      return view;
  }
  
}
