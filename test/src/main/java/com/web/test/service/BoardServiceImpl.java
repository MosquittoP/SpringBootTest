package com.web.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.test.model.Board;
import com.web.test.repo.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDao;
	
	@Override
	public List<Board> getBoard() {
		return boardDao.getBoard();
	}

	@Override
	public Board getBoardByBoardno(int boardno) {
		return boardDao.getBoardByBoardno(boardno);
	}

	@Override
	public boolean insert(Board board) {
		return boardDao.insert(board) == 1;
	}

	@Override
	public boolean update(Board board) {
		return boardDao.update(board) == 1;
	}

	@Override
	public boolean delete(int boardno) {
		return boardDao.delete(boardno) == 1;
	}

	@Override
	public int getBoardnoByBoard(Board board) {
		return boardDao.getBoardnoByBoard(board);
	}

}
