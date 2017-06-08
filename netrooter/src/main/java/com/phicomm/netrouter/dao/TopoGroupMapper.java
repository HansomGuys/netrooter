package com.phicomm.netrouter.dao;

import com.phicomm.netrouter.model.TopoGroup;

public interface TopoGroupMapper {
    int deleteByPrimaryKey(Long topogroupid);

    int insert(TopoGroup record);

    int insertSelective(TopoGroup record);

    TopoGroup selectByPrimaryKey(Long topogroupid);

    int updateByPrimaryKeySelective(TopoGroup record);

    int updateByPrimaryKey(TopoGroup record);
}