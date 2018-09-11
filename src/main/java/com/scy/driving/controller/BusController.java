package com.scy.driving.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scy.driving.entity.BusInfo;
import com.scy.driving.repository.BusInfoRepository;
import com.scy.driving.util.model.GenericJsonResult;
import com.scy.driving.util.model.HResult;
import com.scy.driving.util.model.PartialArrayList;

@RestController
@RequestMapping(value = "bus")
public class BusController {
	@Autowired
	private BusInfoRepository busInfoRepository;
	
	@RequestMapping(value = "/getBusInfo", method = RequestMethod.GET)
	public GenericJsonResult<PartialArrayList<BusInfo>> getBusInfo(@RequestParam(value = "destination") Integer destination) {
		GenericJsonResult<PartialArrayList<BusInfo>> result = new GenericJsonResult<PartialArrayList<BusInfo>>(HResult.S_OK);
		
		Boolean type = isWeekend();
		PartialArrayList<BusInfo> info = busInfoRepository.findAllByWeekendAndDestination(type, destination);
		result.setData(info);
		
		return result;
	}
	
	private boolean isWeekend() {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}
}
