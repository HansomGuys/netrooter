package com.phicomm.netrouter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.netrouter.dao.DevNtwTopoMapper;
import com.phicomm.netrouter.dao.DeviceWarningMapper;
import com.phicomm.netrouter.dao.IotDeviceMapper;
import com.phicomm.netrouter.model.DevNtwTopo;
import com.phicomm.netrouter.model.DevNtwTopoKey;
import com.phicomm.netrouter.model.DeviceWarning;
import com.phicomm.netrouter.model.IotDevice;
import com.phicomm.netrouter.service.NRService;

@Service("netRouterService")
public class NRServiceImpl implements NRService {

	@Autowired
	private IotDeviceMapper iotDeviceMapper;
	
	@Autowired
	private DevNtwTopoMapper devNtwTopoMapper;
	
	@Autowired
	private DeviceWarningMapper deviceWarningMapper;
	
	
	
	@Override
	public IotDevice getDeviceByPrimaryKey(long deviceid) {
		// TODO Auto-generated method stub
		return iotDeviceMapper.selectByPrimaryKey(deviceid);
	}
	@Override
	public int insertBwInfo(long deviceId,String ipAddr,long uplinkBw,long downlinkBw) {
		return iotDeviceMapper.insertBwInfo(deviceId, ipAddr, uplinkBw, downlinkBw)
				;
	}
	@Override
	public int getDeviceId(String manufacure, String manufacureSN) {
		return iotDeviceMapper.getDeviceIdByManufactureInfo(manufacure, manufacureSN);
	}
	@Override
	public int insertDevNtwTopo(DevNtwTopo record) {
		return devNtwTopoMapper.insertDevNtwTopo(record);
	}
	@Override
	public int insertDeviceWarning(DeviceWarning record) {
		return deviceWarningMapper.insertDeviceWarning(record);
	}
	@Override
	public int insertManufactureInfo(IotDevice iotDevice) {
		return iotDeviceMapper.insert(iotDevice);
	}
	@Override
	public int updateDevice(IotDevice iotDevice) {
		return iotDeviceMapper.updateDevice(iotDevice);
	}
	@Override
	public boolean isOnline(Long deviceId) {
		return iotDeviceMapper.isOnline(deviceId);
	}
	@Override
	public void offLine(long deviceId) {
		iotDeviceMapper.offLine(deviceId);
	}
	@Override
	public DevNtwTopo getDevNtwTopoByPrimaryKey(DevNtwTopoKey key) {
		// TODO Auto-generated method stub
		return devNtwTopoMapper.selectByPrimaryKey(key);
	}

}
