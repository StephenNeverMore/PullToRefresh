package com.stephen.helloworld.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;

import com.stephen.helloworld.R;
import com.stephen.helloworld.views.pullrefresh.FirstView;
import com.stephen.helloworld.views.pullrefresh.PullToRefreshListView;
import com.stephen.helloworld.views.pullrefresh.SecondView;
import com.stephen.helloworld.views.pullrefresh.ThirdView;

import java.util.ArrayList;
import java.util.List;

public class PullRefreshAty extends Activity implements PullToRefreshListView.OnMyRefreshListener {

//    private FirstView firstView;
//    private SeekBar seekBar;
//    private SecondView secondView;
//    private AnimationDrawable second_ad;
//    private ThirdView thirdView;
//    private AnimationDrawable third_ad;
//
//    private String TAG = "PullRefreshAty";
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            Log.d(TAG, "handleMessage");
//            second_ad.stop();
//            secondView.setVisibility(View.GONE);
//            thirdView.setVisibility(View.VISIBLE);
//            third_ad.start();
//        }
//    };


    private PullToRefreshListView pullToRefreshListView;
    private List<String> data;
    private ArrayAdapter<String> arrayAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pullToRefreshListView.setOnRefreshComplete();
            arrayAdapter.notifyDataSetChanged();
            pullToRefreshListView.setSelection(0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pull_refresh_aty);
//
//        seekBar = (SeekBar) findViewById(R.id.seekbar);
//        seekBar.setMax(100);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                firstView.setCurrentProgress(progress / 100.0f);
//                if (progress == 100) {
//                    firstView.setVisibility(View.GONE);
//                    secondView.setVisibility(View.VISIBLE);
//                    second_ad.start();
//                    handler.sendEmptyMessageDelayed(1,1000);
//                } else {
//                    firstView.setVisibility(View.VISIBLE);
//                    secondView.setVisibility(View.GONE);
//                    second_ad.stop();
//                    thirdView.setVisibility(View.GONE);
//                    third_ad.stop();
//                }
//            }
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {}
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {}
//        });
//
//        firstView = (FirstView) findViewById(R.id.firstview);
//
//        secondView = (SecondView) findViewById(R.id.secondview);
//        secondView.setBackgroundResource(R.drawable.pull_to_refresh_second_anim);
//        second_ad = (AnimationDrawable) secondView.getBackground();
//
//        thirdView = (ThirdView) findViewById(R.id.thirdview);
//        thirdView.setBackgroundResource(R.drawable.pull_to_refresh_third_anim);
//        third_ad = (AnimationDrawable) thirdView.getBackground();

        setContentView(R.layout.test_listview);


        data = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            data.add("test" + i);
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.test_lv);

        pullToRefreshListView.setAdapter(arrayAdapter);
        pullToRefreshListView.setOnMyRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        data.add(0,"new data");
        handler.sendEmptyMessageDelayed(1, 3000);
    }
}
