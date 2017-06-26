package com.phicomm.netrouter.model;

import java.util.Date;

public class Transfer_resource extends Transfer_resourceKey {
    private Boolean online;

    private String totalbytes;

    private Date starttime;

    private Date endtime;

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getTotalbytes() {
        return totalbytes;
    }

    public void setTotalbytes(String totalbytes) {
        this.totalbytes = totalbytes == null ? null : totalbytes.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}