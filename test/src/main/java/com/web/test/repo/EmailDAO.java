package com.web.test.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.test.model.Email;

@Mapper
public interface EmailDAO {
	List<Email> getEmail();
	Email getEmailByEmailno(int emailno);
	int insert(Email email);
	int open(String pw);
}
