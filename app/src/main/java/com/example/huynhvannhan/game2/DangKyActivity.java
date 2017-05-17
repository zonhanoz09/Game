package com.example.huynhvannhan.game2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

public class DangKyActivity extends AppCompatActivity {

    private static final String TAG = "DangKyActivity";
    MyService mm = new MyService();
    Button btndangky;
    EditText edttaikhoan,edtmatkhau,edtmatkhau1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        AnhXa();

        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtmatkhau.getText().toString().equals(edtmatkhau1.getText().toString())) {

                    if (edttaikhoan.getText().toString().equals("")||edtmatkhau.getText().toString().equals(""))
                    {
                        Toast.makeText(DangKyActivity.this, "Chưa nhập tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }else {
                        JSONObject tkmk = new JSONObject();
                        try {
                            tkmk.put("Ten", edttaikhoan.getText().toString());
                            tkmk.put("MatKhau", edtmatkhau.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mm.mSocket.emit("client-gui-taikhoan", tkmk);
                    }
                }
                else
                {
                    Toast.makeText(DangKyActivity.this, "Mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mm.mSocket.on("serverguikqdangky", KetquaDangKi);
    }

    private Emitter.Listener KetquaDangKi = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "[ Emitter.Listener]" + args[0]);
                    try {
                        JSONObject data = (JSONObject) args[0];
                        String noidung = data.getString("noidung");
                        if (noidung!="false")
                        {
                            Toast.makeText(DangKyActivity.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(DangKyActivity.this,DangNhapActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(DangKyActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private void AnhXa() {
        btndangky = (Button)findViewById(R.id.btndangky);
        edttaikhoan = (EditText)findViewById(R.id.edittaikhoan);
        edtmatkhau = (EditText)findViewById(R.id.editmk);
        edtmatkhau1 = (EditText)findViewById(R.id.edtmk1);
    }
}
