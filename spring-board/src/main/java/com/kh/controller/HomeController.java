package com.kh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.service.BoardService;

@Controller
public class HomeController {
  private BoardService boardService;

  public HomeController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping("/")
  public ModelAndView index(@RequestParam(defaultValue = "1") int pageNo,
    @RequestParam(defaultValue = "30") int pageContentEa, ModelAndView view) {
      //전체 게시글 개수 조회

      //페이지 번호를 보내서 해당 페이지 게시글 목록만 조회

      //PaggingVO 페이징 정보 생성

      //데이터 추가

      //이동할 페이지 설정
      view.setViewName("index");

    return view;
  }

}
