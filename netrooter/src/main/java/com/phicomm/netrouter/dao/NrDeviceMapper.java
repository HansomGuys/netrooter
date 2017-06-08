package com.phicomm.netrouter.dao;

import com.phicomm.netrouter.model.NrDevice;

public interface NrDeviceMapper {
    int deleteByPrimaryKey(Long deviceid);

    int insert(NrDevice record);

    int insertSelective(NrDevice record);

    NrDevice selectByPrimaryKey(Long deviceid);

    int updateByPrimaryKeySelective(NrDevice record);

    int updateByPrimaryKey(NrDevice record);
}