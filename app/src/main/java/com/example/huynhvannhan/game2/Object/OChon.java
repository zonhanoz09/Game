package com.example.huynhvannhan.game2.Object;

import java.util.ArrayList;

/**
 * Created by HUYNH-VAN-NHAN on 05/19/17.
 */

public class OChon {
    private  int id,x,y;
    private boolean chon;
    private int dichta;
    private int otruoc;
    private ArrayList<Integer> lancan = new ArrayList<Integer>();

    public OChon() {
    }

    public OChon(int id, int x, int y, boolean chon, int otruoc) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.chon = chon;
        this.otruoc = otruoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isChon() {
        return chon;
    }

    public void setChon(boolean chon) {
        this.chon = chon;
    }

    public int getDichta() {
        return dichta;
    }

    public void setDichta(int dichta) {
        this.dichta = dichta;
    }

    public int getOtruoc() {
        return otruoc;
    }

    public void setOtruoc(int otruoc) {
        this.otruoc = otruoc;
    }

    public ArrayList<Integer> getLancan() {
        return lancan;
    }

    public void setLancan(ArrayList<Integer> lancan) {
        this.lancan = lancan;
    }
}
