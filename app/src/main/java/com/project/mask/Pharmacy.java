package com.project.mask;

public class Pharmacy {
    private String addr;
    private String created_at;
    private float lat;
    private float lng;
    private String name;
    private String remain_stat;
    private String stock_at;
    private String type;

    public Pharmacy(String addr, String created_at, float lat, float lng, String name, String remain_stat, String stock_at, String type) {
        this.addr = addr;
        this.created_at = created_at;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.remain_stat = remain_stat;
        this.stock_at = stock_at;
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemain_stat() {
        return remain_stat;
    }

    public void setRemain_stat(String remain_stat) {
        this.remain_stat = remain_stat;
    }

    public String getStock_at() {
        return stock_at;
    }

    public void setStock_at(String stock_at) {
        this.stock_at = stock_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
