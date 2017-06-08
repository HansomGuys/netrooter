package com.phicomm.netrooter.dao;

import com.phicomm.netrooter.model.DevNtwTopo;
import com.phicomm.netrooter.model.DevNtwTopoKey;

public interface DevNtwTopoMapper {
    int deleteByPrimaryKey(DevNtwTopoKey key);

    int insert(DevNtwTopo record);

    int insertSelective(DevNtwTopo record);

    DevNtwTopo selectByPrimaryKey(DevNtwTopoKey key);

    int updateByPrimaryKeySelective(DevNtwTopo record);

    int updateByPrimaryKey(DevNtwTopo record);
}