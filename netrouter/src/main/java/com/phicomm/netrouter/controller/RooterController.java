package com.phicomm.netrouter.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.phicomm.netrouter.model.DevLiveResource;
import com.phicomm.netrouter.model.DevLiveResourceKey;
import com.phicomm.netrouter.model.IotDevice;
import com.phicomm.netrouter.model.LiveInfo;
import com.phicomm.netrouter.model.ParameterList;
import com.phicomm.netrouter.model.Transfer_resource;
import com.phicomm.netrouter.service.DevLiveResourceService;
import com.phicomm.netrouter.service.IotDeviceService;
import com.phicomm.netrouter.service.LiveInfoService;
import com.phicomm.netrouter.service.Transfer_resourceService;

@Controller  
@RequestMapping("/")
public class RooterController {
	@Autowired  
    private IotDeviceService iotDeviceService;  
      
	@Autowired  
    private LiveInfoService liveInfoService; 
	
	@Autowired  
    private DevLiveResourceService devLiveResourceService; 
	
	@Autowired  
    private Transfer_resourceService  transfer_resourceService; 
	
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
    @RequestMapping("/showInfo/{deviceid}")  
    public @ResponseBody Object showNrDeviceInfo(ModelMap modelMap, @PathVariable int deviceid){  
        IotDevice userInfo = iotDeviceService.getDeviceByPrimaryKey(deviceid);  
        modelMap.addAttribute("userInfo", userInfo);  
        return modelMap;  
    }  
    

	public String requestTest(@RequestBody ParameterList list) 
    {
    	
    	System.out.println(list.getType());
    	return "success";
    }

