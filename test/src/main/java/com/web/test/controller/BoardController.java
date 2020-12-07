package com.web.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.test.model.Board;
import com.web.test.service.BoardService;
import com.web.test.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("board")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	UserService userService;
	
	@GetMapping
	@ApiOperation(value = "모든 게시글 목록 조회")
	public ResponseEntity<List<Board>> BoardList() {
		logger.debug("BoardList");
		List<Board> list = boardService.getBoard();
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "게시글 작성")
	public ResponseEntity<String> writeBoard(@RequestBody Board board) {
		logger.debug("writeBoard");
		if (boardService.insert(board))
			return new ResponseEntity<String> (SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String> (FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("{boardno}")
	@ApiOperation(value = "해당 게시글 조회")
	public ResponseEntity<Board> boardDetail(@PathVariable int boardno) {
		logger.debug("readBoard");
		Board board = boardService.getBoardByBoardno(boardno);
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	@PutMapping("{boardno}")
	@ApiOperation(value = "게시글 수정")
	public ResponseEntity<String> updateBoard(@RequestBody Board board) {
		logger.debug("updateBoard");
		if (boardService.update(board))
			return new ResponseEntity<String> (SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String> (FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("{boardno}")
	@ApiOperation(value = "게시글 삭제")
	public ResponseEntity<String> deleteBoard(@PathVariable int boardno) {
		logger.debug("deleteBoard");
		if (boardService.delete(boardno))
			return new ResponseEntity<String> (SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String> (FAIL, HttpStatus.BAD_REQUEST);
	}
	
}
