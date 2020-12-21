package com.web.test.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.web.test.model.User;

@Mapper
public interface UserDAO {
	
	int insert(User user);
	User login(User user);
	User getUserByUserno(int userno);
	List<User> selectUser();
	int update(User user);
	User getUserById(String id);
	int resetPw(User user);
}
