package com.phicomm.netrouter.model;

public class Transfer_resource extends Transfer_resourceKey {
    private Boolean online;

    private String totalbytes;

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
}