package com.phicomm.netrouter.model;

public class DevLiveResource extends DevLiveResourceKey {
    private String url;

    private Integer bitrate;

    private Integer maxslavecnt;

    private Integer currentslavecnt;

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
}