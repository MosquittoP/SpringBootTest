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
		ResponseEntity entity = null;
		User userinfo = userService.login(user);
		String jwt = jwtUtil.createToken(user);
		logger.debug("login");
		if (userinfo != null) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = SUCCESS;
			result.object = userinfo;
			response.setHeader("Token", jwt);
			response.setHeader("Access-Control-Expose-Headers", "Token");
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			entity = new ResponseEntity<String>("로그인 정보 에러", HttpStatus.NOT_FOUND);
		}
		return entity;
	}
	
	@PostMapping("signup")
	@ApiOperation(value = "회원가입")
	public ResponseEntity<BasicResponse> signup(@RequestBody User user) {
		String id = user.getId();
		User check = userService.getUserById(id);
		logger.debug("signup");
		BasicResponse result = new BasicResponse();
		if (check != null) {
			result.status = false;
			result.data = "id";
			return new ResponseEntity<BasicResponse>(result, HttpStatus.BAD_REQUEST);
		}
		if (userService.insert(user)) {
			result.status = true;
			result.data = SUCCESS;
			return new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		result.status = false;
		result.data = FAIL;
		return new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);			
	}
	
//	@GetMapping("{userno}")
//	public ResponseEntity<BasicResponse> userDetail(@PathVariable int userno) {
//		BasicResponse result = new BasicResponse();
//		return new ResponseEntity<BasicResponse>(result, HttpStatus.BAD_REQUEST);
//	}
	
	@PutMapping("{userno}")
	@ApiOperation(value = "회원정보 수정")
	public ResponseEntity<BasicResponse> updateUser(@RequestBody User user) {
		BasicResponse result = new BasicResponse();
		if (userService.update(user)) {
			User userinfo = userService.getUserByUserno(user.getUserno());
			result.status = true;
			result.data = SUCCESS;
			result.object = userinfo; 
			return new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		result.status = false;
		result.data = FAIL; 
		return new ResponseEntity<BasicResponse>(result, HttpStatus.BAD_REQUEST);
	}
}
