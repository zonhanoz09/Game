package com.example.huynhvannhan.game2.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.huynhvannhan.game2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class newBlankFragment extends Fragment {

    ListView l ;

    public newBlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_blank, container, false);
        l = (ListView)view.findViewById(R.id.la);

        //anhxa(view); ok chua

        return view;
    }

    //ham
    private  void anhxa(View view)
    {
        l = (ListView)view.findViewById(R.id.la);

    }






}
