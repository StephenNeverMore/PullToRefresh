package com.stephen.helloworld.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Stephen on 2015/12/2.
 */
public class ToastUtils {

    private static Toast toast;

    public static void showShortToast(Context context, String msg) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

}
