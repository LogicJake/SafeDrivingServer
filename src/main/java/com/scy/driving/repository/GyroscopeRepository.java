package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;

import com.scy.driving.entity.Gyroscope;

public interface GyroscopeRepository extends CrudRepository<Gyroscope, Long> {
	
}
