package com.stephen.helloworld.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Stephen on 2015/11/12.
 */
public class CoolView extends View {

    private String TAG = "CoolView";
    private TextPaint textPaint;
    private Paint backPaint;
    private int number = 3256;
    private int n = 0;
    private IncreaseListener increaseListener;
    int w;
    int h;
    Rect rect;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            invalidate();
        }
    };


    public CoolView(Context context) {
        this(context, null);
    }

    public CoolView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoolView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(0xffffffff);
        textPaint.setTextSize(200);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        backPaint = new Paint();
        backPaint.setAntiAlias(true);
        backPaint.setColor(0x00000000);
        backPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw background
        w = getWidth();
        h = getHeight();
        rect = new Rect(0, 0, w, h);
        canvas.drawRect(rect, backPaint);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int baseLine = (rect.bottom + rect.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        canvas.drawText(String.valueOf(n), rect.centerX(), baseLine, textPaint);
        increaseListener.numberChanger(n);
        handler.post(runnable);
    }

    public void setTextString(int i) {
        this.number = i;
    }

    public void setOnNumberIncreaseListener(IncreaseListener l) {
        this.increaseListener = l;
    }

    public interface IncreaseListener {
        void numberChanger(int n);
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (n < number - 100) {
                n += 1;
                handler.postDelayed(runnable, 10);
                handler.sendMessage(handler.obtainMessage());
            }
            if (number - 100 <= n && n < number) {
                n += 1;
                handler.postDelayed(runnable, 1000);
                handler.sendMessage(handler.obtainMessage());
            }
        }
    };

}
