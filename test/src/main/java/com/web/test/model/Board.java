package com.web.test.model;

import java.sql.Date;

public class Board {
	
	private int boardno, userno;
	private String title, content;
	private Date date;
	public Board() {
		super();
	}
	public Board(int boardno, int userno, String title, String content, Date date) {
		super();
		this.boardno = boardno;
		this.userno = userno;
		this.title = title;
		this.content = content;
		this.date = date;
	}
	public int getBoardno() {
		return boardno;
	}
	public void setBoardno(int boardno) {
		this.boardno = boardno;
	}
	public int getUserno() {
		return userno;
	}
	public void setUserno(int userno) {
		this.userno = userno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Board [boardno=" + boardno + ", userno=" + userno + ", title=" + title + ", content=" + content
				+ ", date=" + date + "]";
	}
}
