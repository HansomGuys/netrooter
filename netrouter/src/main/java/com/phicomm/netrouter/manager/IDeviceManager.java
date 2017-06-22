package com.phicomm.netrouter.manager;

import java.util.Map;

/**
 * @author chenglin02.wang
 *
 */
public interface IDeviceManager {
	
	/**
	 * 设备上线后，将厂商名和设备序列号上报给服务器。
	 * @return 
	 * 
	 */
	public String onlineNotify(Map<String, Object> map);
	public String bWInfoReport(Map<String, Object> map);
	public String topoInfoReport(Map<String, Object> map);
	public String deviceWarning(Map<String, Object> map);
	public String updateTranRes(Map<String, Object> map);
	public String onlineKeep(Map<String, Object> map);
	
	
}
