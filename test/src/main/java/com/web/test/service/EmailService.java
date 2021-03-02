package com.web.test.service;

import java.util.List;

import com.web.test.model.Email;

public interface EmailService {
	List<Email> getEmail();
	Email getEmailByEmailno(int emailno);
	boolean insert(Email email);
	boolean open(String pw);
}
