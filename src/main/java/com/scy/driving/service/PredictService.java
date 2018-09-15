package com.scy.driving.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scy.driving.entity.Accelerometer;
import com.scy.driving.entity.Gyroscope;
import com.scy.driving.entity.RideRecord;
import com.scy.driving.repository.AccelerometerRepository;
import com.scy.driving.repository.GyroscopeRepository;
import com.scy.driving.repository.RideRecordRepository;

@Service
public class PredictService {
	@Autowired
	private GyroscopeRepository gyroscopeRepository;
	@Autowired
	private AccelerometerRepository accelerometerRepository;
	@Autowired
	private RideRecordRepository recordRepository;
	
	/*
	 * 1.变速过快
	 * 2.转弯过快
	 * 3.变道过快
	 */
	public void predict(Long rideId) {
		Set<Integer> res = new HashSet<>();
		List<Gyroscope> gyroscopes = gyroscopeRepository.findAllByRideId(rideId);
		List<Accelerometer> accelerometers = accelerometerRepository.findAllByRideId(rideId);
		
		for (Accelerometer accelerometer : accelerometers) {
			double am = Math.sqrt(accelerometer.getX() * accelerometer.getX() + accelerometer.getY() * accelerometer.getY() + accelerometer.getZ() * accelerometer.getZ());
			if (am > 13.24) {
				res.add(1);
			}
		}
		
		long startTime = 0;
		boolean flag = true;
		boolean flag1 = true;
		for (Gyroscope gyroscope : gyroscopes) {
			double gc = Math.sqrt(gyroscope.getX() * gyroscope.getX() + gyroscope.getY() * gyroscope.getY() + gyroscope.getZ() * gyroscope.getZ());
			if (gc > 1.66) {
				// 记录第一次gc>1.66的开始时间，第二次gc>1.66不修改
				if (flag) {
					startTime = gyroscope.getTime();
					flag = false;
				}
				// 计算持续时间换算成秒
				int c = (int) ((gyroscope.getTime() - startTime) / 1000);
				if (c > 2) {
					res.add(2);
				}
			}
			
			// 如果gc<=1.66则把flag=true;到下次gc>1.66的时候又可以修改
			if (gc <= 1.66) {
				flag = true;
			}
			
			if (gc > 1.1) {
				// 记录第一次gc>1.1的开始时间，第二次不修改
				if (flag1) {
					startTime = gyroscope.getTime();
					flag1 = false;
				}
				// 计算持续时间换算成秒
				int c = (int) ((gyroscope.getTime() - startTime) / 1000);
				if (c <= 2) {
					res.add(3);
				}
			}
			
			// 如果gc>=1.1则把flag=true;到下次gc>=1.1的时候又可以修改
			if (gc >= 1.1) {
				flag = true;
			}
			
		}
		String predictTag = "";
		
		for (Integer i : res) {
			predictTag += Integer.toString(i);
		}
		
		RideRecord rideRecord = recordRepository.findById(rideId).get();
		rideRecord.setPredictTag(predictTag);
		recordRepository.save(rideRecord);
	}
}
