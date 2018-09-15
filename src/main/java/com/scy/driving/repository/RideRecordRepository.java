package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scy.driving.entity.RideRecord;

@Repository
public interface RideRecordRepository extends CrudRepository<RideRecord, Long> {
}
