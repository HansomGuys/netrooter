package com.phicomm.netrooter.model;

import java.util.Date;

public class NrDevice {
    private Long deviceid;

    private String manufacture;

    private String manufacturesn;

    private Boolean online;

    private Date firstonlinetime;

    private Date latestonlinetime;

    private Integer ipaddr;

    private Integer uplinkbw;

    private Integer downlinkbw;

    private Boolean bemaster;

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture == null ? null : manufacture.trim();
    }

    public String getManufacturesn() {
        return manufacturesn;
    }

    public void setManufacturesn(String manufacturesn) {
        this.manufacturesn = manufacturesn == null ? null : manufacturesn.trim();
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Date getFirstonlinetime() {
        return firstonlinetime;
    }

    public void setFirstonlinetime(Date firstonlinetime) {
        this.firstonlinetime = firstonlinetime;
    }

    public Date getLatestonlinetime() {
        return latestonlinetime;
    }

    public void setLatestonlinetime(Date latestonlinetime) {
        this.latestonlinetime = latestonlinetime;
    }

    public Integer getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(Integer ipaddr) {
        this.ipaddr = ipaddr;
    }

    public Integer getUplinkbw() {
        return uplinkbw;
    }

    public void setUplinkbw(Integer uplinkbw) {
        this.uplinkbw = uplinkbw;
    }

    public Integer getDownlinkbw() {
        return downlinkbw;
    }

    public void setDownlinkbw(Integer downlinkbw) {
        this.downlinkbw = downlinkbw;
    }

    public Boolean getBemaster() {
        return bemaster;
    }

    public void setBemaster(Boolean bemaster) {
        this.bemaster = bemaster;
    }
}