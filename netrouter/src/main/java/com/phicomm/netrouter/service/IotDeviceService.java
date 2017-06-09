package com.phicomm.netrouter.service;

import com.phicomm.netrouter.model.IotDevice;

public interface IotDeviceService {
	IotDevice getDeviceByPrimaryKey(long deviceid);
}
