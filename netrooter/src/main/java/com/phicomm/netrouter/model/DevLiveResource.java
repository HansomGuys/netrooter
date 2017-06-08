package com.phicomm.netrouter.model;

public class DevLiveResource extends DevLiveResourceKey {
    private String path;

    private Integer bitrate;

    private Integer maxslavecnt;

    private Integer currentslavecnt;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
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