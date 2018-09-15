package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scy.driving.entity.BusInfo;
import com.scy.driving.util.model.PartialArrayList;

@Repository
public interface BusInfoRepository extends CrudRepository<BusInfo, Long> {
	PartialArrayList<BusInfo> findAllByWeekendAndDestination(Boolean type, Integer destination);
	
}
