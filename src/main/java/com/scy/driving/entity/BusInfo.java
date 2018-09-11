package com.scy.driving.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shuttle_info")
public class BusInfo {
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "type")
	private Boolean weekend;
	@Column(name = "time")
	private String time;
	@Column(name = "car_num")
	private Integer carNum;
	@Column(name = "east")
	private Boolean east;
	@Column(name = "lancui")
	private Boolean lancui;
	@Column(name = "destination")
	private Integer destination;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean getWeekend() {
		return weekend;
	}
	
	public void setWeekend(Boolean weekend) {
		this.weekend = weekend;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public Integer getCarNum() {
		return carNum;
	}
	
	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}
	
	public Boolean getEast() {
		return east;
	}
	
	public void setEast(Boolean east) {
		this.east = east;
	}
	
	public Boolean getLancui() {
		return lancui;
	}
	
	public void setLancui(Boolean lancui) {
		this.lancui = lancui;
	}
	
	public Integer getDestination() {
		return destination;
	}
	
	public void setDestination(Integer destination) {
		this.destination = destination;
	}
	
}
