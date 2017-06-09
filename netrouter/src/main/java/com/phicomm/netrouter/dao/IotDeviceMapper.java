package com.phicomm.netrouter.dao;

import com.phicomm.netrouter.model.IotDevice;

public interface IotDeviceMapper {
    int deleteByPrimaryKey(Long deviceid);

    int insert(IotDevice record);

    int insertSelective(IotDevice record);

    IotDevice selectByPrimaryKey(Long deviceid);

    int updateByPrimaryKeySelective(IotDevice record);

    int updateByPrimaryKey(IotDevice record);
}