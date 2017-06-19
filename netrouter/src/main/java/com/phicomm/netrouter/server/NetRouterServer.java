package com.phicomm.netrouter.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.Charset;

import javax.servlet.ServletContext;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.phicomm.netrouter.handler.NetRouterServerHandler;

@Component
public class NetRouterServer implements InitializingBean, ServletContextAware {
	private static final int PORT = 9123;
	@Autowired
	private NetRouterServerHandler handler;

	/**
	 * 启动 TCP Server端
	 */
	public void start() {
		// 这里需要加个判断，如果启动了，则不需要再启动的，有挑站
		//每次代码修改后都要重启springframe， 否则会报错，这样的以后升级的时候会重启整个服务器
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		acceptor.setHandler(handler);
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		InetSocketAddress inetSocketAddress = null;
		try {
			inetSocketAddress = new InetSocketAddress("localhost", PORT);
			acceptor.bind(inetSocketAddress);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}

	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		start();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

}
