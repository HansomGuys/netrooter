package com.phicomm.netrooter.model;

public class DevNtwTopo extends DevNtwTopoKey {
    private String metriclist;

    private Integer publicipaddr;

    public String getMetriclist() {
        return metriclist;
    }

    public void setMetriclist(String metriclist) {
        this.metriclist = metriclist == null ? null : metriclist.trim();
    }

    public Integer getPublicipaddr() {
        return publicipaddr;
    }

    public void setPublicipaddr(Integer publicipaddr) {
        this.publicipaddr = publicipaddr;
    }
}