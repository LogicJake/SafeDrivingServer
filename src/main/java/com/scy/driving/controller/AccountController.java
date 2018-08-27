package com.scy.driving.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scy.driving.entity.User;
import com.scy.driving.repository.UserRepository;
import com.scy.driving.response.UserInfoResponse;
import com.scy.driving.service.AuthorizeService;
import com.scy.driving.util.Utility;
import com.scy.driving.util.model.GenericJsonResult;
import com.scy.driving.util.model.HResult;

@RestController
@RequestMapping(value = "account")
public class AccountController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorizeService authorizeService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public GenericJsonResult<UserInfoResponse> login(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) throws IllegalArgumentException, UnsupportedEncodingException {
		GenericJsonResult<UserInfoResponse> result = new GenericJsonResult<>(HResult.S_OK);
		
		if (Utility.isEmptyString(userName) | Utility.isEmptyString(password)) {
			result.setHr(HResult.E_ACCOUNT_PASSWORD_NULL);
			return result;
		}
		
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			result.setHr(HResult.E_NO_USERNAME);
			return result;
		}
		if (!password.equals(user.getPassword())) {
			result.setHr(HResult.E_INCORRECT_PASSWORD);
			return result;
		}
		
		UserInfoResponse loginResponse = new UserInfoResponse(user);
		String token = authorizeService.createToken(user.getUid());
		loginResponse.setToken(token);
		result.setData(loginResponse);
		return result;
	}
	
	@Transactional
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public GenericJsonResult<UserInfoResponse> signUp(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) throws IllegalArgumentException, UnsupportedEncodingException {
		GenericJsonResult<UserInfoResponse> result = new GenericJsonResult<>(HResult.S_OK);
		
		boolean isExist = userRepository.existsByUserName(userName);
		if (isExist) {
			result.setHr(HResult.E_DUPLICATED_USERNAME);
			return result;
		}
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		userRepository.save(user);
		
		user = userRepository.findByUserNameAndPassword(userName, password);
		UserInfoResponse signUpResponse = new UserInfoResponse(user);
		String token = authorizeService.createToken(user.getUid());
		signUpResponse.setToken(token);
		result.setData(signUpResponse);
		return result;
	}
}
