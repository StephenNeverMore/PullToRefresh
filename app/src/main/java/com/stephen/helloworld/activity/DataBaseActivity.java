package com.stephen.helloworld.activity;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.stephen.helloworld.R;
import com.stephen.helloworld.utils.ScreenUtil;
import com.stephen.helloworld.utils.ToastUtils;
import com.stephen.helloworld.utils.UIConstants;

public class DataBaseActivity extends Activity implements View.OnTouchListener {


    private String TAG = "DataBaseActivity";
    private boolean isMenu = true;
    private ImageView animImageView;
    private AnimationDrawable animationDrawable;


    private RelativeLayout slide_rl;
    private ImageView back_iv;
    private TextView midle_tv;
    private Button thumb_btn;
    private RelativeLayout.LayoutParams p;

    private int oldTouchX = 0;
    private int thumbW = 0;
    private int screenW = 0;

    private int maxLeft = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        screenW = this.getWindowManager().getDefaultDisplay().getWidth();
        animImageView = (ImageView) findViewById(R.id.menu_close);

        initSlideViews();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) slide_rl.getLayoutParams();
        params.width = (int) (ScreenUtil.getScreenWidth(DataBaseActivity.this) / UIConstants.UI_BASE_WIDTH_LAND * 392);
//        params.width = (int) (ScreenUtil.getScreenWidth(DataBaseActivity.this) / UIConstants.UI_BASE_WIDTH_LAND * 86);
        params.height = (int) (ScreenUtil.getScreenHeight(DataBaseActivity.this) / UIConstants.UI_BASE_HEIGHT_LAND * 86);
        slide_rl.setLayoutParams(params);


        RelativeLayout.LayoutParams thumbParams = (RelativeLayout.LayoutParams) thumb_btn.getLayoutParams();
        thumbParams.width = thumbParams.height = (int) (ScreenUtil.getScreenHeight(DataBaseActivity.this) / UIConstants.UI_BASE_HEIGHT_LAND * 81);
        thumb_btn.setLayoutParams(thumbParams);
        thumbW = thumbParams.width;

        RelativeLayout.LayoutParams backParams = (RelativeLayout.LayoutParams) back_iv.getLayoutParams();
//        backParams.width = backParams.height = (int) (ScreenUtil.getScreenHeight(DataBaseActivity.this) / UIConstants.UI_BASE_HEIGHT_LAND * 86);
        backParams.width = (int) (ScreenUtil.getScreenWidth(DataBaseActivity.this) / UIConstants.UI_BASE_WIDTH_LAND * 392);
        backParams.height = (int) (ScreenUtil.getScreenHeight(DataBaseActivity.this) / UIConstants.UI_BASE_HEIGHT_LAND * 86);
        back_iv.setLayoutParams(backParams);


        maxLeft = params.width - params.leftMargin - thumbW;

        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast(DataBaseActivity.this, "focus point here!");
            }
        });
    }

    private void initSlideViews() {

        slide_rl = (RelativeLayout) findViewById(R.id.slide_rl);
        back_iv = (ImageView) findViewById(R.id.back_iv);
        thumb_btn = (Button) findViewById(R.id.thumb_btn);
        thumb_btn.setOnTouchListener(this);
        midle_tv = (TextView) findViewById(R.id.mid_tv);

        p = (RelativeLayout.LayoutParams) thumb_btn.getLayoutParams();


    }

    public void doCreateDb(View view) {
//        boolean isFinished = MyDbService.adddata(201251,"Stephen", "test database", "does he success???");
//        Log.d(TAG, "add data " + isFinished);
//        Toast.makeText(DataBaseActivity.this, "1234567890", Toast.LENGTH_SHORT).show();

        if (isMenu) {
            animImageView.setBackgroundResource(R.drawable.close);
            animationDrawable = (AnimationDrawable) animImageView.getBackground();
            animationDrawable.start();

        } else {
            animImageView.setBackgroundResource(R.drawable.menu);
            animationDrawable = (AnimationDrawable) animImageView.getBackground();
            animationDrawable.start();

        }
        isMenu = !isMenu;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                thumb_btn.setBackgroundResource(R.drawable.map_fly2);
                oldTouchX = (int) event.getRawX();
                thumbW = v.getWidth();

                Log.d(TAG, "oldTouchX = " + oldTouchX + "    thumbW = " + thumbW + "      leftmargin = " + p.leftMargin + "    rightmargin = " + p.rightMargin);

                back_iv.setVisibility(View.VISIBLE);

                float scale = (float) (392.0 / 81.0);
                ViewHelper.setPivotX(back_iv, 0);
                ViewHelper.setPivotY(back_iv, 0.5f);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(back_iv, "scaleX", 1, scale);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(back_iv, "translationX", 81, 392);
                android.animation.ObjectAnimator animator = android.animation.ObjectAnimator.ofFloat(back_iv, "alpha", 0.2f, 1.0f);
                animator.setDuration(150);
                animator.start();
//                animator.addListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(com.nineoldandroids.animation.Animator animator) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(com.nineoldandroids.animation.Animator animator) {
//                        midle_tv.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(com.nineoldandroids.animation.Animator animator) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(com.nineoldandroids.animation.Animator animator) {
//
//                    }
//                });
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        midle_tv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:

                int newX = (int) event.getRawX();
                int deltX = newX - oldTouchX - 5;

                Log.d(TAG, "newX = " + newX + "    deltX = " + deltX);


//                if (deltX >= 0 && deltX <= screenW - thumbW) {
                if (deltX >= 0 && deltX <= maxLeft) {
                    p.setMargins(deltX, 0, 0, 0);
                }else if (deltX > maxLeft){
                    p.setMargins(maxLeft, 0, 0, 0);
                }else if (deltX < 0){
                    p.setMargins(0,0,0,0);
                }
                thumb_btn.setLayoutParams(p);
                if (deltX >= maxLeft) {
                    midle_tv.setText("松开起飞");
                } else {
                    midle_tv.setText("向右滑动");
                }

                break;
            case MotionEvent.ACTION_UP:


//                if (midle_tv.getText().toString().equals("松开起飞")) {
                if (p.leftMargin >= maxLeft) {
                    ToastUtils.showShortToast(DataBaseActivity.this, "准备起飞………………");
                } else {
                    ToastUtils.showShortToast(DataBaseActivity.this, "取消起飞………………");
                }
                p.leftMargin = 0;
                midle_tv.setText("向右滑动");
                thumb_btn.setBackgroundResource(R.drawable.map_fly);
                thumb_btn.setLayoutParams(p);
                midle_tv.setVisibility(View.GONE);
                back_iv.setVisibility(View.GONE);

                break;
        }
        return false;
    }


}
