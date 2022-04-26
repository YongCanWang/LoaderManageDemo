package com.mapscloud.dtt.loadermanagerdemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.mapscloud.dtt.loadermanagerdemo.bean.Constant;
import com.mapscloud.dtt.loadermanagerdemo.db.DBHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author TomCan
 * @description:
 * @date :2021/10/19 13:26
 */
public class MyContentProvider2 extends ContentProvider {
    private static final String     TAG            = "MyContentProvider2";
    private static final String     AUTHORITY      = "com.mapscloud.dtt.loadermanagerdemo";
    private static final String     AUTHORITY_NAME = ".MyContentProvider";
    public static final  Uri        STUDENT_URI    = Uri.parse("content://" + AUTHORITY + AUTHORITY_NAME);
    public static final  String     URL_PATH       = "MyContentProviderPath";
    public static final  String     URL_PATH2      = "MyContentProviderPath2";
    public static final  String     URL_PATH3      = "MyContentProviderPath3";
    public static final  Uri        CONTENT_URI    = STUDENT_URI.buildUpon().appendPath(URL_PATH).build();
    //匹配成功后的匹配码
    private static final int        MATCH_CODE     = 10001;
    private static final int        MATCH_CODE2    = 10002;
    private static final int        MATCH_CODE3    = 10003;
    private static final UriMatcher uriMatcher;


    static {
        //匹配不成功返回NO_MATCH(-1)
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //添加我们需要匹配的uri
        uriMatcher.addURI(AUTHORITY + AUTHORITY_NAME, URL_PATH, MATCH_CODE);
        uriMatcher.addURI(AUTHORITY + AUTHORITY_NAME, URL_PATH2, MATCH_CODE2);
        uriMatcher.addURI(AUTHORITY + AUTHORITY_NAME, URL_PATH3, MATCH_CODE3);
    }

    private SQLiteDatabase readableDatabase;


    @Override
    public boolean onCreate() {
        // 创建引擎类 数据库对象，该对象支撑 增查删改功能
        DBHelper dbHelper = new DBHelper(getContext());
        readableDatabase = dbHelper.getReadableDatabase();
        Log.e(TAG, "onCreate");
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e(TAG, "query");
        // 匹配URI对象，匹配成功 （可能不止一个ContentProvider,所以在query的时候，先匹配需要那一个ContentProvider提供技术支持）
        // 一个ContentProvider不止支持一套数据，可能支持多套数据支撑，所以需要先匹配，查询的是哪一套数据。
        switch (uriMatcher.match(uri)) { // 匹配成功之后的ContentProVider
            // 匹配成功，执行查询功能
            // 查询成功返回结果 Cursor
            case MATCH_CODE:
                Log.e(TAG, "匹配URI对象为：" + MATCH_CODE + "---" + uri.toString());
                return readableDatabase.query(Constant.NAVIGATE_POINT_TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
            case MATCH_CODE2:
                Log.e(TAG, "匹配URI对象为：" + MATCH_CODE2 + "---" + uri.toString());
                break;
            case MATCH_CODE3:
                Log.e(TAG, "匹配URI对象为：" + MATCH_CODE3 + "---" + uri.toString());
                break;
        }

        return null;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (null == uri) {
            Log.e(TAG, "uri is null");
        } else {
            Log.e(TAG, "uri:" + uri.toString());
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e(TAG, "insert --->>> uri:" + uri.toString());
        Log.e(TAG, "insert --->>> ContentValues:" + values.toString());
        switch (uriMatcher.match(uri)) { // 匹配成功之后的ContentProVider
            // 匹配成功，执行查询功能
            // 查询成功返回结果 Cursor
            case MATCH_CODE:
                readableDatabase.insert(Constant.NAVIGATE_POINT_TABLE_NAME,null,  values);
                break;
            case MATCH_CODE2:
                break;
            case MATCH_CODE3:
                break;
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) { // 匹配成功之后的ContentProVider
            // 匹配成功，执行查询功能
            // 查询成功返回结果 Cursor
            case MATCH_CODE:
               return readableDatabase.delete(Constant.NAVIGATE_POINT_TABLE_NAME,selection,  selectionArgs);
            case MATCH_CODE2:
                break;
            case MATCH_CODE3:
                break;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
