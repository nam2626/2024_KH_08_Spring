package com.kh.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.dto.BoardDTO;
import com.kh.service.BoardService;
import com.kh.vo.PaggingVO;

@Controller
public class HomeController {
  private BoardService boardService;

  public HomeController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping({"/", "/main"})
  public ModelAndView index(@RequestParam(defaultValue = "1") int pageNo,
    @RequestParam(defaultValue = "30") int pageContentEa, ModelAndView view) {
      //전체 게시글 개수 조회
      int count = boardService.selectBoardTotalCount();
      //페이지 번호를 보내서 해당 페이지 게시글 목록만 조회
      List<BoardDTO> list = boardService.getBoardList(pageNo, pageContentEa);
      //PaggingVO 페이징 정보 생성
      PaggingVO pagging = new PaggingVO(count, pageNo, pageContentEa);
      //데이터 추가
      view.addObject("list", list);
      view.addObject("pagging", pagging);
      //이동할 페이지 설정
      view.setViewName("index");

    return view;
  }


  @GetMapping("/login/view")
  public String loginView() {
      return "login";
  }
  
}
