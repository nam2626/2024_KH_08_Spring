package com.kh.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.dto.BoardCommentDTO;
import com.kh.dto.BoardDTO;
import com.kh.dto.BoardMemberDTO;
import com.kh.service.BoardService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    List<BoardCommentDTO> commentList = boardService.getCommentList(bno, 1);

    view.addObject("board", board);
    view.addObject("commentList", commentList);
    view.setViewName("board_view");

    return view;
  }
  
  @ResponseBody
  @GetMapping("/like/{bno}")
  public Map<String, Object> boardLike(@PathVariable int bno, HttpSession session) {
    Map<String, Object> map = new HashMap<String, Object>();
    
    if(session.getAttribute("user") == null){
      map.put("code", 2);
      map.put("msg", "로그인 하셔야 이용하실수 있습니다.");
    }else{
      String id = ((BoardMemberDTO)session.getAttribute("user")).getId();
      try {
        boardService.insertBoardLike(bno, id);
        map.put("code", 1);
        map.put("msg", "해당 게시글에 좋아요 하셨습니다.");
      } catch (Exception e) {
        boardService.deleteBoardLike(bno, id);
        map.put("code", 1);
        map.put("msg", "해당 게시글에 좋아요를 취소 하셨습니다.");
      }
      map.put("count", boardService.getBoardLike(bno));
    }
    return map;
  }

  @ResponseBody
  @GetMapping("/hate/{bno}")
  public Map<String, Object> boardHate(@PathVariable int bno, HttpSession session) {
    Map<String, Object> map = new HashMap<String, Object>();
    
    if(session.getAttribute("user") == null){
      map.put("code", 2);
      map.put("msg", "로그인 하셔야 이용하실수 있습니다.");
    }else{
      String id = ((BoardMemberDTO)session.getAttribute("user")).getId();
      try {
        boardService.insertBoardHate(bno, id);
        map.put("code", 1);
        map.put("msg", "해당 게시글에 싫어요 하셨습니다.");
      } catch (Exception e) {
        boardService.deleteBoardHate(bno, id);
        map.put("code", 1);
        map.put("msg", "해당 게시글에 싫어요를 취소 하셨습니다.");
      }
      map.put("count", boardService.getBoardHate(bno));
    }
    return map;
  }

  @PostMapping("/comment")
  public String boardCommentWrite(BoardCommentDTO comment, HttpSession session, HttpServletResponse response) {
      //로그인하지 않았을때
      if(session.getAttribute("user") == null){
          response.setContentType("text/html; charset=utf-8");
          try {
              response.getWriter().println(
                      "<script>alert('로그인 하셔야 이용하실수 있습니다.'); location.href='/login/view';</script>");
          } catch (Exception e) {
              e.printStackTrace();
          }
          return null;
      }



      String id = ((BoardMemberDTO)session.getAttribute("user")).getId();
      comment.setId(id);
      boardService.insertBoardComment(comment);
      
      return "redirect:/board/" + comment.getBno();
  }
  
}


