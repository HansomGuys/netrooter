package com.phicomm.netrouter.manager.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.phicomm.netrouter.manager.IDeviceManager;
import com.phicomm.netrouter.model.DevNtwTopo;
import com.phicomm.netrouter.model.DeviceWarning;
import com.phicomm.netrouter.model.IotDevice;
import com.phicomm.netrouter.service.NRService;

@Component
public class DeviceManager implements IDeviceManager {
	/*@Autowired
	private OnlineDevice onlineDevice;
	@Autowired
	private OnlineDevicePool onlineDevicePool;*/
	
	@Autowired  
    private NRService netRouterService; 
	
	Result rt;



	@Override
	public String bWInfoReport(Map<String, Object> map) {
		int deviceId = Integer.parseInt(map.get("deviceId").toString());
		String ipAddr = map.get("ipAddr").toString();
		int uplinkBw = Integer.parseInt(map.get("uplinkBw").toString());
		int downlinkBw = Integer.parseInt(map.get("downlinkBw").toString());
		int type = Integer.parseInt(map.get("type").toString());
		netRouterService.insertBwInfo(deviceId,ipAddr,uplinkBw,downlinkBw); 
		return generateAck(type);
	}

	@Override
	public String topoInfoReport(Map<String, Object> map) {
		//ToDo:根据ip地址给出topoid，即要决定此设备属于哪一个拓扑
		long deviceId = Long.parseLong(map.get("deviceId").toString());
		long topoGroupId = 0;
		String publicIpAddr = map.get("publicIpAddr").toString();
		int type = Integer.parseInt(map.get("type").toString());
		DevNtwTopo devNtwTopo = new DevNtwTopo();
		devNtwTopo.setDeviceid(deviceId);
		devNtwTopo.setPublicipaddr(publicIpAddr);
		devNtwTopo.setTopogroupid(topoGroupId);
		netRouterService.insertDevNtwTopo(devNtwTopo);
		return generateAck(type);
	}

	@Override
	public String deviceWarning(Map<String, Object> map) {
		int type = Integer.parseInt(map.get("type").toString());
		long deviceId = Long.parseLong(map.get("deviceId").toString());
		long warningId = Long.parseLong(map.get("warningId").toString());
		
		DeviceWarning record = new DeviceWarning();
		record.setDeviceId(deviceId);
		record.setWarningId(warningId);
		netRouterService.insertDeviceWarning(record);
		return generateAck(type);
	}

	@Override
	public String updateTranRes(Map<String, Object> map) {
		//需要讨论
		return null;
	}
	//此接口需要好好讨论一下，只记录最新的时间或使用任务来做的话都有问题
	@Override
	public String onlineKeep(Map<String, Object> map) {
		Date latestTime = new Date();
		int type = Integer.parseInt(map.get("type").toString());
		long deviceId = Long.parseLong(map.get("deviceId").toString());
		
		IotDevice iotDevice = new IotDevice();
		iotDevice.setOnline(true);
		iotDevice.setDeviceid((long)deviceId);
		iotDevice.setLatestonlinetime(latestTime);
//		boolean a = netRouterService.isOnline(deviceId);
		netRouterService.updateDevice(iotDevice);
		return generateAck(type);
	}
	
	@Override
	public String onlineNotify(Map<String, Object> map) {
		String manufactureSN = map.get("manufactureSN").toString();
		String manufacture = map.get("manufacture").toString();
		//插入之前看看有没有记录，如果有则更新，没有则插入
		int type = Integer.parseInt(map.get("type").toString());
		IotDevice iotDevice = new IotDevice();
		int deviceId = isExisting(manufacture, manufactureSN);
		Date latestTime = new Date();
		if(deviceId>=0){
			//更新
			iotDevice.setDeviceid((long)deviceId);
			iotDevice.setOnline(true);
			iotDevice.setLatestonlinetime(latestTime);
			netRouterService.updateDevice(iotDevice);
		}else{
			//插入
			iotDevice.setManufacture(manufacture);
			iotDevice.setManufacturesn(manufactureSN);
			iotDevice.setOnline(true);
			iotDevice.setFirstonlinetime(latestTime);
			iotDevice.setLatestonlinetime(latestTime);
			netRouterService.insertManufactureInfo(iotDevice);
			deviceId = netRouterService.getDeviceId(manufacture,manufactureSN);
		} 
		/*onlineDevice.setDeviceId(deviceId);
		onlineDevice.setLatestonlinetime(latestTime);
		onlineDevice.timer(15000);*/
		return generateAck(type,deviceId);
	}
	
	private int isExisting(String manufacture,String manufactureSN){
		int deviceId; 
		try {
			deviceId = netRouterService.getDeviceId(manufacture,manufactureSN);
		} catch (Exception e) {
			return -1;
		}
		return deviceId;
	}
	
	private String generateAck(int type) {
		Result rt = new Result();
		rt.setRet(0);
		rt.setType(type);
		return JSON.toJSONString(rt);
	}
	
	private String generateAck(int type,int deviceId){
		String value = generateAck(type);
		StringBuilder sb = new StringBuilder(value.subSequence(0, value.length()-1));
		sb.append(",\"deviceId\":"+deviceId+",\"zburl\":\"http://172.17.72.249:3001/zb\"}");
		return sb.toString();
	}
	
	class Result {
		int type;
		int ret;

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getRet() {
			return ret;
		}

		public void setRet(int ret) {
			this.ret = ret;
		}

	}

}