    //请求总接口，根据type来做相应的处理
    @RequestMapping(value = "/index", method=RequestMethod.POST, produces="application/json")
	//@RequestMapping(value = {"/index","","/"}, method=RequestMethod.POST, produces="application/json")
	@ResponseBody
    public void request(@RequestBody ParameterList list, HttpServletResponse response)
    {
    	try
    	{
    		
    		int type = list.getType();//Integer.parseInt(request.getParameter("type"));
    		
    		//设备获取直播资源列表  type=0
    		if(type == 0)
    		{
    			int deviceId = Integer.parseInt(list.getDeviceId().trim());
    			String path=list.getPath();
    			String host=list.getHost();
    			
    			/*
    			response.setContentType("application/json;charset=utf-8"); 
    			
    			String jsonData = "{\"ret\":0, \"type\":0}";
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			*/
    			
    			getZbResource(type, deviceId, host, path, response);
    		}
    		//设备直播服务接口上报 tyrpe=1
    		else if(type == 1)
    		{
    			int deviceId = Integer.parseInt(list.getDeviceId().trim());
    			int resourceId = list.getResId();
    			int cap = list.getCap();
    			String url = list.getUrl();
    			
    			resourceOnLineReport(type, deviceId, resourceId, url, cap, response);
    		}
    		//设备直播服务（下线）接口上报  type=2
    		else if(type ==2)
    		{
    			long deviceId = Integer.parseInt(list.getDeviceId().trim());
    			long resourceId = list.getResId();
    			String url = list.getUrl();
    			
    			resourceOffLineReport(type, deviceId, resourceId, response);
    		}
    		//直播转发流建立上报  type=3
    		else if(type == 3)
    		{
    			long deviceId = Integer.parseInt(list.getDeviceId().trim());
    			long resourceId = list.getResId();
    			String src = list.getSrc().trim();
    			
    			if(src.trim().equals("origin"))
    			{
    				transferOnlineReport_Origin(type, deviceId, resourceId, src, response);
    				return;
    			}
    			
    			long src1 = Integer.parseInt(src);  			
    			transferOnlineReport(type, deviceId, resourceId, src1, response); 
    			return;
    		}
    		//直播转发流断开上报  type=4
    		else if(type == 4)
    		{
    			long deviceId = Integer.parseInt(list.getDeviceId().trim());//Integer.parseInt(request.getParameter("deviceId").replace('"', ' ').trim());
    			long resourceId = list.getResId();//Integer.parseInt(request.getParameter("resId"));
    			String src = list.getSrc().trim();//request.getParameter("src").replace("\"", "").replace('"', ' ').trim();
    			String totalBytes = list.getTotalBytes() + "";
    			
    			if(src.trim().equals("origin"))
    			{
    				transferOfflineReport_Origin(type, deviceId, resourceId, src, totalBytes, response);
    				return;
    			}
    			
    			long src1 = Integer.parseInt(src); 
    			transferOfflineReport(type, deviceId, resourceId, src1, totalBytes, response); 
    			
    			return;
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	} 	
    }
    
    public void getZbResource(int type, long deviceId, String host, String path, HttpServletResponse response) 
    {
		try 
		{
			long resourceId;
			
			logger.info("*******************************getzbresource**********************************");
			logger.info("[getzbresource Parameter]  type:" + type + ", deviceId:" + deviceId + ", host:" + host + ", path:" + path);
			LiveInfo liveInfo = new LiveInfo();
			
			String[] path1 = path.split("/");
			String path2 = path1[path1.length-1];
			path = path2.split("\\?")[0];
			liveInfo.setPath(path);
			liveInfo.setHost(host);
			
			List<LiveInfo> resList = liveInfoService.getResourceId(liveInfo);
			if(resList.size() == 0)
			{
				logger.info("未查到请求的资源");
				
				// . 在正则表达式中有特殊的含义，因此我们使用的时候必须进行转义
				String[] devType = host.split("\\.");
				String provider = "";
				if(devType.length >= 2)
					provider = devType[devType.length-2];
				else if(devType.length == 1)
					provider = devType[0];
				
				liveInfo.setProvider(provider);
				liveInfoService.insertNewItem(liveInfo);
				
				resourceId = liveInfoService.getMaxResId();
				logger.info("向表live_info中插入资源信息， resourceId:" + resourceId);
				
				List<Object> list = new ArrayList<Object>();
				
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("resId", resourceId);
				/*返回的资源列表为空*/
				json.put("list", list);
				json.put("ret", 0);
				
                response.setContentType("text/html; charset=utf-8"); 
    			response.setHeader("Connection", "Keep-Alive");
    			//String jsonData = "{\"ret\":0, \"type\":0}";
                String jsonData = JSON.toJSONString(json);
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("[getzbresource Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			else
			{
				LiveInfo res = resList.get(0);
				resourceId = res.getResourceid();
				//根据查到的resourceId从表dev_live_resource中查询直播资源
				List<DevLiveResource> devLiveResourceList = devLiveResourceService.selectLiveResByPrimaryKey(resourceId);
				
				List<Object> list = new ArrayList<Object>();
				
				JSONObject json = new JSONObject();

				for (int i = 0; i < devLiveResourceList.size(); i++)
				{
					JSONObject data = new JSONObject();
					data.put("deviceId", devLiveResourceList.get(i).getDeviceid()+"");
					data.put("url", devLiveResourceList.get(i).getUrl());
					list.add(data);
				}
				json.put("type", type);
				json.put("resId", resourceId);
				json.put("list", list);
				json.put("ret", 0);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("[getzbresource Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				//返回查询到的直播资源
				
				return;
			}
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			//return null;
			return;
		}
	}
    
    public void resourceOnLineReport(int type, long deviceId, long resourceId, String url, int cap, HttpServletResponse response) 
    {
		try 
		{
			logger.info("*****************************resourceOnLineReport******************************");
			logger.info("[resourceOnLineReport Parameter]  type:" + type + ", deviceId:" + deviceId + ", resourceId:" + resourceId + ", url:" + url);
			
			DevLiveResource devLiveResource = new DevLiveResource();
			devLiveResource.setDeviceid(deviceId);
			devLiveResource.setResourceid(resourceId);
			devLiveResource.setUrl(url);
			devLiveResource.setOnline(true);
			
			IotDevice iotDevice = null;
			iotDevice = iotDeviceService.getDeviceByPrimaryKey(deviceId);
			LiveInfo liveInfo = null;
			liveInfo = liveInfoService.getDeviceByPrimaryKey(resourceId);
			
			/*如果设备表iot_device没有对应的设备（deviceId），或者直播资源表live_info没有对应的资源（resourceId），则返回异常1*/
			if(iotDevice == null || liveInfo == null)
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 1);
				
                String jsonData = JSON.toJSONString(json);
                response.setContentType("text/html; charset=utf-8"); 
    			response.setHeader("Connection", "Keep-Alive");
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("设备表iot_device没有对应的设备（deviceId），或者直播资源表live_info没有对应的资源（resourceId），则返回异常1");
				logger.info("[resourceOnLineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
    			
				return;
				
			}
			
			DevLiveResourceKey key = new DevLiveResourceKey();
			key.setDeviceid(deviceId);
			key.setResourceid(resourceId);			
			DevLiveResource res = null;
			res = devLiveResourceService.selectByPrimaryKey(key);
			
			/*如果设备上没有上报的资源，则在设备资源表dev_live_resource中添加该资源记录*/
			if(res == null)
			{
				/*设置最大转发链接数*/
				devLiveResource.setMaxslavecnt(cap);
				/*暂时默认设置当前转发链接数为0，后面由上报参数确定*/
				devLiveResource.setCurrentslavecnt(0);
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String firstTime = df.format(new Date());
				/*设置资源上线时间*/
				devLiveResource.setFirsttime(new Date());
				
				devLiveResourceService.insertSelective(devLiveResource);
								
				logger.info("设备上没有上报的资源，则在设备资源表dev_live_resource中添加该资源记录，设置最大转发链接数为" + cap + "，默认设置当前转发链接数为0");
			}
			else /*否则只将该资源状态置为在线，初始化最大转发链接数和当前转发链接数*/
			{
				/*设置最大转发链接数*/
				devLiveResource.setMaxslavecnt(cap);
				/*暂时默认设置当前转发链接数为0，后面由上报参数确定*/
				devLiveResource.setCurrentslavecnt(0);
				
				/*设置资源上线时间*/
				devLiveResource.setFirsttime(new Date());
				
				logger.info("设备上有上报的资源，将该资源状态置为在线，设置最大转发链接数为" + cap + "，默认设置当前转发链接数为0");
				devLiveResourceService.updateByPrimaryKeySelective(devLiveResource);
			}
			
			JSONObject json = new JSONObject();
			json.put("type", type);
			json.put("ret", 0);
			
            String jsonData = JSON.toJSONString(json);
            response.setHeader("Connection", "Keep-Alive");
            response.setContentType("text/html; charset=utf-8"); 
			response.setContentLength(jsonData.length());
			response.getWriter().write(jsonData);
			
			logger.info("[resourceOnLineReport Ret]  " + json);
			logger.info("**************************************************************************\n");
			
			return;
			
			
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			
			JSONObject json = new JSONObject();
			json.put("type", 1);
			//上报异常
			json.put("ret", 2);
			
			logger.info("发生其他异常");
			logger.info("[resourceOnLineReport Ret]  " + json);
			logger.info("**************************************************************************\n");
			
			return;
			
		}
	}
    
    //设备直播服务（下线）接口上报  type=2
	public void resourceOffLineReport(int type, long deviceId, long resourceId, HttpServletResponse response)//, String url) 
    {
		try 
		{
			logger.info("*****************************resourceOffLineReport******************************");
			logger.info("[resourceOffLineReport Parameter]  type:" + type + ", deviceId:" + deviceId + ", resourceId:" + resourceId);// + ", url:" + url);
			
			DevLiveResource devLiveResource = new DevLiveResource();
			devLiveResource.setDeviceid(deviceId);
			devLiveResource.setResourceid(resourceId);
			//devLiveResource.setUrl(url);
			devLiveResource.setOnline(false);
			
			DevLiveResourceKey key = new DevLiveResourceKey();
			key.setDeviceid(deviceId);
			key.setResourceid(resourceId);			
			DevLiveResource res = null;
			res = devLiveResourceService.selectByPrimaryKey(key);
			
			/*如果设备上没有上报的资源，则返回异常1*/
			if(res == null)
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 1);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("设备上没有上报的资源，则返回异常1");
				logger.info("[resourceOffLineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
    			
				return;
				
			}
			
			/*否则将资源的online置为false，当前转发链接数置为0*/
			devLiveResource.setCurrentslavecnt(0);
			
			/*设置资源下线时间*/
			devLiveResource.setLasttime(new Date());
			devLiveResourceService.updateByPrimaryKeySelective(devLiveResource);
			/*将转发表中设备(src)对应的转发信息状态置为下线 ？*/
			Transfer_resource transferResource = new Transfer_resource();
			//transferResource.setDeviceid(deviceId);
			transferResource.setSrc(deviceId+"");
			transferResource.setResourceid(resourceId);
			transferResource.setOnline(false);
			
			transfer_resourceService.updateBysrcAndresId(transferResource);
			
			JSONObject json = new JSONObject();
			json.put("type", type);
			json.put("ret", 0);
			
            String jsonData = JSON.toJSONString(json);
            response.setHeader("Connection", "Keep-Alive");
            response.setContentType("text/html; charset=utf-8"); 
			response.setContentLength(jsonData.length());
			response.getWriter().write(jsonData);
			
			logger.info("设备上有上报的资源，将资源的online置为false，当前转发链接数置为0，将转发表中设备(src)对应的转发信息状态置为下线");
			logger.info("[resourceOffLineReport Ret]  " + json);
			logger.info("**************************************************************************\n"); 
			
			return;
			
			
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			
			JSONObject json = new JSONObject();
			json.put("type", 2);
			//上报其他异常2
			json.put("ret", 2);
			
			logger.info("发生其他异常");
			logger.info("[resourceOffLineReport Ret]  " + json);
			logger.info("**************************************************************************\n");
			
			return;
			
		}
	}
    
    //直播转发流建立上报  type=3
	public void transferOnlineReport(int type, long deviceId, long resourceId, long src, HttpServletResponse response) 
    {
		try 
		{
			logger.info("***************************transferOnlineReport****************************");
			logger.info("[transferOnlineReport Parameter]  type:" + type + ", deviceId:" + deviceId + ", resourceId:" + resourceId + ", src:" + src);
			
			Transfer_resource transfer_resource = new Transfer_resource();
			transfer_resource.setDeviceid(deviceId);
			transfer_resource.setResourceid(resourceId);
			transfer_resource.setSrc(src+"");
			
			DevLiveResourceKey key = new DevLiveResourceKey();
			key.setDeviceid(src);
			//key.setDeviceid(deviceId);
			key.setResourceid(resourceId);			
			DevLiveResource res = null;
			res = devLiveResourceService.selectByPrimaryKey(key);
			
			IotDevice iotDevice = null;
			iotDevice = iotDeviceService.getDeviceByPrimaryKey(deviceId);
			LiveInfo liveInfo = null;
			liveInfo = liveInfoService.getDeviceByPrimaryKey(resourceId);
			
			/*如果设备表iot_device没有对应的设备（src），或者直播资源表live_info没有对应的资源（resourceId），则返回异常1*/
			if(iotDevice == null || liveInfo == null)
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 1);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("设备表iot_device没有对应的设备（src），或者直播资源表live_info没有对应的资源（resourceId），则返回异常1");
				logger.info("[resourceOnLineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
    			
				return;
				
			}
			
			/*如果设备上没有要转发的资源或要转发的资源下线，就没法进行转发，返回异常2*/
			if((res == null) || (res.getOnline() == false))
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 2);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("设备上没有要转发的资源或要转发的资源下线，就没法进行转发，返回异常2");
				logger.info("[transferOnlineReport Ret]  " + json);
				logger.info("**************************************************************************\n");				
    			
				return;
				
			}
			
			int maxLaveCnt = res.getMaxslavecnt();
			int currentSlaveCnt = res.getCurrentslavecnt();
			
			/*如果设备上的当前转发链接数已达到上限，就没法进行转发，返回异常3*/
			if(maxLaveCnt <= (currentSlaveCnt))
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 3);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("设备上的当前转发链接数已达到上限，就没法进行转发，返回异常3");
				logger.info("[transferOnlineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
    			
				return;
    			
			}
			
