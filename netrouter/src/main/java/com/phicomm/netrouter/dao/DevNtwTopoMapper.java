package com.phicomm.netrouter.dao;

import com.phicomm.netrouter.model.DevNtwTopo;
import com.phicomm.netrouter.model.DevNtwTopoKey;

public interface DevNtwTopoMapper {
    int deleteByPrimaryKey(DevNtwTopoKey key);

    int insertDevNtwTopo(DevNtwTopo record);

    int insertSelective(DevNtwTopo record);

    DevNtwTopo selectByPrimaryKey(DevNtwTopoKey key);

    int updateByPrimaryKeySelective(DevNtwTopo record);

    int updateByPrimaryKey(DevNtwTopo record);
}