package com.scy.driving.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scy.driving.Application;
import com.scy.driving.entity.Token;
import com.scy.driving.entity.User;
import com.scy.driving.repository.TokenRepository;
import com.scy.driving.repository.UserRepository;
import com.scy.driving.response.UserInfoResponse;
import com.scy.driving.service.AuthorizeService;
import com.scy.driving.util.Utility;
import com.scy.driving.util.exception.TokenErrorException;
import com.scy.driving.util.model.GenericJsonResult;
import com.scy.driving.util.model.HResult;

@RestController
@RequestMapping(value = "account")
public class AccountController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorizeService authorizeService;
	@Autowired
	private Environment env;
	@Autowired
	private TokenRepository tokenRepository;
	private String[] avatarSuffix = { "jpg", "jpeg", "png" };
	
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
	
	@Transactional
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public GenericJsonResult<String> logout(HttpServletRequest httpRequest) throws TokenErrorException {
		Long userId = Application.getUserId(httpRequest);
		authorizeService.deleteToken(userId);
		return new GenericJsonResult<>(HResult.S_OK);
	}
	
	@Transactional
	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public GenericJsonResult<String> getToken(@RequestParam(value = "name", required = true) String userName) throws IllegalArgumentException, UnsupportedEncodingException {
		GenericJsonResult<String> result = new GenericJsonResult<>(HResult.S_OK);
		
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			result.setHr(HResult.E_NO_USERNAME);
			return result;
		}
		
		Long uid = user.getUid();
		Token token = tokenRepository.findByUid(uid);
		
		if (token == null || authorizeService.auth(token.getToken()) != AuthorizeService.SUCCESS) {
			String tokenTxt = authorizeService.createToken(uid);
			result.setData(tokenTxt);
		} else {
			result.setData(token.getToken());
		}
		
		return result;
	}
	
	@Transactional
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public GenericJsonResult<String> modifyPassword(HttpServletRequest httpRequest, @RequestParam(value = "password", required = true) String newPassword)
			throws TokenErrorException {
		GenericJsonResult<String> result = new GenericJsonResult<>(HResult.S_OK);
		Long uid = Application.getUserId(httpRequest);
		User user = userRepository.findByUid(uid);
		if (!Utility.isEmptyString(newPassword)) {
			user.setPassword(newPassword);
			userRepository.save(user);
		} else {
			result.setHr(HResult.E_INVALID_PARAMETERS);
		}
		return result;
	}
	
	@Transactional
	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	public GenericJsonResult<String> uploadAvator(HttpServletRequest httpRequest, @RequestParam(value = "file", required = true) MultipartFile file) throws TokenErrorException {
		GenericJsonResult<String> result = new GenericJsonResult<>(HResult.S_OK);
		
		String uploadDir = env.getProperty("upload.avatar.location");
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			// 检查后缀是否符合要求
			boolean isValidSuffix = false;
			for (int i = 0; i < avatarSuffix.length; i++) {
				if (suffix.equalsIgnoreCase(avatarSuffix[i])) {
					isValidSuffix = true;
				}
			}
			if (!isValidSuffix) {
				result.setHr(HResult.E_AVATAR_SUFFIX);
				return result;
			}
			
			String defineName = UUID.randomUUID() + "." + suffix;
			File saveFile = new File(uploadDir + defineName);
			System.out.println(defineName);
			try {
				file.transferTo(saveFile);
			} catch (Exception e) {
				result.setHr(HResult.E_AVATAR_SAVE);
				return result;
			}
			
			// 虚拟路径
			String avatarUrl = "avatar/" + defineName;
			Long uid = Application.getUserId(httpRequest);
			User user = userRepository.findByUid(uid);
			user.setAvatarUrl(avatarUrl);
			userRepository.save(user);
			result.setData(avatarUrl);
		}
		return result;
	}
}
