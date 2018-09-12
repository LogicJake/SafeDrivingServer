package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;

import com.scy.driving.entity.RideRecord;

public interface RideRecordRepository extends CrudRepository<RideRecord, Long> {
	RideRecord findByRideId(String rideId);
}
