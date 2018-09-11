package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;

import com.scy.driving.entity.BusInfo;
import com.scy.driving.util.model.PartialArrayList;

public interface BusInfoRepository extends CrudRepository<BusInfo, Long> {
	PartialArrayList<BusInfo> findAllByWeekendAndDestination(Boolean type, Integer destination);
	
}
