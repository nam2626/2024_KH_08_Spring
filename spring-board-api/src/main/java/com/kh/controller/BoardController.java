package com.kh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kh.dto.BoardDTO;
import com.kh.service.BoardService;
import com.kh.token.JwtTokenProvider;
import com.kh.vo.PaggingVO;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardController {

	private BoardService boardService;
	private JwtTokenProvider tokenProvider;
	
	public BoardController(BoardService boardService, JwtTokenProvider tokenProvider) {
		this.boardService = boardService;
		this.tokenProvider = tokenProvider;
	}

	@GetMapping("/board/list")
	public Map<String, Object> index(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "30") int pageContentEa) {
		// 전체 게시글 개수 조회
		int count = boardService.selectBoardTotalCount();
		// 페이지 번호를 보내서 해당 페이지 게시글 목록만 조회
		List<BoardDTO> list = boardService.getBoardList(pageNo, pageContentEa);
		// PaggingVO 페이징 정보 생성
		PaggingVO pagging = new PaggingVO(count, pageNo, pageContentEa);
		// 데이터 추가
		Map<String, Object> map = new HashMap<>();
		map.put("boardList", list);
		map.put("pagging", pagging);
		
		return map;
	}

}
