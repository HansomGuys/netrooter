package com.phicomm.netrouter.manager.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.phicomm.netrouter.manager.IDeviceManager;
import com.phicomm.netrouter.model.DevNtwTopo;
import com.phicomm.netrouter.model.DevNtwTopoKey;
import com.phicomm.netrouter.model.DeviceWarning;
import com.phicomm.netrouter.model.IotDevice;
import com.phicomm.netrouter.model.SharedStrategy;
import com.phicomm.netrouter.service.NRService;
import com.phicomm.netrouter.service.SharedStrategyService;

@Component
public class DeviceManager implements IDeviceManager {
	/*@Autowired
	private OnlineDevice onlineDevice;
	@Autowired
	private OnlineDevicePool onlineDevicePool;*/
	
	@Autowired  
    private NRService netRouterService; 
	
	@Autowired
	private SharedStrategyService sharedStrategyService;
	
	private Logger log = Logger.getLogger(DeviceManager.class);
	
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
		long topoGroupId = 1;//当前写死
		String publicIpAddr = map.get("publicIpAddr").toString();
		int type = Integer.parseInt(map.get("type").toString());
		boolean beMaster = Integer.parseInt(map.get("beMaster").toString())>=1?true:false;
		
		DevNtwTopoKey topoKey = new DevNtwTopoKey();
		topoKey.setDeviceid(deviceId);
		topoKey.setTopogroupid(topoGroupId);
		
		DevNtwTopo devNtwTopo = netRouterService.getDevNtwTopoByPrimaryKey(topoKey);
		
		
		if(null == devNtwTopo){
			devNtwTopo = new DevNtwTopo();
			devNtwTopo.setDeviceid(deviceId);
			devNtwTopo.setPublicipaddr(publicIpAddr);
			devNtwTopo.setTopogroupid(topoGroupId);
			netRouterService.insertDevNtwTopo(devNtwTopo);
			
			IotDevice iotDevice = new IotDevice();
			iotDevice.setDeviceid((long)deviceId);
			iotDevice.setBeMaster(beMaster);
			netRouterService.updateDeviceMasterInfo(iotDevice);
		}
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
		//插入之前需要判断是否存在此记录，如果有则更新，没有则插入
		int type = Integer.parseInt(map.get("type").toString());
		
		int upnpPort = Integer.parseInt(map.get("upnpPort").toString());
		String publicIpAddr = map.get("publicIpAddr").toString();
		startUdp(upnpPort,publicIpAddr);
		IotDevice iotDevice = new IotDevice();
		int deviceId = isExisting(manufacture, manufactureSN);
		Date latestTime = new Date();
		SharedStrategy sharedStrategy = null;
		if(deviceId>=0){
			//更新
			iotDevice.setDeviceid((long)deviceId);
			iotDevice.setOnline(true);
			iotDevice.setLatestonlinetime(latestTime);
			netRouterService.updateDevice(iotDevice);
			sharedStrategy = sharedStrategyService.selectByDeviceId((long)deviceId);
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
		return generateAck(type,deviceId,sharedStrategy);
	}
	
	private void startUdp(int port, String ip) {
		final UDPServer udpServer = new UDPServer(port, ip);
		final String sendStr = "OK";
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				udpServer.send(sendStr);
			}
		});
		thread.start();
		try {
			Thread.sleep(2000);
			udpServer.dispose(); 
			thread.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
		sb.append(",\"deviceId\":"+deviceId+",\"zburl\":\"http://172.17.225.249:8090/zb/\"}");
		return sb.toString();
	}
	
	private String generateAck(int type,int deviceId,SharedStrategy sharedStrategy){
		String value1 = generateAck(type,deviceId);
		if(null==sharedStrategy){
			log.info("The [id:"+deviceId+"] device is not binded");
			return value1;
		}
		String value2 = JSON.toJSONString(sharedStrategy);
		StringBuilder sb = new StringBuilder(value1.substring(0, value1.length()-1));
		sb.append(","+value2.substring(1, value2.length()));
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
	
	class UDPServer {
	    private DatagramSocket dataSocket;
	    private DatagramPacket sendPacket;
	    private byte sendDataByte[];
	    private int port;
	    private InetAddress ip;

	    public UDPServer(int port, String ip) {
	        init(port,ip);
	    }
	    
	    public void init(int port,String ip) {
	        try {
	        	dataSocket = new DatagramSocket();
	            this.port = port;
	            this.ip = InetAddress.getByName(ip);
	            sendDataByte = new byte[1024];
	            log.info("UpnpPort :"+port+"--- Public Ip Address :"+ip);
	        }catch (IOException ie) {
	            ie.printStackTrace();
	        }
	    }

	    public void dispose() {
	    	dataSocket.close();
	    	log.info("The connection via udp is off");
		}

		public void send(String value) {
			try {
				sendDataByte = value.getBytes();
				sendPacket = new DatagramPacket(sendDataByte, sendDataByte.length,ip, port);
				dataSocket.send(sendPacket);
				log.info("send a message via udp protocol");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}

}
