package com.phicomm.netrooter.model;

public class TopoGroup {
    private Long topogroupid;

    private Integer avgdelay;

    private String isp;

    public Long getTopogroupid() {
        return topogroupid;
    }

    public void setTopogroupid(Long topogroupid) {
        this.topogroupid = topogroupid;
    }

    public Integer getAvgdelay() {
        return avgdelay;
    }

    public void setAvgdelay(Integer avgdelay) {
        this.avgdelay = avgdelay;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp == null ? null : isp.trim();
    }
}