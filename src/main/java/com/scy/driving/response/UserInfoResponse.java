package com.scy.driving.response;

import com.scy.driving.entity.User;

public class UserInfoResponse {
	private String token;
	private String email;
	private Long uid;
	private String userName;
	private String avatarUrl;
	
	public UserInfoResponse(User user) {
		this.email = user.getEmail();
		this.uid = user.getUid();
		this.userName = user.getUserName();
		this.avatarUrl = user.getAvatarUrl();
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
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
