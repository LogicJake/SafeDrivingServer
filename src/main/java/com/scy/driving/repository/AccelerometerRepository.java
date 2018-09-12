package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;

import com.scy.driving.entity.Accelerometer;

public interface AccelerometerRepository extends CrudRepository<Accelerometer, Long> {
	
}
