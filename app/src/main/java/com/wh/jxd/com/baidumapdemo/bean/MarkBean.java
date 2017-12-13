package com.wh.jxd.com.baidumapdemo.bean;

import java.io.Serializable;

/**
 * Created by kevin321vip on 2017/12/12.
 * 标记点的bean
 */

public class MarkBean implements Serializable {
    private String title;
    private String ima_url;
    private String content;
    private String time;
    private String address;
    private double longitude;//经度
    private double latitude;//纬度

    public MarkBean(String title, String address, double longitude, double latitude) {
        this.title = title;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public MarkBean(String title, String ima_url, String content, String time, String address, long longitude, long latitude) {
        this.title = title;
        this.ima_url = ima_url;
        this.content = content;
        this.time = time;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIma_url() {
        return ima_url;
    }

    public void setIma_url(String ima_url) {
        this.ima_url = ima_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
