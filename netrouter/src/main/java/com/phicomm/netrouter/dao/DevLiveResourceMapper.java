package com.phicomm.netrouter.dao;

import java.util.List;

import com.phicomm.netrouter.model.DevLiveResource;
import com.phicomm.netrouter.model.DevLiveResourceKey;

public interface DevLiveResourceMapper {
    int deleteByPrimaryKey(DevLiveResourceKey key);

    int insert(DevLiveResource record);

    int insertSelective(DevLiveResource record);

    DevLiveResource selectByPrimaryKey(DevLiveResourceKey key);

    int updateByPrimaryKeySelective(DevLiveResource record);

    int updateByPrimaryKey(DevLiveResource record);
    
    List<DevLiveResource> selectLiveResByPrimaryKey(long resourceId);
}