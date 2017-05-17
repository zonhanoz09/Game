package com.example.huynhvannhan.game2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.huynhvannhan.game2.Fragment.BanBeFragment;
import com.example.huynhvannhan.game2.Fragment.GameFragment;
import com.example.huynhvannhan.game2.Fragment.ShopFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    MyService mm = new MyService();
    private String id,username;
    private ArrayList<String> dsbanbe= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        id = intent.getStringExtra("id");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tab = (TabLayout)findViewById(R.id.tab);
        tab.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2) {
                    LayDanhSachBanBe(id);
                }
                if(position == 1) {
                    LayThongTinUser();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

    }

    public void LayDanhSachBanBe(String id) {
        JSONObject tkmkdn = new JSONObject();
        try {
            tkmkdn.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mm.mSocket.emit("client-lay-dsbanbe", tkmkdn);
    }
    public void LayThongTinUser() {
        mm.mSocket.emit("client-lay-thongtin-user","");
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    return new ShopFragment();
                case 1:
                    return new GameFragment();
                case 2:
                    return new BanBeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Shop";
                case 1:
                    return "Game";
                case 2:
                    return "Bạn Bè";
            }
            return null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(),MyService.class));
    }
}
