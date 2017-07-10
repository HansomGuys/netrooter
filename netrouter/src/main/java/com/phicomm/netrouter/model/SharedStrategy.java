package com.phicomm.netrouter.model;

public class SharedStrategy {
//	private Date startSharedTime;
//	private Date endSharedTime;
	private int sharedBW;
	private boolean sharedLock;
	/*public Date getStartSharedTime() {
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
	}*/
	public int getSharedBW() {
		return sharedBW;
	}
	public void setSharedBW(int sharedBW) {
		this.sharedBW = sharedBW;
	}
	public boolean isSharedLock() {
		return sharedLock;
	}
	public void setSharedLock(boolean sharedLock) {
		this.sharedLock = sharedLock;
	}
}
