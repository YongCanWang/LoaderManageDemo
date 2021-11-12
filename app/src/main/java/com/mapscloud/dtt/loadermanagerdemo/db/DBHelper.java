package com.mapscloud.dtt.loadermanagerdemo.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    // 数据库信息
    public static final String DB_NAME    = "personalTest.db";
    public static final int    DB_VERSION = 1;

    // 导航点收藏表
    public static final String NAVIGATE_POINT_TABLE_NAME = "navigatepoint";
    public static final String NAVIGATE_POINT_ID         = "_id";
    public static final String NAVIGATE_POINT_NAME       = "name";
    public static final String NAVIGATE_POINT_LON        = "lon";
    public static final String NAVIGATE_POINT_LAT        = "lat";
    public static final String NAVIGATE_POINT_ADDRESS    = "address";
    public static final String NAVIGATE_POINT_PROVINCE   = "province";
    public static final String NAVIGATE_POINT_CITY       = "city";
    public static final String NAVIGATE_POINT_REGION     = "region";
    public static final String NAVIGATE_POINT_TYPE       = "type";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        LogUtils.e("Navigation----------MyDBHelper", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAVIGATE_POINT_TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
//        LogUtils.e("Navigation----------MyDBHelper", "onOpen");
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE IF NOT EXISTS ")
                .append(NAVIGATE_POINT_TABLE_NAME).append(" (")
                .append("_id")
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(NAVIGATE_POINT_NAME).append(" TEXT,")
                .append(NAVIGATE_POINT_LON).append(" TEXT,")
                .append(NAVIGATE_POINT_LAT).append(" TEXT,")
                .append(NAVIGATE_POINT_ADDRESS).append(" TEXT,")
                .append(NAVIGATE_POINT_PROVINCE).append(" TEXT,")
                .append(NAVIGATE_POINT_CITY).append(" TEXT,")
                .append(NAVIGATE_POINT_REGION).append(" TEXT,")
                .append(NAVIGATE_POINT_TYPE).append(" TEXT)");

        db.execSQL(sb.toString());
    }

    @SuppressLint("NewApi")
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
//        LogUtils.e("Navigation----------MyDBHelper", "onConfigure");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
//        LogUtils.e("Navigation----------MyDBHelper", "onDowngrades");
    }


}
