package com.scy.driving.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scy.driving.entity.Accelerometer;

@Repository
public interface AccelerometerRepository extends CrudRepository<Accelerometer, Long> {
	List<Accelerometer> findAllByRideId(Long rideId);
}
