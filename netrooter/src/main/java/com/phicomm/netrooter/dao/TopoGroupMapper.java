package com.phicomm.netrooter.dao;

import com.phicomm.netrooter.model.TopoGroup;

public interface TopoGroupMapper {
    int deleteByPrimaryKey(Long topogroupid);

    int insert(TopoGroup record);

    int insertSelective(TopoGroup record);

    TopoGroup selectByPrimaryKey(Long topogroupid);

    int updateByPrimaryKeySelective(TopoGroup record);

    int updateByPrimaryKey(TopoGroup record);
}