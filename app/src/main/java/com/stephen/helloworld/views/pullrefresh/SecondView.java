package com.stephen.helloworld.views.pullrefresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.AttributeSet;
import android.view.View;

import com.stephen.helloworld.R;

/**
 * Created by Stephen on 2015/12/19.
 */
public class SecondView extends View {

    private Bitmap bitmap;

    public SecondView(Context context) {
        super(context);
        init();
    }

    public SecondView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SecondView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        bitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pull_end_image_frame_05));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureWidth(widthMeasureSpec) * bitmap.getWidth() / bitmap.getHeight());
    }

    private int measureWidth(int widthMeasureWidth) {

        int result = 0;
        int size = MeasureSpec.getSize(widthMeasureWidth);
        int mode = MeasureSpec.getMode(widthMeasureWidth);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = bitmap.getWidth();
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

}
