package com.phicomm.netrooter.service;

import com.phicomm.netrooter.model.NrDevice;

public interface NrDeviceService {
	NrDevice getDeviceByPrimaryKey(long deviceid);
}
