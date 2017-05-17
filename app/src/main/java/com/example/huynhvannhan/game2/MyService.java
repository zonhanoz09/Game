package com.example.huynhvannhan.game2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by HUYNH VAN NHAN on 4/12/2017.
 */


public class MyService extends Service {

    public Socket mSocket;{
        try {
            mSocket = IO.socket("http://192.168.1.17:3000");
        } catch (URISyntaxException e) {}
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mSocket.connect();
    }

    public void KiemTraDangNhap(String taikhoan,String matkhau) {
        JSONObject tkmkdn = new JSONObject();
        try {
            tkmkdn.put("Ten",taikhoan);
            tkmkdn.put("MatKhau",matkhau);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("client-gui-dangnhap", tkmkdn);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }
}
