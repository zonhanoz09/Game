package com.example.huynhvannhan.game2.Fragment;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhvannhan.game2.MyService;
import com.example.huynhvannhan.game2.Object.BanBe;
import com.example.huynhvannhan.game2.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameFragment extends Fragment {
    private static final String Ten = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String ten;
    private String mParam2;

    private TextView tvgamecapdo,tvgameten,tvgamecup;
    private Button btgamechien;

    public MyService mm = new MyService();

    private String socketid = "";

    Dialog dialog;

    private OnFragmentInteractionListener mListener;


    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(Ten, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ten = getArguments().getString(Ten);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.xmldialogframentbanbe);
        dialog.setTitle("Thông báo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game, container, false);

        tvgameten = (TextView)view.findViewById(R.id.gameten);
        tvgamecapdo = (TextView)view.findViewById(R.id.gamecapdo);
        tvgamecup = (TextView)view.findViewById(R.id.gamecup);
        btgamechien = (Button)view.findViewById(R.id.gamechien);

        mm.mSocket.emit("client-lay-thongtin-user","");

        mm.mSocket.on("server-gui-data-user",ThongTinUser);

        btgamechien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject thongtin = new JSONObject();
                try {
                    thongtin.put("socketid",socketid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mm.mSocket.emit("client-gui-thongtin-chien",thongtin);
            }
        });
        
        return view;
    }

    private Emitter.Listener ThongTinUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray data = new JSONArray(args[0].toString());
                        JSONObject tv = new JSONObject(data.get(0).toString());

                        tvgameten.setText(tv.getString("username").toString());
                        tvgamecup.setText(tv.getString("cup").toString());
                        tvgamecapdo.setText(tv.getString("level").toString());
                        socketid = tv.getString("socketid").toString();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
