package com.phicomm.netrouter.handler;

import java.util.Map;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.phicomm.netrouter.manager.IDeviceManager;
import com.phicomm.netrouter.manager.ManagerType;
import com.phicomm.netrouter.manager.impl.DeviceManager;

@Component
public class NetRouterServerHandler extends IoHandlerAdapter implements IoHandler {

	@Autowired
	private IDeviceManager deviceManager;

	@Override
	public void exceptionCaught(IoSession arg0, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
//		deviceManager = new DeviceManager();//暂时的
		@SuppressWarnings("unchecked") 
		Map<String, Object> map = (Map<String, Object>) JSON.parseObject(str);
		int type = Integer.parseInt(map.get("type").toString());
		if (type==(ManagerType.ONLINE_NOTIFY.ordinal()+1)) {
			String result = deviceManager.onlineNotify(map);
			session.write(result);
		}else if(type==(ManagerType.BWINFO_REPORT.ordinal()+1)){
			String result = deviceManager.bWInfoReport(map);
			session.write(result);
		}else if(type==(ManagerType.TOPOINFO_REPORT.ordinal()+1)){
			String result = deviceManager.topoInfoReport(map);
			session.write(result);
		}else if(type==(ManagerType.DEVICE_WARNING.ordinal()+1)){
			//TODO: modify sql script
			String result = deviceManager.deviceWarning(map);
			session.write(result);
		}else if(type==(ManagerType.UPDATE_TRANRES.ordinal()+1)){
			deviceManager.updateTranRes(map);
		}else if(type==(ManagerType.ONLINE_KEEP.ordinal()+1)){
			String result = deviceManager.onlineKeep(map);
			session.write(result);
		}
		if (str.trim().equalsIgnoreCase("quit")) {
			session.close();
			return;
		}

		// Date date = new Date();
		System.out.println("Message written..." + message);
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {

	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {

	}

	public void test(){
		System.out.println("handler injection");
	}
}
