package com.phicomm.netrouter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.netrouter.dao.DevLiveResourceMapper;
import com.phicomm.netrouter.dao.IotDeviceMapper;
import com.phicomm.netrouter.dao.LiveInfoMapper;
import com.phicomm.netrouter.model.DevLiveResource;
import com.phicomm.netrouter.model.DevLiveResourceKey;
import com.phicomm.netrouter.model.IotDevice;
import com.phicomm.netrouter.model.LiveInfo;
import com.phicomm.netrouter.service.DevLiveResourceService;
import com.phicomm.netrouter.service.IotDeviceService;
import com.phicomm.netrouter.service.LiveInfoService;

@Service("devLiveResourceService")
public class DevLiveResourceServiceImpl implements DevLiveResourceService {

	@Autowired
	private DevLiveResourceMapper devLiveResourceMapper;
	
	@Override
	public List<DevLiveResource> selectLiveResByPrimaryKey(long resourceId) {
		// TODO Auto-generated method stub
		return devLiveResourceMapper.selectLiveResByPrimaryKey(resourceId);
	}

	@Override
	public void insertSelective(DevLiveResource record) {
		// TODO Auto-generated method stub
		devLiveResourceMapper.insertSelective(record);
	}

	@Override
	public DevLiveResource selectByPrimaryKey(DevLiveResourceKey key) {
		// TODO Auto-generated method stub
		return devLiveResourceMapper.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(DevLiveResource record) {
		// TODO Auto-generated method stub
		return devLiveResourceMapper.updateByPrimaryKeySelective(record);
	}

}
