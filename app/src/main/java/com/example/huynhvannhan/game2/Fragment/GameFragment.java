package com.example.huynhvannhan.game2.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhvannhan.game2.R;

public class GameFragment extends Fragment {
    private static final String Ten = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String ten;
    private String mParam2;

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

        //Toast.makeText(getContext(), "aa "+ten, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        if(isVisibleToUser){

        }else {

        }
    }

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
