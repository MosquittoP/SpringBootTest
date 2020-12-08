package com.web.test.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.test.config.BasicResponse;
import com.web.test.config.JwtUtil;
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
	
	@PostMapping("login")
	@ApiOperation(value = "로그인")
	public Object login(@RequestBody User user, HttpServletResponse response) {
		User userinfo = userService.login(user);
		String jwt = jwtUtil.createToken(user);
		logger.debug("login");
		if (userinfo != null) {
			response.setHeader("Token", jwt);
			response.setHeader("Access-Control-Expose-Headers", "Token");
			return new ResponseEntity<User>(userinfo, HttpStatus.OK);
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
		if (userService.update(user)) {
			User userinfo = userService.getUserByUserno(user.getUserno());
			return new ResponseEntity<User>(userinfo, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
}
