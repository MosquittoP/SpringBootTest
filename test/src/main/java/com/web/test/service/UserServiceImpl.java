package com.web.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.test.model.User;
import com.web.test.repo.UserDAO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDao;
	
	@Override
	public boolean insert(User user) {
		return userDao.insert(user) == 1;
	}

	@Override
	public User login(User user) {
		return userDao.login(user);
	}

	@Override
	public User getUserByUserno(int userno) {
		return userDao.getUserByUserno(userno);
	}

	@Override
	public List<User> selectUser() {
		return userDao.selectUser();
	}

	@Override
	public boolean update(User user) {
		return userDao.update(user) == 1;
	}

	@Override
	public User getUserById(String id) {
		return userDao.getUserById(id);
	}

	@Override
	public boolean resetPw(User user) {
		return userDao.resetPw(user) == 1;
	}

}
