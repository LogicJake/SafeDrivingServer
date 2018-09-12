package com.scy.driving.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scy.driving.Application;
import com.scy.driving.entity.BusInfo;
import com.scy.driving.entity.Comment;
import com.scy.driving.entity.RideRecord;
import com.scy.driving.repository.BusInfoRepository;
import com.scy.driving.repository.CommentRepository;
import com.scy.driving.repository.RideRecordRepository;
import com.scy.driving.util.exception.TokenErrorException;
import com.scy.driving.util.model.GenericJsonResult;
import com.scy.driving.util.model.HResult;
import com.scy.driving.util.model.PartialArrayList;

@RestController
@RequestMapping(value = "ride")
public class RideController {
	@Autowired
	private BusInfoRepository busInfoRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private RideRecordRepository rideRecordRepository;
	
	@RequestMapping(value = "/getBusInfo", method = RequestMethod.GET)
	public GenericJsonResult<PartialArrayList<BusInfo>> getBusInfo(@RequestParam(value = "destination", required = true) Integer destination) {
		GenericJsonResult<PartialArrayList<BusInfo>> result = new GenericJsonResult<PartialArrayList<BusInfo>>(HResult.S_OK);
		
		Boolean type = isWeekend();
		PartialArrayList<BusInfo> info = busInfoRepository.findAllByWeekendAndDestination(type, destination);
		result.setData(info);
		
		return result;
	}
	
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public GenericJsonResult<String> addComment(HttpServletRequest httpRequest, @RequestParam(value = "rate", required = true) Float rate,
			@RequestParam(value = "comment", required = true) String commentTxt, @RequestParam(value = "tag", required = true) String tag) throws TokenErrorException {
		GenericJsonResult<String> result = new GenericJsonResult<String>(HResult.S_OK);
		
		Long uid = Application.getUserId(httpRequest);
		Comment comment = new Comment(uid, rate, commentTxt, tag);
		commentRepository.save(comment);
		
		return result;
	}
	
	@Transactional
	@RequestMapping(value = "/startRide", method = RequestMethod.GET)
	public GenericJsonResult<Long> startRide(HttpServletRequest httpRequest, @RequestParam(value = "region", required = true) String region) throws TokenErrorException {
		GenericJsonResult<Long> result = new GenericJsonResult<>(HResult.S_OK);
		Long uid = Application.getUserId(httpRequest);
		Long startTime = System.currentTimeMillis();
		
		RideRecord rideRecord = new RideRecord();
		rideRecord.setRegion(region);
		rideRecord.setUid(uid);
		rideRecord.setStartTime(startTime);
		
		RideRecord saveRideRecord = rideRecordRepository.save(rideRecord);
		result.setData(saveRideRecord.getId());

		return result;
	}
	
	@Transactional
	@RequestMapping(value = "/endRide", method = RequestMethod.GET)
	public GenericJsonResult<String> endRide(HttpServletRequest httpRequest, @RequestParam(value = "rideId", required = true) Long rideId) throws TokenErrorException {
		GenericJsonResult<String> result = new GenericJsonResult<>(HResult.S_OK);
		RideRecord rideRecord = rideRecordRepository.findById(rideId).get();
		Long uid = Application.getUserId(httpRequest);
		
		if (uid != rideRecord.getUid()) {
			result.setHr(HResult.E_UNKNOWN);
			return result;
		}
		
		// 行程已结束
		if (0 != rideRecord.getEndTime()) {
			result.setHr(HResult.E_END_RIDE);
			return result;
		}
		
		Long endTime = System.currentTimeMillis();
		rideRecord.setEndTime(endTime);
		rideRecordRepository.save(rideRecord);
		
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
