package com.phicomm.netrouter.model;

import java.util.Date;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ParameterList implements Serializable{
    private int type;
    private String deviceId;
    private String path;
    private String host;
    private int resId;
    private int cap;
    private String url;
    private String src;
    private long totalBytes;
    
	public long getTotalBytes() {
		return totalBytes;
	}

	public void setTotalBytes(long totalBytes) {
		this.totalBytes = totalBytes;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

   
}