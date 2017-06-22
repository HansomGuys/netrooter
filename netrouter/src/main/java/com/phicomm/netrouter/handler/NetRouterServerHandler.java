package com.phicomm.netrouter.handler;

import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.phicomm.netrouter.manager.IDeviceManager;
import com.phicomm.netrouter.manager.ManagerType;

/**
 * @author chenglin02.wang
 *	处理与客户端之间的会话
 */
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
		String str = ioBuffer2String(message);
		System.out.println("Message written..." + str);

		Map<String, Object> map = (Map<String, Object>) JSON.parseObject(str);
		int type = Integer.parseInt(map.get("type").toString());
		if (type == (ManagerType.ONLINE_NOTIFY.ordinal() + 1)) {
			String temp = deviceManager.onlineNotify(map);
			IoBuffer result = string2IOBuffer(temp);
			session.write(result);
		} else if (type == (ManagerType.BWINFO_REPORT.ordinal() + 1)) {
			String temp = deviceManager.bWInfoReport(map);
			IoBuffer result = string2IOBuffer(temp);
			session.write(result);
		} else if (type == (ManagerType.TOPOINFO_REPORT.ordinal() + 1)) {
			String temp = deviceManager.topoInfoReport(map);
			IoBuffer result = string2IOBuffer(temp);
			session.write(result);
		} else if (type == (ManagerType.DEVICE_WARNING.ordinal() + 1)) {
			// TODO: modify sql script
			String temp = deviceManager.deviceWarning(map);
			IoBuffer result = string2IOBuffer(temp);
			session.write(result);
		} else if (type == (ManagerType.UPDATE_TRANRES.ordinal() + 1)) {
			deviceManager.updateTranRes(map);
		} else if (type == (ManagerType.ONLINE_KEEP.ordinal() + 1)) {
			String temp = deviceManager.onlineKeep(map);
			IoBuffer result = string2IOBuffer(temp);
			session.write(result);
		}
	}

	private IoBuffer string2IOBuffer(String result) {
		byte bt[] = result.getBytes();
		IoBuffer ioBuffer2 = IoBuffer.allocate(bt.length);
		ioBuffer2.put(bt, 0, bt.length);
		ioBuffer2.flip();
		return ioBuffer2;
	}

	private String ioBuffer2String(Object message) {
		IoBuffer ioBuffer = (IoBuffer) message;
		byte[] b = new byte[ioBuffer.limit()];
		ioBuffer.get(b);
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			stringBuffer.append((char) b[i]);
		}
		String str = stringBuffer.toString();
		return str;
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
}
