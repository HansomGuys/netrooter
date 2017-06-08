package com.phicomm.netrooter.dao;

import com.phicomm.netrooter.model.DevLiveResource;
import com.phicomm.netrooter.model.DevLiveResourceKey;

public interface DevLiveResourceMapper {
    int deleteByPrimaryKey(DevLiveResourceKey key);

    int insert(DevLiveResource record);

    int insertSelective(DevLiveResource record);

    DevLiveResource selectByPrimaryKey(DevLiveResourceKey key);

    int updateByPrimaryKeySelective(DevLiveResource record);

    int updateByPrimaryKey(DevLiveResource record);
}