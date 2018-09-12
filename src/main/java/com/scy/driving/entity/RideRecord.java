package com.scy.driving.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ride_record")
public class RideRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "uid")
	private Long uid;
	@Column(name = "region")
	private String region;
	@Column(name = "start_time")
	private Long startTime;
	@Column(name = "end_time")
	private Long endTime = 0L;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public Long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	
	public Long getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
}
