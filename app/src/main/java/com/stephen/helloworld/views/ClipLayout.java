package com.stephen.helloworld.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by ZS on 2015-11-04.
 */
public class ClipLayout extends RelativeLayout {

    private FrameView frameView;//圆圈
    private ZoomImageView zoomImageView;//待剪裁的图片

    public ClipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        zoomImageView = new ZoomImageView(context);
        frameView = new FrameView(context);
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(zoomImageView, layoutParams);
        addView(frameView, layoutParams);
    }

    public void setBitmap(Bitmap bitmap) {
        zoomImageView.setImageBitmap(bitmap);
    }

    /**
     * 裁切图片
     *
     * @return
     */
    public Bitmap clip() {
        return zoomImageView.clip();
    }
}
