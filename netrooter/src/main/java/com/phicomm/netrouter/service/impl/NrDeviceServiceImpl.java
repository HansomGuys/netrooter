package com.phicomm.netrouter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.netrouter.dao.NrDeviceMapper;
import com.phicomm.netrouter.model.NrDevice;
import com.phicomm.netrouter.service.NrDeviceService;

@Service("nrDeviceService")
public class NrDeviceServiceImpl implements NrDeviceService {

	@Autowired
	private NrDeviceMapper nrDeviceMapper;
	@Override
	public NrDevice getDeviceByPrimaryKey(long deviceid) {
		// TODO Auto-generated method stub
		return nrDeviceMapper.selectByPrimaryKey(deviceid);
	}

}
