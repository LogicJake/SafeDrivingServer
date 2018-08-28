package com.scy.driving.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "verify")
public class Verify {
	@Id
	@Column(name = "uid")
	private Long uid;
	@Column(name = "verify_code")
	private String verifyCode;
	@Column(name = "add_time")
	private Long addTime;
	@Column(name = "expire")
	private Long expire;
	
	public Verify() {
		super();
	}
	
	public Verify(Long uid, String verifyCode, Long addTime, Long expire) {
		super();
		this.uid = uid;
		this.verifyCode = verifyCode;
		this.addTime = addTime;
		this.expire = expire;
	}
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public String getVerifyCode() {
		return verifyCode;
	}
	
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	public Long getAddTime() {
		return addTime;
	}
	
	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}
	
	public Long getExpire() {
		return expire;
	}
	
	public void setExpire(Long expire) {
		this.expire = expire;
	}
	
	@Override
	public String toString() {
		return "Verify [uid=" + uid + ", verifyCode=" + verifyCode + ", addTime=" + addTime + ", expire=" + expire + "]";
	}
	
}
