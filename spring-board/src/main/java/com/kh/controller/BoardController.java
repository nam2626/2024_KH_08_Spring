package com.kh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.dto.BoardCommentDTO;
import com.kh.dto.BoardDTO;
import com.kh.dto.BoardFileDTO;
import com.kh.dto.BoardMemberDTO;
import com.kh.service.BoardService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




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
  
  @GetMapping("/write/view")
  public String boardView() {
      return "board_write_view";
  }
  
  @PostMapping("/write")
  public String boardWrite(BoardDTO board, HttpSession session, @RequestParam(value = "file", required = false) MultipartFile[] files) {
      //1. 사용자가 작성한 게시글 제목, 내용, 파일 받아옴
      //2. 작성자는 세션에서 아이디만 가져옴
      String id = ((BoardMemberDTO)session.getAttribute("user")).getId();
      board.setId(id);
      //3. 게시글 새번호 받아옴
      int bno = boardService.selectBoardNo();
      board.setBno(bno);
      //4. 파일 업로드
      List<BoardFileDTO> fileList = new ArrayList<>();
      
      //5. 게시글 데이터베이스에 추가
      boardService.insertBoard(board, fileList);

      return "redirect:/board/" + board.getBno();
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

  @GetMapping("/delete/{bno}")
  public String boardDelete(@PathVariable int bno, HttpSession session, HttpServletResponse response) {
      if(session.getAttribute("user") != null && ((BoardMemberDTO)session.getAttribute("user")).getId().equals(boardService.selectBoard(bno).getId())){
        boardService.deleteBoard(bno);
      }else{
        response.setContentType("text/html; charset=utf-8");
        try {
            response.getWriter().println(
                    "<script>alert('해당 글 작성자만 삭제가 가능합니다..'); history.back();</script>");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

      }
      return "redirect:/main";
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
  
  @GetMapping("/comment/{bno}")
  @ResponseBody
  public List<BoardCommentDTO> getMethodName(@PathVariable int bno, @RequestParam int start) {
      List<BoardCommentDTO> commentList = boardService.getCommentList(bno, start);
      return commentList;
  }
  
  @GetMapping("/comment/like/{cno}")
  @ResponseBody
  public Map<String, Object> boardCommentLike(@PathVariable int cno, HttpSession session) {
      Map<String, Object> map = new HashMap<String, Object>();
      
      if(session.getAttribute("user") == null){
          map.put("code", 2);
          map.put("msg", "로그인 하셔야 이용하실수 있습니다.");
      }else{
          String id = ((BoardMemberDTO)session.getAttribute("user")).getId();
          try {
              boardService.insertBoardCommentLike(cno, id);
              map.put("code", 1);
              map.put("msg", "해당 댓글에 좋아요 하셨습니다.");
          } catch (Exception e) {
              boardService.deleteBoardCommentLike(cno, id);
              map.put("code", 1);
              map.put("msg", "해당 댓글에 좋아요를 취소 하셨습니다.");
          }
          map.put("count", boardService.selectCommentLikeCount(cno));
      }
      return map;
    }  
  @GetMapping("/comment/hate/{cno}")
  @ResponseBody
  public Map<String, Object> boardCommentHate(@PathVariable int cno, HttpSession session) {
      Map<String, Object> map = new HashMap<String, Object>();
      
      if(session.getAttribute("user") == null){
          map.put("code", 2);
          map.put("msg", "로그인 하셔야 이용하실수 있습니다.");
      }else{
          String id = ((BoardMemberDTO)session.getAttribute("user")).getId();
          try {
              boardService.insertBoardCommentHate(cno, id);
              map.put("code", 1);
              map.put("msg", "해당 댓글에 싫어요 하셨습니다.");
          } catch (Exception e) {
              boardService.deleteBoardCommentHate(cno, id);
              map.put("code", 1);
              map.put("msg", "해당 댓글에 싫어요를 취소 하셨습니다.");
          }
          map.put("count", boardService.selectCommentHateCount(cno));
      }
      return map;
    }  

    @GetMapping("/comment/delete/{cno}")
    public String commentDelete(@PathVariable int cno, HttpSession session, HttpServletResponse response) {
      BoardCommentDTO comment = boardService.selectComment(cno);
      if(session.getAttribute("user") != null && ((BoardMemberDTO)session.getAttribute("user")).getId().equals(comment.getId())){
        boardService.deleteBoardComment(cno);
      }else{
        response.setContentType("text/html; charset=utf-8");
        try {
            response.getWriter().println(
                    "<script>alert('해당 댓글 작성자만 삭제가 가능합니다.'); history.back();</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
      }
      return "redirect:/board/" + comment.getBno();
    }
    
    @PatchMapping("/comment")
    @ResponseBody
    public Map<String, Object> boardCommentUpdate(@RequestBody Map<String, String> body, HttpSession session) {
      Map<String, Object> map = new HashMap<String, Object>();
      BoardCommentDTO comment = boardService.selectComment(Integer.parseInt(body.get("cno")));
      if(session.getAttribute("user") != null && ((BoardMemberDTO)session.getAttribute("user")).getId().equals(comment.getId())){
        comment.setContent(body.get("content"));
        boardService.updateBoardComment(comment);
        map.put("code", 1);
        map.put("msg", "해당 댓글 수정 완료");
      }else{
        map.put("code", 2);
        map.put("msg", "해당 댓글 작성자만 수정이 가능합니다.");
      }
      return map;
    }
}


