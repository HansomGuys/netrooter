package com.phicomm.netrouter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.netrouter.dao.DevLiveResourceMapper;
import com.phicomm.netrouter.dao.IotDeviceMapper;
import com.phicomm.netrouter.dao.LiveInfoMapper;
import com.phicomm.netrouter.dao.Transfer_resourceMapper;
import com.phicomm.netrouter.model.DevLiveResource;
import com.phicomm.netrouter.model.IotDevice;
import com.phicomm.netrouter.model.LiveInfo;
import com.phicomm.netrouter.model.Transfer_resource;
import com.phicomm.netrouter.service.DevLiveResourceService;
import com.phicomm.netrouter.service.IotDeviceService;
import com.phicomm.netrouter.service.LiveInfoService;
import com.phicomm.netrouter.service.Transfer_resourceService;

@Service("transfer_resourceService")
public class Transfer_resourceServiceImpl implements Transfer_resourceService {

	@Autowired
	private Transfer_resourceMapper transfer_resourceMapper;
	
	@Override
	public void insertSelective(Transfer_resource record) {
		// TODO Auto-generated method stub
		transfer_resourceMapper.insertSelective(record);
	}

	@Override
	public void updateByPrimaryKeySelective(Transfer_resource record) {
		// TODO Auto-generated method stub
		transfer_resourceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Transfer_resource selectByPrimaryKey(Transfer_resource record) {
		// TODO Auto-generated method stub
		return transfer_resourceMapper.selectByPrimaryKey(record);
	}

	@Override
	public void updateBysrcAndresId(Transfer_resource record) {
		// TODO Auto-generated method stub
		transfer_resourceMapper.updateBysrcAndresId(record);
	}

}
