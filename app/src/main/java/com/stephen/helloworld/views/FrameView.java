package com.stephen.helloworld.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by ZS on 2015-11-04.
 */
public class FrameView extends View {

    /**
     * 边框的宽度 单位dp
     */
    private int mBorderWidth = 2;

    private Paint mPaint;

    public FrameView(Context context) {
        this(context, null);
    }

    public FrameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //dp -- px
        mBorderWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources()
                        .getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(0xffff0000);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        if (getWidth() > getHeight()) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() * 0.4f, mPaint);
        } else {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() * 0.4f, mPaint);
        }
    }


}