			Transfer_resource transRes = transfer_resourceService.selectByPrimaryKey(transfer_resource);
			
			/*如果资源转发表transfer_resource中已经有该转发记录且状态在线，则没法再转发，返回异常4*/
			if((transRes != null) && (transRes.getOnline() == true))
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 4);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("资源转发表transfer_resource中已经有该转发记录且状态在线，则没法再转发，返回异常4");
				logger.info("[transferOnlineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
    			
				return;
    			
			}
			else if((transRes != null) && (transRes.getOnline() == false))
			{
				/*如果资源转发表transfer_resource中已经有该转发记录且状态不在线，则将设备上当前转发链接数加1，并将转发信息状态置为在线*/
				currentSlaveCnt++;
				res.setCurrentslavecnt(currentSlaveCnt);
				devLiveResourceService.updateByPrimaryKeySelective(res);
				transfer_resource.setOnline(true);
				/*设置转发记录上线时间*/
				transfer_resource.setStarttime(new Date());
				transfer_resourceService.updateByPrimaryKeySelective(transfer_resource);
				
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 0);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("资源转发表transfer_resource中已经有该转发记录且状态不在线，则将设备上当前转发链接数加1，并将转发信息状态置为在线");
				logger.info("[transferOnlineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			else
			{
				/*否则，如果资源转发表transfer_resource中没有该转发记录，将设备上当前转发链接数加1，并向表transfer_resource插入一条转发记录*/
				currentSlaveCnt++;
				res.setCurrentslavecnt(currentSlaveCnt);
				devLiveResourceService.updateByPrimaryKeySelective(res);
				transfer_resource.setOnline(true);
				/*设置转发记录上线时间*/
				transfer_resource.setStarttime(new Date());
				transfer_resourceService.insertSelective(transfer_resource);
				
				JSONObject json = new JSONObject();
				json.put("type", 3);
				json.put("ret", 0);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("资源转发表transfer_resource中没有该转发记录，将设备上当前转发链接数加1，并向表transfer_resource插入一条转发记录");
				logger.info("[transferOnlineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			
			JSONObject json = new JSONObject();
			json.put("type", 3);
			//上报其他异常
			json.put("ret", 4);
			
			logger.info("发生其他异常");
			logger.info("[transferOnlineReport Ret]  " + json);
			logger.info("**************************************************************************\n");
			
			return;
			
		}
	}
    
	//直播转发流建立上报(从源站点获取)  type=3
	public void transferOnlineReport_Origin(int type, long deviceId, long resourceId, String src, HttpServletResponse response) 
	{
			try 
			{
				logger.info("***************************transferOnlineReport_Origin****************************");
				logger.info("[transferOnlineReport_Origin Parameter]  type:" + type + ", deviceId:" + deviceId + ", resourceId:" + resourceId + ", src:" + src);
				
				Transfer_resource transfer_resource = new Transfer_resource();
				transfer_resource.setDeviceid(deviceId);
				transfer_resource.setResourceid(resourceId);
				transfer_resource.setSrc(src);
				
				/*
				DevLiveResourceKey key = new DevLiveResourceKey();
				key.setDeviceid(src);
				//key.setDeviceid(deviceId);
				key.setResourceid(resourceId);			
				DevLiveResource res = null;
				res = devLiveResourceService.selectByPrimaryKey(key);
				*/

				IotDevice iotDevice = null;
				iotDevice = iotDeviceService.getDeviceByPrimaryKey(deviceId);
				LiveInfo liveInfo = null;
				liveInfo = liveInfoService.getDeviceByPrimaryKey(resourceId);
				
				/*如果设备表iot_device没有对应的设备（deviceId），或者直播资源表live_info没有对应的资源（resourceId），则返回异常1*/
				if(iotDevice == null || liveInfo == null)
				{
					JSONObject json = new JSONObject();
					json.put("type", type);
					json.put("ret", 1);
					
	                String jsonData = JSON.toJSONString(json);
	                response.setHeader("Connection", "Keep-Alive");
	                response.setContentType("text/html; charset=utf-8"); 
	    			response.setContentLength(jsonData.length());
	    			response.getWriter().write(jsonData);
	    			
					logger.info("设备表iot_device没有对应的设备（src），或者直播资源表live_info没有对应的资源（resourceId），则返回异常1");
					logger.info("[transferOnlineReport_Origin Ret]  " + json);
					logger.info("**************************************************************************\n");
					
					return;
					
				}
				
				/*如果设备上没有要转发的资源或要转发的资源下线，就没法进行转发，返回异常2*/
				/*
				if((res == null) || (res.getOnline() == false))
				{
					JSONObject json = new JSONObject();
					json.put("type", type);
					json.put("ret", 2);
					
					logger.info("设备上没有要转发的资源或要转发的资源下线，就没法进行转发，返回异常2");
					logger.info("[transferOnlineReport Ret]  " + json);
					logger.info("**************************************************************************\n");
					return json;
				}			
				
				
				int maxLaveCnt = res.getMaxslavecnt();
				int currentSlaveCnt = res.getCurrentslavecnt();
				*/
				/*如果设备上的当前转发链接数已达到上限，就没法进行转发，返回异常3*/
				/*
				if(maxLaveCnt <= (currentSlaveCnt))
				{
					JSONObject json = new JSONObject();
					json.put("type", type);
					json.put("ret", 3);
					
					logger.info("设备上的当前转发链接数已达到上限，就没法进行转发，返回异常3");
					logger.info("[transferOnlineReport Ret]  " + json);
					logger.info("**************************************************************************\n");
					return json;
				}
				*/
				
				Transfer_resource transRes = transfer_resourceService.selectByPrimaryKey(transfer_resource);
				
				/*如果资源转发表transfer_resource中已经有该转发记录且状态在线，则没法再转发，返回异常2*/
				if((transRes != null) && (transRes.getOnline() == true))
				{
					JSONObject json = new JSONObject();
					json.put("type", type);
					json.put("ret", 2);
					
	                String jsonData = JSON.toJSONString(json);
	                response.setHeader("Connection", "Keep-Alive");
	                response.setContentType("text/html; charset=utf-8"); 
	    			response.setContentLength(jsonData.length());
	    			response.getWriter().write(jsonData);
	    			
					logger.info("资源转发表transfer_resource中已经有该转发记录且状态在线，则没法再转发，返回异常2");
					logger.info("[transferOnlineReport_Origin Ret]  " + json);
					logger.info("**************************************************************************\n");
					
					return;
					
				}
				else if((transRes != null) && (transRes.getOnline() == false))
				{
					/*如果资源转发表transfer_resource中已经有该转发记录且状态不在线，则将设备上当前转发链接数加1，并将转发信息状态置为在线*/
					/*
					currentSlaveCnt++;
					res.setCurrentslavecnt(currentSlaveCnt);
					devLiveResourceService.updateByPrimaryKeySelective(res);
					*/
					
					transfer_resource.setOnline(true);
					/*设置转发记录上线时间*/
					transfer_resource.setStarttime(new Date());
					transfer_resourceService.updateByPrimaryKeySelective(transfer_resource);
					
					JSONObject json = new JSONObject();
					json.put("type", type);
					json.put("ret", 0);
					
	                String jsonData = JSON.toJSONString(json);
	                response.setHeader("Connection", "Keep-Alive");
	                response.setContentType("text/html; charset=utf-8"); 
	    			response.setContentLength(jsonData.length());
	    			response.getWriter().write(jsonData);
	    			
					logger.info("资源转发表transfer_resource中已经有该转发记录且状态不在线，并将转发信息状态置为在线");
					logger.info("[transferOnlineReport_Origin Ret]  " + json);
					logger.info("**************************************************************************\n");
					
					return;
					
				}
				else
				{
					/*否则，如果资源转发表transfer_resource中没有该转发记录，将设备上当前转发链接数加1，并向表transfer_resource插入一条转发记录*/
					/*
					currentSlaveCnt++;
					res.setCurrentslavecnt(currentSlaveCnt);
					devLiveResourceService.updateByPrimaryKeySelective(res);
					*/
					
					transfer_resource.setOnline(true);
					/*设置转发记录上线时间*/
					transfer_resource.setStarttime(new Date());
					transfer_resourceService.insertSelective(transfer_resource);
					
					JSONObject json = new JSONObject();
					json.put("type", 3);
					json.put("ret", 0);
					
	                String jsonData = JSON.toJSONString(json);
	                response.setHeader("Connection", "Keep-Alive");
	                response.setContentType("text/html; charset=utf-8"); 
	    			response.setContentLength(jsonData.length());
	    			response.getWriter().write(jsonData);
	    			
					logger.info("资源转发表transfer_resource中没有该转发记录，并向表transfer_resource插入一条转发记录");
					logger.info("[transferOnlineReport_Origin Ret]  " + json);
					logger.info("**************************************************************************\n");
					
					return;
					
				}
				
			} catch (Exception e) {
				logger.error(e.getStackTrace());
				e.printStackTrace();
				
				JSONObject json = new JSONObject();
				json.put("type", 3);
				//返回其他异常2
				json.put("ret", 3);
				
				logger.info("发生其他异常");
				logger.info("[transferOnlineReport_Origin Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
	}
	
    //直播转发流断开上报  type=4
	public void transferOfflineReport(int type, long deviceId, long resourceId, long src, String totalBytes, HttpServletResponse response) 
    {
		try 
		{
			logger.info("***************************transferOfflineReport***************************");
			logger.info("[transferOfflineReport Parameter]  type:" + type + ", deviceId:" + deviceId + ", resourceId:" + resourceId + ", src:" + src);
			
			Transfer_resource transfer_resource = new Transfer_resource();
			transfer_resource.setDeviceid(deviceId);
			transfer_resource.setResourceid(resourceId);
			transfer_resource.setSrc(src+"");
			
			DevLiveResourceKey key = new DevLiveResourceKey();
			//key.setDeviceid(deviceId);
			key.setDeviceid(src);
			key.setResourceid(resourceId);			
			DevLiveResource res = null;
			res = devLiveResourceService.selectByPrimaryKey(key);
			
			/*如果设备上没有要转发的资源，返回异常1*/
			if(res == null)
			{
				/*一般情况下，设备上没有要转发的资源，资源转发表transfer_resource中应该没有对应的转发记录，但为了以防万一，进行删除操作*/
				//transfer_resourceService.deleteSelective(transfer_resource);
				
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 1);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("设备上没有要转发的资源，返回异常1");
				logger.info("[transferOfflineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			
			Transfer_resource transRes = transfer_resourceService.selectByPrimaryKey(transfer_resource);
			
			/*如果资源转发表transfer_resource中没有对应的转发记录，返回异常2*/
			if(transRes == null)
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 2);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("资源转发表transfer_resource中没有对应的转发记录，返回异常2");
				logger.info("[transferOfflineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			/*如果资源转发表transfer_resource中已经有该转发记录且状态已下线，返回异常3*/
			else if((transRes != null) && (transRes.getOnline() == false))
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 3);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("资源转发表transfer_resource中已经有该转发记录且状态已下线，返回异常3");
				logger.info("[transferOfflineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			/*如果资源转发表transfer_resource中已经有对应的转发记录且状态为在线*/
			else
			{
				int currentSlaveCnt = res.getCurrentslavecnt();			
				/*否则，将设备上当前转发链接数减1，并将表transfer_resource的转发记录置为下线*/
				currentSlaveCnt = currentSlaveCnt-1 < 0 ? 0 : currentSlaveCnt-1;
				res.setCurrentslavecnt(currentSlaveCnt);
				devLiveResourceService.updateByPrimaryKeySelective(res);
				transfer_resource.setOnline(false);
				/*设置转发记录下线时间*/
				transfer_resource.setEndtime(new Date());
				/* 将上次的totalBytes加上上报的totalBytes作为新的转发字节数 */
				long newBytes = Integer.parseInt(totalBytes) + Integer.parseInt(transRes.getTotalbytes());
				transfer_resource.setTotalbytes(newBytes + "");
				transfer_resourceService.updateByPrimaryKeySelective(transfer_resource);
				
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 0);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("资源转发表transfer_resource中已经有对应的转发记录且状态为在线，将设备上当前转发链接数减1，并将表transfer_resource的转发记录置为下线");
				logger.info("[transferOfflineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			
			JSONObject json = new JSONObject();
			json.put("type", 4);
			/*返回其他异常2*/
			json.put("ret", 2);
			
			logger.info("发生其他异常");
			logger.info("[transferOfflineReport Ret]  " + json);
			logger.info("**************************************************************************\n");
			
			return;
			
		}
	}
	
    //直播转发流断开上报(源站点端)  type=4
	public void transferOfflineReport_Origin(int type, long deviceId, long resourceId, String src, String totalBytes, HttpServletResponse response) 
    {
		try 
		{
			logger.info("***************************transferOfflineReport_Origin***************************");
			logger.info("[transferOfflineReport_Origin Parameter]  type:" + type + ", deviceId:" + deviceId + ", resourceId:" + resourceId + ", src:" + src + ", totalBytes:" + totalBytes);
			
			Transfer_resource transfer_resource = new Transfer_resource();
			transfer_resource.setDeviceid(deviceId);
			transfer_resource.setResourceid(resourceId);
			transfer_resource.setSrc(src);
			
			/*
			DevLiveResourceKey key = new DevLiveResourceKey();
			//key.setDeviceid(deviceId);
			key.setDeviceid(src);
			key.setResourceid(resourceId);			
			DevLiveResource res = null;
			res = devLiveResourceService.selectByPrimaryKey(key);
			*/
			
			/*如果设备上没有要转发的资源，返回异常1*/
			/*
			if(res == null)
			{
				/*一般情况下，设备上没有要转发的资源，资源转发表transfer_resource中应该没有对应的转发记录，但为了以防万一，进行删除操作*/
				//transfer_resourceService.deleteSelective(transfer_resource);
			/*	
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 1);
				
				logger.info("设备上没有要转发的资源，返回异常1");
				logger.info("[transferOfflineReport Ret]  " + json);
				logger.info("**************************************************************************\n");
				return json;
			}
			*/
			
			Transfer_resource transRes = transfer_resourceService.selectByPrimaryKey(transfer_resource);
			
			/*如果资源转发表transfer_resource中没有对应的转发记录，返回异常1*/
			if(transRes == null)
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 1);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("资源转发表transfer_resource中没有对应的转发记录，返回异常1");
				logger.info("[transferOfflineReport_Origin Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			/*如果资源转发表transfer_resource中已经有该转发记录且状态已下线，返回异常2*/
			else if((transRes != null) && (transRes.getOnline() == false))
			{
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 2);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("资源转发表transfer_resource中已经有该转发记录且状态已下线，返回异常2");
				logger.info("[transferOfflineReport_Origin Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			/*如果资源转发表transfer_resource中已经有对应的转发记录且状态为在线*/
			else
			{				
				/*否则，将设备上当前转发链接数减1，并将表transfer_resource的转发记录置为下线*/
				/*
				int currentSlaveCnt = res.getCurrentslavecnt();			
				currentSlaveCnt = currentSlaveCnt-1 < 0 ? 0 : currentSlaveCnt-1;
				res.setCurrentslavecnt(currentSlaveCnt);
				devLiveResourceService.updateByPrimaryKeySelective(res);
				*/
				
				transfer_resource.setOnline(false);
				/*设置转发记录下线时间*/
				transfer_resource.setEndtime(new Date());
				/* 将上次的totalBytes加上上报的totalBytes作为新的转发字节数 */
				long newBytes = Integer.parseInt(totalBytes) + Integer.parseInt(transRes.getTotalbytes());
				transfer_resource.setTotalbytes(newBytes + "");
				transfer_resourceService.updateByPrimaryKeySelective(transfer_resource);
				
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("ret", 0);
				
                String jsonData = JSON.toJSONString(json);
                response.setHeader("Connection", "Keep-Alive");
                response.setContentType("text/html; charset=utf-8"); 
    			response.setContentLength(jsonData.length());
    			response.getWriter().write(jsonData);
    			
				logger.info("资源转发表transfer_resource中已经有对应的转发记录且状态为在线，并将表transfer_resource的转发记录置为下线");
				logger.info("[transferOfflineReport_Origin Ret]  " + json);
				logger.info("**************************************************************************\n");
				
				return;
				
			}
			
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			
			JSONObject json = new JSONObject();
			json.put("type", 4);
			/*返回其他异常3*/
			json.put("ret", 3);
			
			logger.info("发生其他异常");
			logger.info("[transferOfflineReport_Origin Ret]  " + json);
			logger.info("**************************************************************************\n");
			
			return;
			
		}
	}
}
