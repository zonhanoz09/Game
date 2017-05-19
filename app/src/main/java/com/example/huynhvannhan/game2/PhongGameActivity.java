package com.example.huynhvannhan.game2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

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

        mm.mSocket.on("server-gui-tengiaohuu",TenGiaoHuu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSONObject tk = new JSONObject();
        try {
            tk.put("Ten",ten);
            tk.put("ID",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mm.mSocket.emit("client-thoatkhoiphong",tk);
    }
    private Emitter.Listener TenGiaoHuu = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = new JSONObject(args[0].toString());
                        Toast.makeText(getApplication(), ""+data, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
