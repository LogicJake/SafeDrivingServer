package com.scy.driving.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "ride_id")
	private Long rideId;
	@Column(name = "rate")
	private Float rate;
	@Column(name = "suggestion")
	private String suggestion;
	
	public Comment(Long rideId, Float rate, String suggestion) {
		super();
		this.rideId = rideId;
		this.rate = rate;
		this.suggestion = suggestion;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getRideId() {
		return rideId;
	}
	
	public void setRideId(Long rideId) {
		this.rideId = rideId;
	}
	
	public Float getRate() {
		return rate;
	}
	
	public void setRate(Float rate) {
		this.rate = rate;
	}
	
	public String getSuggestion() {
		return suggestion;
	}
	
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
}
