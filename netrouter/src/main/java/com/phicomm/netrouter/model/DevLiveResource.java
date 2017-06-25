package com.phicomm.netrouter.model;

import java.util.Date;

public class DevLiveResource extends DevLiveResourceKey {
    private String url;

    private Integer bitrate;

    private Integer maxslavecnt;

    private Integer currentslavecnt;

    private Boolean online;

    private Date firsttime;

    private Date lasttime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getMaxslavecnt() {
        return maxslavecnt;
    }

    public void setMaxslavecnt(Integer maxslavecnt) {
        this.maxslavecnt = maxslavecnt;
    }

    public Integer getCurrentslavecnt() {
        return currentslavecnt;
    }

    public void setCurrentslavecnt(Integer currentslavecnt) {
        this.currentslavecnt = currentslavecnt;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Date getFirsttime() {
        return firsttime;
    }

    public void setFirsttime(Date firsttime) {
        this.firsttime = firsttime;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }
}