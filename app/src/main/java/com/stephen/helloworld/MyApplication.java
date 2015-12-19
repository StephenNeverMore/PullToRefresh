package com.stephen.helloworld;

import android.app.Application;
import android.content.Context;

import com.stephen.helloworld.database.MyDbHelper;

/**
 * Created by Stephen on 2015/12/1.
 */
public class MyApplication extends Application {


    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        MyDbHelper helper = new MyDbHelper(context);

    }
}
