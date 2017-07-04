package com.phicomm.netrouter.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.phicomm.netrouter.handler.NetRouterServerHandler;

@Component
public class NetRouterServer implements  ServletContextAware, DisposableBean {
	private static final int PORT = 9123;
	@Autowired
	private NetRouterServerHandler handler;

	Logger log = Logger.getLogger(NetRouterServer.class);
	private IoAcceptor acceptor = null;

	/**
	 * 启动 Mina Server端
	 */
	public void start() {
		log.info("Tcp server started");
		acceptor = new NioSocketAcceptor();
		SocketSessionConfig config = (SocketSessionConfig) acceptor.getSessionConfig();
		//acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		acceptor.setHandler(handler);
		config.setReadBufferSize(2048);
		// config.setIdleTime(IdleStatus.BOTH_IDLE, 10);
		InetSocketAddress inetSocketAddress = null;
		try {
//			 inetSocketAddress = new InetSocketAddress("172.17.225.249",PORT);
			// inetSocketAddress = new InetSocketAddress("10.10.10.237", PORT);
			inetSocketAddress = new InetSocketAddress(PORT);
			// inetSocketAddress = new InetSocketAddress("172.17.255.80", PORT);
			config.setReuseAddress(true);
			acceptor.bind(inetSocketAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void dispose() {
		acceptor.dispose();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		start();
	}

	@Override
	public void destroy() throws Exception {
		dispose();
		log.info("Tcp server closed");
	}

}
