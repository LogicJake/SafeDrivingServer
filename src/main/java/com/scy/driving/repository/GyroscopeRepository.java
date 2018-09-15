package com.scy.driving.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scy.driving.entity.Gyroscope;

@Repository
public interface GyroscopeRepository extends CrudRepository<Gyroscope, Long> {
	List<Gyroscope> findAllByRideId(Long rideId);
}
