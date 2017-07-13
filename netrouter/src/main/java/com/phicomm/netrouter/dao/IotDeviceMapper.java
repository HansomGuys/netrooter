package com.phicomm.netrouter.dao;

import com.phicomm.netrouter.model.IotDevice;

public interface IotDeviceMapper {
    int deleteByPrimaryKey(Long deviceid);

    int insert(IotDevice record);
    
    int insertBwInfo(long deviceId,String ipAddr,long uplinkBw,long downlinkBw);
    
    int insertSelective(IotDevice record);

    IotDevice selectByPrimaryKey(Long deviceid);

    int updateByPrimaryKeySelective(IotDevice record);

    int updateByPrimaryKey(IotDevice record);
    
    /**
     * 设备重新上线 后，更新设备的最新上线时间以及在线状态
     * @param record: 需要更新的设备
     * @return 所影响的数据行数
     */
    int updateDevice(IotDevice record);
    
    int getDeviceIdByManufactureInfo(String manufacure, String manufacureSN);
    
    boolean isOnline(Long deviceid);

	void offLine(long deviceId);

	int updateDeviceMasterInfo(IotDevice iotDevice);
}