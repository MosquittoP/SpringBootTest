package com.web.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.test.model.Email;
import com.web.test.repo.EmailDAO;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailDAO emailDao;
	
	@Override
	public List<Email> getEmail() {
		return emailDao.getEmail();
	}

	@Override
	public Email getEmailByEmailno(int emailno) {
		return emailDao.getEmailByEmailno(emailno);
	}

	@Override
	public boolean insert(Email email) {
		return emailDao.insert(email) == 1;
	}

	@Override
	public boolean open(String pw) {
		return emailDao.open(pw) == 1;
	}

}
