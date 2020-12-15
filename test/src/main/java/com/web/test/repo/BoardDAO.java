package com.web.test.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.test.model.Board;

@Mapper
public interface BoardDAO {
	List<Board> getBoard();
	Board getBoardByBoardno(int boardno);
	int getBoardnoByBoard(Board board);
	int insert(Board board);
	int update(Board board);
	int delete(int boardno);
}
