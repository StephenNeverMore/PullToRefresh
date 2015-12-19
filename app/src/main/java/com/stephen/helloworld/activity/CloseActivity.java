package com.stephen.helloworld.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.stephen.helloworld.R;

public class CloseActivity extends Activity implements View.OnClickListener {

    private RelativeLayout root;
    private View top, midle1, midle2, bottom;
    private boolean isCancleOpened = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        root = (RelativeLayout) findViewById(R.id.root);
        root.setOnClickListener(this);
        top = findViewById(R.id.up);
        midle1 = findViewById(R.id.midle1);
        midle2 = findViewById(R.id.midle2);
        bottom = findViewById(R.id.down);
    }
    @Override
    public void onClick(View v) {
        if (isCancleOpened) {
            final RotateAnimation rotateAnimation3 = new RotateAnimation(45, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation3.setDuration(200);
            final RotateAnimation rotateAnimation4 = new RotateAnimation(-45, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation4.setDuration(200);
            midle1.setAnimation(rotateAnimation4);
            midle2.setAnimation(rotateAnimation3);
            midle1.startAnimation(rotateAnimation3);
            midle2.startAnimation(rotateAnimation4);

            rotateAnimation3.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    top.setVisibility(View.VISIBLE);
                    bottom.setVisibility(View.VISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        } else {
            final RotateAnimation rotateAnimation1 = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation1.setDuration(200);
            rotateAnimation1.setFillAfter(true);
            final RotateAnimation rotateAnimation2 = new RotateAnimation(0, -45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation2.setDuration(200);
            rotateAnimation2.setFillAfter(true);
            TranslateAnimation downAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 2);
            downAnimation.setDuration(200);
            TranslateAnimation upAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -2);
            upAnimation.setDuration(200);
            upAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    top.setVisibility(View.GONE);
                    bottom.setVisibility(View.GONE);
                    midle1.setAnimation(rotateAnimation1);
                    midle2.setAnimation(rotateAnimation2);
                    midle1.startAnimation(rotateAnimation1);
                    midle2.startAnimation(rotateAnimation2);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            top.setAnimation(downAnimation);
            bottom.setAnimation(upAnimation);
            top.startAnimation(downAnimation);
            bottom.startAnimation(upAnimation);
        }
        isCancleOpened = !isCancleOpened;
    }
}
