package com.phicomm.netrouter.model;

public class DevNtwTopo extends DevNtwTopoKey {
    private String metriclist;

    private String publicipaddr;

    public String getMetriclist() {
        return metriclist;
    }

    public void setMetriclist(String metriclist) {
        this.metriclist = metriclist == null ? null : metriclist.trim();
    }

    public String getPublicipaddr() {
        return publicipaddr;
    }

    public void setPublicipaddr(String publicipaddr) {
        this.publicipaddr = publicipaddr;
    }
}