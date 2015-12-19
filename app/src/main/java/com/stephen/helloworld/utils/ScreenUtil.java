package com.stephen.helloworld.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;


public class ScreenUtil {

    /**
     * 根据背景宽高去设置空间的宽高
     *  @param context  上下文
     * @param view     设置宽高的view
     * @param bgwidth  所放背景的宽度
     * @param bgheight 所放背景的高度
     */
    public static void setSizeWithBackground(Context context, View view, double bgwidth, double bgheight) {
        LayoutParams params = view.getLayoutParams();
        params.width = (int) (getScreenWidth(context) / UIConstants.UI_BASE_WIDTH_LAND * bgwidth);
        params.height = (int) (getScreenHeight(context) / UIConstants.UI_BASE_HEIGHT_LAND * bgheight);
        view.setLayoutParams(params);
    }

    /**
     * 确定控件宽度，再根据传来的宽度根据背景进行比例适配
     *
     * @param context  上下文
     * @param width    自己固定的、期望的宽高
     * @param view     设置宽高的View
     * @param bgwidth  所放背景的宽度
     * @param bgheight 所放背景的高度
     */
    public static void setSizeWithWidthAndBackground(Context context, int width, View view, int bgwidth, int bgheight) {
        LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = (int) ((double) width / bgwidth * bgheight);
        view.setLayoutParams(params);
    }

    /**
     * 设置控件的高度，当控件要求宽度是满屏的时候
     *
     * @param context  上下文
     * @param view     设置宽高的View
     * @param bgheight 所放背景的高度
     */
    public static void setSizeFullWidth(Context context, View view, int bgheight) {
        LayoutParams params = view.getLayoutParams();
        params.width = getScreenWidth(context);
        params.height = (int) (getScreenHeight(context) / UIConstants.UI_BASE_HEIGHT_LAND * bgheight);
        view.setLayoutParams(params);
    }


    /**
     * 获取屏幕宽度像素
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels; // 屏幕宽度（像素）
        return width;
    }

    /**
     * 获取屏幕高度像素
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels; // 屏幕高度（像素）
        return height;
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public static int getScreenDensityDpi(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int densityDpi = dm.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return densityDpi;
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density; // 屏幕密度DPI（120 / 160 / 240）
        return density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) (dpValue * getScreenDensity(context) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / getScreenDensity(context) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dp2pxf(Context context, float dpValue) {
        return dpValue * getScreenDensity(context) + 0.5f;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dpf(Context context, float pxValue) {
        return pxValue / getScreenDensity(context) + 0.5f;
    }

}
