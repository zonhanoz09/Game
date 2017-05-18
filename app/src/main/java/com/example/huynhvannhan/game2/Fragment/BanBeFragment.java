package com.example.huynhvannhan.game2.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huynhvannhan.game2.ActivityChat;
import com.example.huynhvannhan.game2.Adapter.DanhSachBanBeAdapter;
import com.example.huynhvannhan.game2.MyService;
import com.example.huynhvannhan.game2.Object.BanBe;
import com.example.huynhvannhan.game2.PhongGameActivity;
import com.example.huynhvannhan.game2.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BanBeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String Ten = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = BanBeFragment.class.getName();

    // TODO: Rename and change types of parameters
    private String mParam2;

    private OnFragmentInteractionListener mListener;

     ListView lvbanbe;
    public List<BanBe> dsbanbe = new ArrayList();
    public DanhSachBanBeAdapter adapter;

    private MyService mm = new MyService();
    private String ten= "";
    private String id1 = "" ;
    MenuItem fav;
    public BanBeFragment() {
        // Required empty public constructor
    }

    public static BanBeFragment newInstance(ArrayList arrayList) {
        BanBeFragment fragment = new BanBeFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(Ten, arrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ban_be, container, false);
        lvbanbe = (ListView)view.findViewById(R.id.listview);

        mm.mSocket.on("server-gui-danhsachbanbe",DanhSachBanBe);
        mm.mSocket.on("server-gui-user-online",UserOnline);
        mm.mSocket.on("server-gui-user-offline",UserOffline);


        adapter = new DanhSachBanBeAdapter(getActivity(),R.layout.dong_ban_be, dsbanbe);
        lvbanbe.setAdapter(adapter);

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.xmldialogframentbanbe);
        dialog.setTitle("Thông báo");
        dialog.setCanceledOnTouchOutside(false);

        final Button bta = (Button)dialog.findViewById(R.id.bta);
        final Button btb = (Button)dialog.findViewById(R.id.btb);


        lvbanbe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(dsbanbe.get(position).isOnline())
                {
                    btb.setEnabled(true);
                }
                else {
                    btb.setEnabled(false);
                }

                dialog.show();
                ten = dsbanbe.get(position).getTen().toString();
                id1 = dsbanbe.get(position).getId().toString();
            }
        });
        bta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ActivityChat.class);
                i.putExtra("ten",ten);
                i.putExtra("id",id1);
                startActivity(i);
            }
        });
        btb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PhongGameActivity.class);
                i.putExtra("ten",ten);
                i.putExtra("id",id1);
                startActivity(i);

            }
        });

        return view;
    }

    private Emitter.Listener DanhSachBanBe = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray data = new JSONArray(args[0].toString());
                        dsbanbe.clear();
                        for (int i=0;i<data.length();i++)
                        {
                            JSONObject tv = new JSONObject(data.get(i).toString());
                            if (tv.get("isplay").toString()=="1")
                            {
                                dsbanbe.add(new BanBe(tv.get("ID").toString(),tv.getString("username").toString(),true));
                            }
                            if (tv.get("isplay").toString()=="0")
                            {
                                dsbanbe.add(new BanBe(tv.get("ID").toString(),tv.getString("username").toString(),false));
                            }
                        }

                        Toast.makeText(getActivity(), "Lấy dữ liệu thành công" , Toast.LENGTH_SHORT).show();

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener UserOnline = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = new JSONObject(args[0].toString());

                        for (int i=0;i<dsbanbe.size();i++)
                        {
                            if (dsbanbe.get(i).getTen().equals(data.getString("useronline")))
                            {
                                dsbanbe.get(i).setOnline(true);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };

    private Emitter.Listener UserOffline = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = new JSONObject(args[0].toString());

                        for (int i=0;i<dsbanbe.size();i++)
                        {
                            if (dsbanbe.get(i).getTen().equals(data.getString("useroffline")))
                            {
                                dsbanbe.get(i).setOnline(false);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
