package com.scy.driving.controller;

import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scy.driving.Application;
import com.scy.driving.entity.Accelerometer;
import com.scy.driving.entity.BusInfo;
import com.scy.driving.entity.Comment;
import com.scy.driving.entity.Gyroscope;
import com.scy.driving.entity.RideRecord;
import com.scy.driving.repository.AccelerometerRepository;
import com.scy.driving.repository.BusInfoRepository;
import com.scy.driving.repository.CommentRepository;
import com.scy.driving.repository.GyroscopeRepository;
import com.scy.driving.repository.RideRecordRepository;
import com.scy.driving.service.PredictService;
import com.scy.driving.util.Utility;
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
	@Autowired
	private AccelerometerRepository accelerometerRepository;
	@Autowired
	private GyroscopeRepository gyroscopeRepository;
	@Autowired
	private PredictService predictService;
	private final int ACCELEROMETER = 0;
	private final int GYROSCOPE = 1;
	
	@RequestMapping(value = "/getBusInfo", method = RequestMethod.GET)
	public GenericJsonResult<PartialArrayList<BusInfo>> getBusInfo(@RequestParam(value = "destination", required = true) Integer destination,
			@RequestParam(value = "date", required = true) Long date) {
		GenericJsonResult<PartialArrayList<BusInfo>> result = new GenericJsonResult<PartialArrayList<BusInfo>>(HResult.S_OK);
		
		Boolean type = isWeekend(date);
		PartialArrayList<BusInfo> info = busInfoRepository.findAllByWeekendAndDestination(type, destination);
		// 自定义Comparator对象，自定义排序
		Comparator<BusInfo> c = new Comparator<BusInfo>() {
			@Override
			public int compare(BusInfo o1, BusInfo o2) {
				return o1.getTime().compareTo(o2.getTime());
			}
		};
		
		info.sort(c);
		result.setData(info);
		
		return result;
	}
	
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public GenericJsonResult<String> addComment(@RequestParam(value = "rate", required = true) Float rate, @RequestParam(value = "comment", required = true) String commentTxt,
			@RequestParam(value = "rideId", required = true) Long rideId) {
		GenericJsonResult<String> result = new GenericJsonResult<String>(HResult.S_OK);
		
		Comment comment = new Comment(rideId, rate, commentTxt);
		commentRepository.save(comment);
		
		return result;
	}
	
	@Transactional
	@RequestMapping(value = "/addData", method = RequestMethod.POST)
	public GenericJsonResult<String> addData(@RequestParam(value = "rideId", required = true) Long rideId, @RequestParam(value = "data", required = true) String data,
			@RequestParam(value = "type", required = true) int type) throws TokenErrorException {
		GenericJsonResult<String> result = new GenericJsonResult<>(HResult.S_OK);
		JSONArray jsonData = JSON.parseArray(data);
		
		switch (type) {
			case ACCELEROMETER:
				addAccelerometer(rideId, jsonData);
				break;
			case GYROSCOPE:
				addGyroscope(rideId, jsonData);
				break;
			default:
				result.setHr(HResult.E_PARAMETER);
				return result;
		}
		
		return result;
	}
	
	private void addAccelerometer(Long rideId, JSONArray data) {
		List<Accelerometer> accelerometers = new LinkedList<>();
		
		for (int i = 0; i < data.size(); i++) {
			System.out.println(data.get(i));
			JSONObject tmp = data.getJSONObject(i);
			Float x = tmp.getFloat("x");
			Float y = tmp.getFloat("y");
			Float z = tmp.getFloat("z");
			Long time = tmp.getLong("time");
			Accelerometer accelerometer = new Accelerometer(rideId, x, y, z, time);
			accelerometers.add(accelerometer);
		}
		accelerometerRepository.saveAll(accelerometers);
	}
	
	private void addGyroscope(Long rideId, JSONArray data) {
		List<Gyroscope> gyroscopes = new LinkedList<>();
		for (int i = 0; i < data.size(); i++) {
			JSONObject tmp = data.getJSONObject(i);
			Float x = tmp.getFloat("x");
			Float y = tmp.getFloat("y");
			Float z = tmp.getFloat("z");
			Long time = tmp.getLong("time");
			Gyroscope gyroscope = new Gyroscope(rideId, x, y, z, time);
			gyroscopes.add(gyroscope);
		}
		gyroscopeRepository.saveAll(gyroscopes);
	}
	
	@Transactional
	@RequestMapping(value = "/startRide", method = RequestMethod.GET)
	public GenericJsonResult<Long> startRide(HttpServletRequest httpRequest, @RequestParam(value = "region", required = true) String region,
			@RequestParam(value = "busTag", required = false) String busTag) throws TokenErrorException {
		GenericJsonResult<Long> result = new GenericJsonResult<>(HResult.S_OK);
		Long uid = Application.getUserId(httpRequest);
		Long startTime = System.currentTimeMillis();
		
		RideRecord rideRecord = new RideRecord();
		rideRecord.setRegion(region);
		rideRecord.setUid(uid);
		rideRecord.setStartTime(startTime);
		
		if (!Utility.isEmptyString(busTag)) {
			rideRecord.setBusTag(busTag);
		}
		
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
		
		predictService.predict(rideId);
		return result;
	}
	
	private boolean isWeekend(Long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}
}
