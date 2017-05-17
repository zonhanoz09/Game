package com.example.huynhvannhan.game2.Object;

/**
 * Created by HUYNH VAN NHAN on 20/04/2017.
 */

public class BanBe {
    public BanBe(String id, String ten, boolean online) {
        this.id = id;
        this.ten = ten;
        this.online = online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String ten;
    private boolean online;

    public BanBe(String ten, boolean online) {
        this.ten = ten;
        this.online = online;
    }

    public BanBe() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
