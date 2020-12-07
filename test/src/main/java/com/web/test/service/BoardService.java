package com.web.test.service;

import java.util.List;

import com.web.test.model.Board;

public interface BoardService {
	List<Board> getBoard();
	Board getBoardByBoardno(int boardno);
	boolean insert(Board board);
	boolean update(Board board);
	boolean delete(int boardno);
}
