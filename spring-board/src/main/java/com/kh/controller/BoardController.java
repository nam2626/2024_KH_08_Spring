package com.kh.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.dto.BoardCommentDTO;
import com.kh.dto.BoardDTO;
import com.kh.service.BoardService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/board")
@Controller
public class BoardController {
  
  private BoardService boardService;

  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping("/{bno}")
  public ModelAndView boardDetail(@PathVariable int bno, ModelAndView view, HttpSession session) {
    //조회수 증가
    HashSet<Integer> visited = (HashSet<Integer>) session.getAttribute("visited");
    if(visited == null){
      visited = new HashSet<Integer>();
      session.setAttribute("visited", visited);
    }
    if(visited.add(bno)){
      boardService.updateBoardCount(bno);
    }

    BoardDTO board = boardService.selectBoard(bno);
    List<BoardCommentDTO> commentList = boardService.getCommentList(bno);

    view.addObject("board", board);
    view.addObject("commentList", commentList);
    view.setViewName("board_view");

    return view;
  }
  
  
}


