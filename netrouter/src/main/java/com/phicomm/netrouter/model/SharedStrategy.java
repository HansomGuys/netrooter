package com.phicomm.netrouter.model;

import java.util.Date;

public class SharedStrategy {
	private Date startSharedTime;
	private Date endSharedTime;
	private int sharedBW;
	public Date getStartSharedTime() {
		return startSharedTime;
	}
	public void setStartSharedTime(Date startSharedTime) {
		this.startSharedTime = startSharedTime;
	}
	public Date getEndSharedTime() {
		return endSharedTime;
	}
	public void setEndSharedTime(Date endSharedTime) {
		this.endSharedTime = endSharedTime;
	}
	public int getSharedBW() {
		return sharedBW;
	}
	public void setSharedBW(int sharedBW) {
		this.sharedBW = sharedBW;
	}
}
