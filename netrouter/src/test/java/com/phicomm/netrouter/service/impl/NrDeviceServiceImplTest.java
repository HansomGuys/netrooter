package com.phicomm.netrouter.service.impl;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.phicomm.netrouter.model.IotDevice;
import com.phicomm.netrouter.service.IotDeviceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class NrDeviceServiceImplTest {
	private static final Logger LOGGER = Logger.getLogger(NrDeviceServiceImplTest.class);

	@Autowired
	private IotDeviceService nrDeviceService;

	@Test
	public void testGetDeviceByPrimaryKey() {
		IotDevice device = nrDeviceService.getDeviceByPrimaryKey(1);
		LOGGER.info(JSON.toJSON(device));
	}

}
