package com.phicomm.netrouter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.phicomm.netrouter.dao.DevLiveResourceMapper;
import com.phicomm.netrouter.dao.LiveInfoMapper;
import com.phicomm.netrouter.model.DevLiveResource;
import com.phicomm.netrouter.model.LiveInfo;
import com.phicomm.netrouter.model.Transfer_resource;;

public interface Transfer_resourceService {
	
	void insertSelective(Transfer_resource record);
	
	void updateByPrimaryKeySelective(Transfer_resource record);
	
	void updateBysrcAndresId(Transfer_resource record);
	
	Transfer_resource selectByPrimaryKey(Transfer_resource record);
}
