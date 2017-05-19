package com.example.huynhvannhan.game2;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.huynhvannhan.game2.Object.OChon;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhongGameActivity extends AppCompatActivity {

    private String ten="";
    private String id = "";
    private String iddp="";
    MyService mm = new MyService();
    Dialog dialog;
    Button bta;

    Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;

    OChon oChon;
    ArrayList<OChon> oChons = new ArrayList<OChon>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_game);
        Anhxa();
        TatButton();

        Intent intent = this.getIntent();
        ten = intent.getStringExtra("ten");
        id= intent.getStringExtra("id");

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.xmldialogsansang);
        dialog.setTitle("Thông báo");
        dialog.setCanceledOnTouchOutside(false);

        bta = (Button)dialog.findViewById(R.id.bta);

        bta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "aaa", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                JSONObject x = new JSONObject();
                try {
                    x.put("id",iddp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mm.mSocket.emit("client-dasansang",x);
            }
        });

        mm.mSocket.on("server-gui-tengiaohuu",TenGiaoHuu);
        mm.mSocket.on("server-gui-daok",DoiPhuongOk);
        mm.mSocket.on("server-tra-idfriend",DoiPhuongDaChon);
    }

    private void Anhxa() {
        bt1 = (Button) findViewById(R.id.button1);
        bt2 = (Button) findViewById(R.id.button2);
        bt3 = (Button) findViewById(R.id.button3);
        bt4 = (Button) findViewById(R.id.button4);
        bt5 = (Button) findViewById(R.id.button5);
        bt6 = (Button) findViewById(R.id.button6);
        bt7 = (Button) findViewById(R.id.button7);
        bt8 = (Button) findViewById(R.id.button8);
        bt9 = (Button) findViewById(R.id.button9);
        oChon = new OChon(bt1.getId(), 0, 0, false, 0);
        oChons.add(oChon);
        oChon = new OChon(bt2.getId(), 1, 0, false, 0);
        oChons.add(oChon);
        oChon = new OChon(bt3.getId(), 2, 0, false, 0);
        oChons.add(oChon);
        oChon = new OChon(bt4.getId(), 0, 1, false, 0);
        oChons.add(oChon);
        oChon = new OChon(bt5.getId(), 1, 1, false, 0);
        oChons.add(oChon);
        oChon = new OChon(bt6.getId(), 2, 1, false, 0);
        oChons.add(oChon);
        oChon = new OChon(bt7.getId(), 0, 2, false, 0);
        oChons.add(oChon);
        oChon = new OChon(bt8.getId(), 1, 2, false, 0);
        oChons.add(oChon);
        oChon = new OChon(bt9.getId(), 2, 2, false, 0);
        oChons.add(oChon);
        TruyenOLanCan();
    }

    public void click(View view) {
        int id = view.getId();
        Toast.makeText(this, "Đã click", Toast.LENGTH_SHORT).show();
        TatButton();
        JSONObject data = new JSONObject();
        try {
            data.put("id",iddp);
            data.put("data","000000000");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mm.mSocket.emit("client-da-chon-o",data);
    }

    private void LayOLanCan(int id) {
        ArrayList<Integer> lancan = new ArrayList<Integer>();
        for (int i = 0; i < oChons.size(); i++) {
            if (oChons.get(i).getId() == id) {
                int ax = oChons.get(i).getX();
                int ay = oChons.get(i).getY();
                for (int j = 0; j < oChons.size(); j++) {
                    int bx = oChons.get(j).getX();
                    int by = oChons.get(j).getY();

                    double dodai = (double) Math.sqrt((Math.pow((ax - bx), 2) + (Math.pow((ay - by), 2))));
                    if (dodai <= 1.5) {
                        lancan.add(oChons.get(j).getId());
                    }
                }
            }
        }
        oChons.get(LayViTri(id)).setLancan(lancan);
    }
    private int LayViTri(int id) {
        for (int i = 0; i < oChons.size(); i++) {
            if (oChons.get(i).getId() == id) {
                return i;
            }
        }
        return 0;
    }
    private void TruyenOLanCan() {
        for (int i = 0; i < oChons.size(); i++) {
            LayOLanCan(oChons.get(i).getId());
        }
    }
    private void TatButton() {
        for (int i=0;i<oChons.size();i++)
        {
            Button btt = (Button)findViewById(oChons.get(i).getId());
            btt.setEnabled(false);
        }
    }
    private void BatButtonChon() {
        for (int i=0;i<oChons.size();i++)
        {
            if (oChons.get(i).getDichta()==0) {
                Button btt = (Button) findViewById(oChons.get(i).getId());
                btt.setEnabled(true);
            }
        }
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
                        iddp = data.getString("id").toString();
                        dialog.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener DoiPhuongOk = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = new JSONObject(args[0].toString());
                        Toast.makeText(getApplication(), ""+data, Toast.LENGTH_SHORT).show();
                        iddp = data.getString("id").toString();
                        BatButtonChon();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener DoiPhuongDaChon = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = new JSONObject(args[0].toString());
                        Toast.makeText(getApplication(), ""+data, Toast.LENGTH_SHORT).show();
                        iddp = data.getString("id").toString();
                        BatButtonChon();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
