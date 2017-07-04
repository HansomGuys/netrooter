package com.phicomm.netrouter.dao;

import com.phicomm.netrouter.model.SharedStrategy;

public interface SharedStrategyMapper {
	SharedStrategy selectByDeviceId(Long deviceId);
}
