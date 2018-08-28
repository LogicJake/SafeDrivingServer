package com.scy.driving.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class Token {
	@Id
	@Column(name = "uid")
	private Long uid;
	@Column(name = "token")
	private String token;
	
	public Token() {
		super();
	}
	
	public Token(Long uid, String token) {
		super();
		this.uid = uid;
		this.token = token;
	}
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
}
