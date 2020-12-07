package com.web.test.model;

public class User {
	int userno;
	String id, pw, email, tel;
	
	public User() {}

	public User(int userno, String id, String pw, String email, String tel) {
		super();
		this.userno = userno;
		this.id = id;
		this.pw = pw;
		this.email = email;
		this.tel = tel;
	}

	public int getUserno() {
		return userno;
	}

	public void setUserno(int userno) {
		this.userno = userno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "UserDAO [userno=" + userno + ", id=" + id + ", pw=" + pw + ", email=" + email + ", tel=" + tel + "]";
	}
}
