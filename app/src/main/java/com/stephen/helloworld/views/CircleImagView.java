package com.stephen.helloworld.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ZS on 2015-11-05.
 */
public class CircleImagView extends ImageView {

    public CircleImagView(Context context) {
        this(context, null);
    }

    public CircleImagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(getCircleBitmap(bm));
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();//bitmap的宽和高是相等的~~~
        canvas.drawCircle(x / 2, x / 2, x / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return newBitmap;
    }

}
