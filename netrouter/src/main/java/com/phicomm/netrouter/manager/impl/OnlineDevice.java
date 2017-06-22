package com.phicomm.netrouter.manager.impl;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.phicomm.netrouter.service.NRService;

@Component
public class OnlineDevice {
	@Autowired
	private NRService nrService;
	private long deviceId;
	private Date latestOnlineTime;
	private TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			nrService.offLine(deviceId);
		}
	};
	private Timer timer;
	private int time;
	
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public Date getLatestonlinetime() {
		return latestOnlineTime;
	}
	public void setLatestonlinetime(Date latestOnlineTime) {
		this.latestOnlineTime = latestOnlineTime;
	}
	
	public synchronized void timer(int time){
		this.time = time;
		timer = new Timer();
		timer.schedule(task, time);
	}
	
	public void refresh(){
		timer(time);
	}
}
