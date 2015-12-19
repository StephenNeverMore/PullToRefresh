package com.stephen.helloworld.database;

import android.content.Context;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.stephen.helloworld.MyApplication;

/**
 * Created by Stephen on 2015/12/1.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    private final static String db_name = "myapplication.db";
    private final static int db_version = 1;
    private final static String TAG = "MyDbHelper";


    public static MyDbHelper helper = new MyDbHelper(MyApplication.context);

    public MyDbHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDb = "CREATE TABLE if not exists mydata ( "
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT , "
                + " number INTEGER ,"
                + " name VARCHAR (20) , "
                + " title VARCHAR (20) , "
                + " content VARCHAR (50) )";

        try {
            Log.d(TAG,createDb);
            db.execSQL(createDb);
            db.execSQL("CREATE INDEX IF NOT EXISTS number ON mydata (number)");//创建索引
            db.execSQL("CREATE INDEX IF NOT EXISTS name ON mydata (name)");//创建索引
            db.execSQL("CREATE INDEX IF NOT EXISTS title ON mydata (title)");//创建索引
            Log.d("dataBase", "数据库创建成功！");
        } catch (SQLException e) {
            Log.d(TAG, "创建数据库失败，错误信息： " + e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL("DROP TABLE IF EXITS mydata");
            this.onCreate(db);
        } catch (SQLException e) {
            Log.d(TAG, "更新数据库失败，错误信息： " + e.toString());
        }
    }
}
