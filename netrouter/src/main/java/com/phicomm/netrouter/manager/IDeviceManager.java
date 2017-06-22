package com.phicomm.netrouter.manager;

import java.util.Map;

/**
 * @author chenglin02.wang
 */
public interface IDeviceManager {
	
	/**
	 * 设备上线上报
	 * @param map 上报数据，包括 type,manufacture,manufactureSN
	 * @return type,deviceId,和error code
	 */

	public String onlineNotify(Map<String, Object> map);
	/**
	 * 设备宽带信息上报
	 * @param map 上报数据，包括 type，deviceId,ipAddr,uplinkBw,downlinkBw
	 * @return type,和error code
	 */
	public String bWInfoReport(Map<String, Object> map);
	/**
	 * 设备网络拓扑信息上报
	 * @param map 上报数据，包括type，deviceId,publicIpAddr
	 * @return type,和error code
	 */

	public String topoInfoReport(Map<String, Object> map);
	/**
	 * 设备告警
	 * @param map 上报数据，包括type，deviceId,warning type
	 * @return type,和error code
	 */
	public String deviceWarning(Map<String, Object> map); public String updateTranRes(Map<String, Object> map);
	
	/**
	 * 设备与后台心跳保持
	 * @param map 上报数据，包括type，deviceId
	 * @return type,和error code
	 */
	public String onlineKeep(Map<String, Object> map);
	
	
}
