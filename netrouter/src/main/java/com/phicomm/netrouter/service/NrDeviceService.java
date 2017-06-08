package com.phicomm.netrouter.service;

import com.phicomm.netrouter.model.NrDevice;

public interface NrDeviceService {
	NrDevice getDeviceByPrimaryKey(long deviceid);
}
