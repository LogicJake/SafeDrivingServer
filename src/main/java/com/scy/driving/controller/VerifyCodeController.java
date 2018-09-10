package com.scy.driving.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scy.driving.Application;
import com.scy.driving.entity.Verify;
import com.scy.driving.repository.VerifyRepository;
import com.scy.driving.service.MailService;
import com.scy.driving.util.exception.TokenErrorException;
import com.scy.driving.util.model.GenericJsonResult;
import com.scy.driving.util.model.HResult;

@RestController
@RequestMapping(value = "verifyCode")
public class VerifyCodeController {
	private static final int codeLength = 4;
	private static final long FIVE_MINUTE_MILLIS = 5 * 60 * 1000L;
	private static final String VERTFY_MAIL_MESSAGE = "您的此次验证码为：%s，如果不是本人操作请忽略。";
	private static final String TITLE = "邮箱验证";
	@Autowired
	private MailService mailService;
	@Autowired
	private VerifyRepository verifyRepository;
	
	@Transactional
	@RequestMapping(value = "/sendCode", method = RequestMethod.POST)
	public GenericJsonResult<String> sendCode(HttpServletRequest httpRequest, @RequestParam(value = "toMail", required = true) String toMail) throws TokenErrorException {
		GenericJsonResult<String> result = new GenericJsonResult<>(HResult.S_OK);
		Long uid = Application.getUserId(httpRequest);
		String code = createCode();
		String content = String.format(VERTFY_MAIL_MESSAGE, code);
		Long addTime = System.currentTimeMillis();
		Long expireTime = addTime + FIVE_MINUTE_MILLIS;
		
		try {
			mailService.sendMail(toMail, TITLE, content);
		} catch (Exception e) {
			System.out.println(e);
			result.setHr(HResult.E_VERIFY_SEND);
			return result;
		}
		
		Verify verify = new Verify(uid, code, addTime, expireTime);
		System.out.println(verify);
		verifyRepository.save(verify);
		return result;
	}
	
	@Transactional
	@RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
	public GenericJsonResult<String> verifyCode(HttpServletRequest httpRequest, @RequestParam(value = "code", required = true) String code) throws TokenErrorException {
		GenericJsonResult<String> result = new GenericJsonResult<>(HResult.S_OK);
		Long uid = Application.getUserId(httpRequest);
		Verify verify = verifyRepository.findByUid(uid);
		
		// 验证码错误
		if (!code.equals(verify.getVerifyCode())) {
			result.setHr(HResult.E_CHECKCODE_VERIFIED_FAILED);
			return result;
		}
		
		// 过期失效
		if (verify.getExpire() < System.currentTimeMillis()) {
			result.setHr(HResult.E_CHECKCODE_INVALID);
			verifyRepository.deleteById(uid);
			return result;
		}
		
		// 删除数据库中验证码
		verifyRepository.deleteById(uid);
		return result;
	}
	
	private String createCode() {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < codeLength; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
}
