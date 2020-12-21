package com.web.test.service;

import java.util.List;

import com.web.test.model.User;

public interface UserService {
	boolean insert(User user);
	User login(User user);
	User getUserByUserno(int userno);
	List<User> selectUser();
	boolean update(User user);
	User getUserById(String id);
	boolean resetPw(User user);
}
