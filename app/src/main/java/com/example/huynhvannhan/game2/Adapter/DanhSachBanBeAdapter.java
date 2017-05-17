package com.example.huynhvannhan.game2.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.huynhvannhan.game2.Object.BanBe;
import com.example.huynhvannhan.game2.R;

import java.util.List;

/**
 * Created by HUYNH VAN NHAN on 20/04/2017.
 */

public class DanhSachBanBeAdapter extends BaseAdapter {

    Context mcontext;
    int mLayout;
    List<BanBe> arrBanBe;
    public DanhSachBanBeAdapter(Context context, int layout, List<BanBe>banBeList)
    {
        mcontext = context;
        mLayout= layout;
        arrBanBe = banBeList;
    }
    @Override
    public int getCount() {
        return arrBanBe.size();
    }

    @Override
    public Object getItem(int position) {
        return arrBanBe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mLayout,null);


        TextView tvten = (TextView)convertView.findViewById(R.id.tenban);
        RadioButton cbtrangthai = (RadioButton) convertView.findViewById(R.id.trangthai);

        tvten.setText(arrBanBe.get(position).getTen().toString());
        if (arrBanBe.get(position).isOnline())
        {
            cbtrangthai.setChecked(true);
        }
        else {
            cbtrangthai.setChecked(false);
        }

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }
}
