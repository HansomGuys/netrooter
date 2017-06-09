package com.phicomm.netrouter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.netrouter.dao.IotDeviceMapper;
import com.phicomm.netrouter.model.IotDevice;
import com.phicomm.netrouter.service.IotDeviceService;

@Service("nrDeviceService")
public class NrDeviceServiceImpl implements IotDeviceService {

	@Autowired
	private IotDeviceMapper nrDeviceMapper;
	@Override
	public IotDevice getDeviceByPrimaryKey(long deviceid) {
		// TODO Auto-generated method stub
		return nrDeviceMapper.selectByPrimaryKey(deviceid);
	}

}
