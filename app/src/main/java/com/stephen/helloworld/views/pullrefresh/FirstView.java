package com.stephen.helloworld.views.pullrefresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.stephen.helloworld.R;

/**
 * Created by Stephen on 2015/12/19.
 */
public class FirstView extends View {

    private Bitmap initialBitmap;
    private int measuredWidth;
    private int measuredHeight;
    private Bitmap endBitmap;
    private float mCurrentProgress;
    private Bitmap scaleBitmap;
    private String TAG = "FirstView";


    public FirstView(Context context) {
        super(context);
        init(context);
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FirstView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {

        initialBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pull_image));
        endBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pull_end_image_frame_05));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");

        setMeasuredDimension(measuredWidth(widthMeasureSpec), measuredWidth(widthMeasureSpec) * endBitmap.getHeight() / endBitmap.getWidth());
    }


    private int measuredWidth(int widthMeasureSpec) {
        Log.d(TAG,"measuredWidth");

        int result = 0;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = endBitmap.getWidth();
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");

        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
        scaleBitmap = Bitmap.createScaledBitmap(initialBitmap, measuredWidth, measuredWidth * initialBitmap.getHeight() / initialBitmap.getWidth(), true);

        Log.d(TAG,"measuredWidth = " + measuredWidth);
        Log.d(TAG,"measuredHeight = " + measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw");

        canvas.scale(mCurrentProgress, mCurrentProgress, measuredWidth / 2, measuredHeight / 2);
        canvas.drawBitmap(scaleBitmap, 0, measuredHeight / 4, null);
    }

    public void setCurrentProgress(float progress) {
        mCurrentProgress = progress;
        invalidate();
    }

}
