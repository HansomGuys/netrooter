package com.phicomm.netrouter.service;

import java.util.List;

import com.phicomm.netrouter.model.LiveInfo;;

public interface LiveInfoService {
	
	LiveInfo getDeviceByPrimaryKey(long resourceId);
	
	List<LiveInfo> getResourceId(LiveInfo liveInfo);
	
	void insertNewItem(LiveInfo liveInfo);
	
	int getMaxResId();
}
