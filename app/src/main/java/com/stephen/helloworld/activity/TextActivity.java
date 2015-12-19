package com.stephen.helloworld.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stephen.helloworld.R;
import com.stephen.helloworld.views.CoolView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TextActivity extends Activity implements View.OnClickListener, CoolView.IncreaseListener {

    private String TAG = "TextActivity";
    private RelativeLayout layout;
    private TextView coolView;
    private CoolView myView;
    int result = 10000;
    int cool = 0;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            coolView.setText(cool + "");
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            coolView.setBackgroundColor(Color.argb(255, r, g, b));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        coolView = (TextView) findViewById(R.id.cool_tv);
        coolView.setOnClickListener(this);
        myView = (CoolView) findViewById(R.id.myview);
        myView.setTextString(9999999);
        myView.setOnNumberIncreaseListener(this);

        layout = (RelativeLayout) findViewById(R.id.main_root);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (cool < result) {
                    cool++;
                    handler.sendMessage(handler.obtainMessage());
                }
            }
        };
    }


    @Override
    public void onClick(View v) {
        timer.schedule(timerTask, 0, 10);
        coolView.setClickable(false);
    }

    @Override
    public void numberChanger(int n) {
        Log.d(TAG, "n = " + n);
        if (n < 499) {
            layout.setBackgroundColor(0xff88fa66);
        } else if (499 <= n && n < 999) {
            layout.setBackgroundColor(0xff89e566);
        } else if (999 <= n && n < 1499) {
            layout.setBackgroundColor(0xff89ce66);
        } else if (1499 <= n && n < 1999) {
            layout.setBackgroundColor(0xff899166);
        } else if (1999 <= n && n < 2499) {
            layout.setBackgroundColor(0xff887666);
        } else if (2499 <= n && n < 2999) {
            layout.setBackgroundColor(0xff8a6066);
        } else if (2999 <= n && n < 3499) {
            layout.setBackgroundColor(0xff894a66);
        } else if (3499 <= n && n < 3999) {
            layout.setBackgroundColor(0xff8a3766);
        } else if (3999 <= n && n < 4499) {
            layout.setBackgroundColor(0xff8b2366);
        } else if (4499 <= n && n < 4999) {
            layout.setBackgroundColor(0xff8b1366);
        } else {
            layout.setBackgroundColor(0xffef0e66);
        }

    }
}
