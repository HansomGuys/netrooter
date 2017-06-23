package com.phicomm.netrouter.dao;

import com.phicomm.netrouter.model.Transfer_resource;
import com.phicomm.netrouter.model.Transfer_resourceKey;

public interface Transfer_resourceMapper {
    int deleteByPrimaryKey(Transfer_resourceKey key);

    int insert(Transfer_resource record);

    int insertSelective(Transfer_resource record);

    Transfer_resource selectByPrimaryKey(Transfer_resourceKey key);

    int updateByPrimaryKeySelective(Transfer_resource record);

    int updateByPrimaryKey(Transfer_resource record);
    
    void updateBysrcAndresId(Transfer_resource record);
}