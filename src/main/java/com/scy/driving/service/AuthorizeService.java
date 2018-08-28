package com.scy.driving.service;

import java.io.UnsupportedEncodingException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.scy.driving.entity.Token;
import com.scy.driving.repository.TokenRepository;

@Service
public class AuthorizeService {
	@Autowired
	private TokenRepository tokenRepository;
	
	public final static int SUCCESS = 0;
	public final static int FAILURE = -1;
	public final static int EXPIRED = -2;
	// 过期时间
	public static final long SIX_MONTH_MILLIS = 7 * 24 * 60 * 60 * 1000L;
	
	// 存放token的请求头名称
	public static final String AUTHORIZATION_HEADER = "Authorization-Driving";
	
	private static final String ACCESS_TOKEN = "nuaa-driving";
	
	public int auth(HttpServletRequest request) throws IllegalArgumentException, UnsupportedEncodingException {
		
		String token = request.getHeader(AUTHORIZATION_HEADER);
		if (token == null || token.isEmpty()) {
			return FAILURE;
		}
		
		try {
			DecodedJWT decodedJWT = JWT.decode(token);
			Algorithm algorithm = Algorithm.HMAC256(ACCESS_TOKEN);
			algorithm.verify(decodedJWT);
			
			Long userId = Long.valueOf(decodedJWT.getId());
			request.setAttribute("userId", userId);
			
			boolean isExist = tokenRepository.existsByUidAndToken(userId, token);
			if (!isExist) {
				return FAILURE;
			}
			
			// 检查token有没有过期
			if (System.currentTimeMillis() >= decodedJWT.getExpiresAt().getTime()) {
				return EXPIRED;
			}
		} catch (Exception e) {
			return FAILURE;
		}
		
		return SUCCESS;
	}
	
	@Transactional
	public String createToken(Long userId) throws IllegalArgumentException, UnsupportedEncodingException {
		Algorithm algorithm = Algorithm.HMAC256(ACCESS_TOKEN); // 加密算法
		String tokenStr = JWT.create().withJWTId(String.valueOf(userId)).withExpiresAt(new Date(System.currentTimeMillis() + SIX_MONTH_MILLIS)).sign(algorithm);
		Token token = new Token(userId, tokenStr);
		tokenRepository.save(token);
		return tokenStr;
	}
	
	@Transactional
	public void deleteToken(Long userId) {
		tokenRepository.deleteById(userId);
	}
}
