package com.phicomm.netrouter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.phicomm.netrouter.dao.DevLiveResourceMapper;
import com.phicomm.netrouter.dao.LiveInfoMapper;
import com.phicomm.netrouter.model.DevLiveResource;
import com.phicomm.netrouter.model.DevLiveResourceKey;
import com.phicomm.netrouter.model.LiveInfo;;

public interface DevLiveResourceService {
	
	List<DevLiveResource> selectLiveResByPrimaryKey(long resourceId);
	
	void insertSelective(DevLiveResource record);
	
    DevLiveResource selectByPrimaryKey(DevLiveResourceKey key);

    int updateByPrimaryKeySelective(DevLiveResource record);
}
