package com.huashi.app.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.huashi.app.Config.Constant;

/**
 * Created by Administrator on 2016/5/5.
 */
public class SearchDBHelper extends SQLiteOpenHelper {

    //数据库版本号
    private static  final  int DB_VERSION=2;

    /***
     * 数据库构造器
     * @param context
     */
    public SearchDBHelper(Context context) {
        super(context, Constant.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constant.CREAT_HISTORY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     onCreate(db);
    }




}
