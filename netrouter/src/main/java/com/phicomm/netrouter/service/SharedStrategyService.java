package com.phicomm.netrouter.service;

import com.phicomm.netrouter.model.SharedStrategy;

public interface SharedStrategyService {
	SharedStrategy selectByDeviceId(Long deviceId);
}
