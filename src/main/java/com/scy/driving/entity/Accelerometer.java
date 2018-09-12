package com.scy.driving.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accelerometer")
public class Accelerometer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "ride_id")
	private Long rideId;
	@Column(name = "x")
	private Float x;
	@Column(name = "y")
	private Float y;
	@Column(name = "z")
	private Float z;
	@Column(name = "time")
	private Long time;
	
	public Accelerometer(Long rideId, Float x, Float y, Float z, Long time) {
		super();
		this.rideId = rideId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.time = time;
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
	
	public Float getX() {
		return x;
	}
	
	public void setX(Float x) {
		this.x = x;
	}
	
	public Float getY() {
		return y;
	}
	
	public void setY(Float y) {
		this.y = y;
	}
	
	public Float getZ() {
		return z;
	}
	
	public void setZ(Float z) {
		this.z = z;
	}
	
	public Long getTime() {
		return time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}
}
