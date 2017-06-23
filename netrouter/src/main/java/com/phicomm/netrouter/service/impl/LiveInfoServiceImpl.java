package com.phicomm.netrouter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.netrouter.dao.IotDeviceMapper;
import com.phicomm.netrouter.dao.LiveInfoMapper;
import com.phicomm.netrouter.model.IotDevice;
import com.phicomm.netrouter.model.LiveInfo;
import com.phicomm.netrouter.service.IotDeviceService;
import com.phicomm.netrouter.service.LiveInfoService;

@Service("liveInfoService")
public class LiveInfoServiceImpl implements LiveInfoService {

	@Autowired
	private LiveInfoMapper liveInfoMapper;
	@Override
	public LiveInfo getDeviceByPrimaryKey(long resourceId) {
		// TODO Auto-generated method stub
		return liveInfoMapper.selectByPrimaryKey(resourceId);
	}
	@Override
	public List<LiveInfo> getResourceId(LiveInfo liveInfo) {
		// TODO Auto-generated method stub
		return liveInfoMapper.getResourceId(liveInfo);
	}
	@Override
	public void insertNewItem(LiveInfo liveInfo) {
		// TODO Auto-generated method stub
		liveInfoMapper.insertSelective(liveInfo);
	}
	@Override
	public int getMaxResId() {
		// TODO Auto-generated method stub
		return liveInfoMapper.getMaxResId();
	}

}
