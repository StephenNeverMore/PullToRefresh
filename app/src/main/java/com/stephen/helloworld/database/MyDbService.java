package com.stephen.helloworld.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Stephen on 2015/12/1.
 */
public class MyDbService {

    static SQLiteDatabase mydb;
    private static String TAG = "MyDbService";

    public static synchronized SQLiteDatabase getDatebase() {

        if (mydb == null) {
            mydb = MyDbHelper.helper.getReadableDatabase();
            MyDbHelper.helper.onCreate(mydb);
            mydb.setLockingEnabled(true);
        }
        return mydb;
    }


    public static boolean adddata(double i, String name, String title, String content) {

        try {

            if (checkDataExist(i)) {
                //updata
                updateData(i, name, title, content);
            } else {
                String sql = "insert into mydata(name,title,content) " + "values(?,?,?)";
                //insert data
                doDb(sql, new Object[]{name, title, content});
            }
            Log.d(TAG, "result = " + (checkDataExist(i) ? "true" : "false"));
            Log.d(TAG, "加入数据成功！");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "加入数据错误，信息：" + e.toString());
            return false;
        }
    }


    public static boolean checkDataExist(double i) {
        boolean b = false;
        String sql = "select * from mydata where number = " + i;
        Cursor cursor = MyDbService.getDatebase().rawQuery(sql, null);
        if (cursor.moveToNext()) {
            b = true;
        }
        cursor.close();
        cursor = null;
        return b;
    }

    public static boolean updateData(double i, String name, String title, String content) {
        try {
            String upsql = "update mydata set name=?,title=?,content=?";
            doDb(upsql, new Object[]{i, name, title, content});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void doDb(String sql, Object params[]) {

        SQLiteDatabase db = null;
        db = MyDbService.getDatebase();
        try {
            if (db != null) {
                db.beginTransaction();
                if (params != null) {
                    db.execSQL(sql, params);
                } else {
                    db.execSQL(sql);
                }
                db.setTransactionSuccessful();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


}
