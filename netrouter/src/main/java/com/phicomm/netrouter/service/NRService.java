package com.phicomm.netrouter.service;

import com.phicomm.netrouter.model.DevNtwTopo;
import com.phicomm.netrouter.model.DeviceWarning;
import com.phicomm.netrouter.model.IotDevice;

public interface NRService {
	IotDevice getDeviceByPrimaryKey(long deviceid);
	int insertManufactureInfo(IotDevice iotDevice);
	
	int insertBwInfo(long deviceId,String ipAddr,long uplinkBw,long downlinkBw);
	
	int getDeviceId(String manufacure,String manufacureSN);
	
	int insertDevNtwTopo(DevNtwTopo record);

	int insertDeviceWarning(DeviceWarning record);
	
	int updateDevice(IotDevice iotDevice);
	
	boolean isOnline(Long deviceid);

	void offLine(long deviceId);

}
