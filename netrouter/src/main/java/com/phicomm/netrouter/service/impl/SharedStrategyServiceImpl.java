package com.phicomm.netrouter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.netrouter.dao.SharedStrategyMapper;
import com.phicomm.netrouter.model.SharedStrategy;
import com.phicomm.netrouter.service.SharedStrategyService;

@Service
public class SharedStrategyServiceImpl implements SharedStrategyService {

	@Autowired
	private SharedStrategyMapper sharedStrategyMapper;
	@Override
	public SharedStrategy selectByDeviceId(Long deviceId) {
		return sharedStrategyMapper.selectByDeviceId(deviceId);
	}

}
