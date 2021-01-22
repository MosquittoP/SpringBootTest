package com.web.test.controller;

import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.test.component.EmailSender;
import com.web.test.component.JwtUtil;
import com.web.test.model.Email;
import com.web.test.model.User;
import com.web.test.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("login")
	@ApiOperation(value = "로그인")
	public Object login(@RequestBody User user, HttpServletResponse response) {
		User userinfo = userService.getUserById(user.getId());
		String jwt = jwtUtil.createToken(user);
		logger.debug("login");
		if (userinfo != null) {
			if (passwordEncoder.matches(user.getPw(), userinfo.getPw())) {
				response.setHeader("Token", jwt);
				response.setHeader("Access-Control-Expose-Headers", "Token");
				return new ResponseEntity<User>(userinfo, HttpStatus.OK);				
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);				
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("signup")
	@ApiOperation(value = "회원가입")
	public ResponseEntity<String> signup(@RequestBody User user) {
		String id = user.getId();
		User check = userService.getUserById(id);
		logger.debug("signup");
		if (check != null) {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
		String encode = passwordEncoder.encode(user.getPw());
		user.setPw(encode);
		if (userService.insert(user)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);			
	}
	
	@GetMapping("logout")
	@ApiOperation(value = "로그아웃")
	public ResponseEntity<String> logout(HttpServletResponse response) {
		response.setHeader("Token", "");
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@PutMapping("{userno}")
	@ApiOperation(value = "회원정보 수정")
	public Object updateUser(@RequestBody User user) {
		String encode = passwordEncoder.encode(user.getPw());
		user.setPw(encode);
		if (userService.update(user)) {
			User userinfo = userService.getUserByUserno(user.getUserno());
			return new ResponseEntity<User>(userinfo, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("pwfind")
	@ApiOperation(value = "비밀번호 찾기")
	public Object resetPw(@RequestBody User user) {
		String tmp = "";
		Random rand = new Random();
		for (int i = 0; i < 6; i++)
			tmp += Integer.toString(rand.nextInt(10));
		String encode = passwordEncoder.encode(tmp);
		user.setPw(encode);
		if (userService.resetPw(user)) {
			Email email = new Email();
			email.setSubject("[test] 임시 비밀번호가 발급되었습니다.");
			email.setReceiver(user.getEmail());
			email.setContent("<html><body><p>안녕하세요.</p><p>" + user.getId() + "님의 임시 비밀번호는</p><p><strong>" + tmp + "</strong>입니다.</p></body></html>");
			emailSender.sendEmail(email);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
}
