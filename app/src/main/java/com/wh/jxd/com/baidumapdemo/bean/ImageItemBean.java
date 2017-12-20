package com.wh.jxd.com.baidumapdemo.bean;

/**
 * Created by kevin321vip on 2017/12/18.
 */

public class ImageItemBean {
    private String title;
    private int res;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public ImageItemBean(String title, int res) {
        this.title = title;
        this.res = res;
    }
}
