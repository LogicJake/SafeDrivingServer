package com.scy.driving.response;

import com.scy.driving.entity.User;

public class LoginResponse {
	private String token;
	private String email;
	private Long uid;
	private String userName;
	
	public LoginResponse(User user) {
		this.email = user.getEmail();
		this.uid = user.getUid();
		this.userName = user.getUserName();
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "LoginResponse [token=" + token + ", email=" + email + ", uid=" + uid + ", userName=" + userName + "]";
	}
	
}
