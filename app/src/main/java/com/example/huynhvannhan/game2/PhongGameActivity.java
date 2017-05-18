package com.example.huynhvannhan.game2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PhongGameActivity extends AppCompatActivity {

    private String ten="";
    private String id = "";
    MyService mm = new MyService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_game);
        Intent intent = this.getIntent();
        ten = intent.getStringExtra("ten");
        id= intent.getStringExtra("id");
        Toast.makeText(this, ten+" "+ id, Toast.LENGTH_SHORT).show();
        JSONObject tk = new JSONObject();
        try {
            tk.put("Ten",ten);
            tk.put("ID",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mm.mSocket.emit("client-gui-tk-giaohuu",tk);
    }
}
