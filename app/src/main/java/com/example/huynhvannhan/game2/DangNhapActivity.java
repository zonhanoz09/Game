package com.example.huynhvannhan.game2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DangNhapActivity extends AppCompatActivity {

    Button btndangky,btndangnhap;
    EditText edtTK,editMK;
    private Intent i ;
    private static final String TAG = DangNhapActivity.class.getName();
    private String noidung;

    MyService mm = new MyService();
    ThongTin tt= new ThongTin();
    public boolean ketquadangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        startService(new Intent(getBaseContext(),MyService.class));
        AnhXa();

        btndangnhap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mm.KiemTraDangNhap(edtTK.getText().toString(),editMK.getText().toString());
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(ii);
            }
        });

        mm.mSocket.on("serverguikqdangky", KetquaDangNhap);
    }

    private void AnhXa() {
        btndangky = (Button)findViewById(R.id.btndangky);
        btndangnhap = (Button)findViewById(R.id.btndangnhap);
        edtTK= (EditText)findViewById(R.id.edittaikhoan);
        editMK = (EditText)findViewById(R.id.editmk);
        i= new Intent(DangNhapActivity.this,MainActivity.class);
        i.putExtra("username",edtTK.getText().toString());

    }

    private Emitter.Listener KetquaDangNhap = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray data = new JSONArray(args[0].toString());
                        if (data.length()!=0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject tv = new JSONObject(data.get(i).toString());
                                noidung = tv.getString("ID");
                            }

                             if (noidung.equals("")) {
                                    ketquadangnhap = false;
                             } else {
                                i.putExtra("id", noidung);
                                ketquadangnhap = true;
                                 mm.setId(noidung);
                                startActivity(i);
                             }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

}
