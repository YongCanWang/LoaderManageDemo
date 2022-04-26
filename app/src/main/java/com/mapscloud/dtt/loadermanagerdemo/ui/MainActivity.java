package com.mapscloud.dtt.loadermanagerdemo.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mapscloud.dtt.loadermanagerdemo.R;
import com.mapscloud.dtt.loadermanagerdemo.bean.Constant;
import com.mapscloud.dtt.loadermanagerdemo.bean.CustomPoint;
import com.mapscloud.dtt.loadermanagerdemo.db.NaviPOI;
import com.mapscloud.dtt.loadermanagerdemo.loader.POILoader;
import com.mapscloud.dtt.loadermanagerdemo.provider.MyContentProvider;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        Loader.OnLoadCanceledListener, Loader.OnLoadCompleteListener<Object> {
    private              String   TAG        = "MainActivity";
    private static final String[] PROJECTION = new String[]{"_id", "text_column"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoaderManager.getInstance(this).initLoader(1, null, this);
        LoaderManager.getInstance(this).getLoader(1).registerOnLoadCanceledListener(this);
//        LoaderManager.getInstance(this).getLoader(1).registerListener(1, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e(TAG, "onCreateLoader:id = " + id);
        return new POILoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (null == data) {
            Log.e(TAG, "onLoadFinished: data is null");
        } else {
            List<NaviPOI> result = parseData(data);
            Log.e(TAG, "onLoadFinished ---->>> result ：" + result.size());
            for (NaviPOI naviPOI : result) {
                Log.e(TAG, "onLoadFinished ---->>> naviPOI:" + naviPOI.toString());
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (null == loader) {
            Log.e(TAG, "onLoaderReset: loader is null");
        } else {
            Log.e(TAG, "onLoaderReset:" + loader.toString());
        }
    }


    public void baseSpatialQuery(View view) {


    }

    public void spatialQuery(View view) {


    }


    public void startLoader(View view) {
        LoaderManager.getInstance(this).getLoader(1).startLoading();
    }

    public void stopLoader(View view) {
        LoaderManager.getInstance(this).getLoader(1).stopLoading();
    }

    public void restartLoader(View view) {
        LoaderManager.getInstance(this).restartLoader(1, null, this);
    }

    public void reset(View view) {
        LoaderManager.getInstance(this).getLoader(1).reset();
    }

    public void cancelLoad(View view) {
        LoaderManager.getInstance(this).getLoader(1).cancelLoad();
    }


    public void query(View view) {
        Cursor query = getContentResolver()
                .query(MyContentProvider.STUDENT_URI.buildUpon().appendPath(MyContentProvider.URL_PATH).build(),
                        Constant.PROJECTION,
                        null, null, null);

        List<NaviPOI> result = parseData(query);
        if (null == result) return;
        Log.e(TAG, "result ：" + result.size());
        for (NaviPOI naviPOI : result) {
            Log.e(TAG, "naviPOI:" + naviPOI.toString());
        }
    }


    public void query2(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String selection = "lon=? AND lat=?"; //  查询条件
            String[] selectArgs = new String[]{"10002.0", "10001.0"}; // 参数三占位符
            Uri build = MyContentProvider.STUDENT_URI.buildUpon().appendPath(MyContentProvider.URL_PATH2).build();
            Cursor query = getContentResolver().query(build, Constant.PROJECTION, selection, selectArgs, null);

            List<NaviPOI> result = parseData(query);
            if (null == result) return;
            Log.e(TAG, "result ：" + result.size());
            for (NaviPOI naviPOI : result) {
                Log.e(TAG, "naviPOI:" + naviPOI.toString());
            }
        }
    }


    public void insert(View view) {
        NaviPOI naviPOI = new NaviPOI();
        naviPOI.setCn("当前位置");
        naviPOI.setLat(10001);
        naviPOI.setLon(10002);
        naviPOI.setLat_i((int) (10001 * 1E5));
        naviPOI.setLon_i((int) (10002 * 1E5));
        naviPOI.setName("环星大厦");
        naviPOI.setProvince("北京");
        naviPOI.setRegion("海淀区");
        naviPOI.setCity("北京市");
//        naviPOI.setTown("");
//        naviPOI.setStreet(location.getStreet());
//        naviPOI.setFloor(location.getFloor());
        naviPOI.setAddress("北京市海淀区花园路环星大厦");
        naviPOI.setType(1);

        ContentValues values = new ContentValues();
        values.put(Constant.NAVIGATE_POINT_NAME, naviPOI.getCn());
        values.put(Constant.NAVIGATE_POINT_LON, naviPOI.getLon());
        values.put(Constant.NAVIGATE_POINT_LAT, naviPOI.getLat());
        values.put(Constant.NAVIGATE_POINT_PROVINCE, naviPOI.getProvince());
        values.put(Constant.NAVIGATE_POINT_CITY, naviPOI.getCity());
        values.put(Constant.NAVIGATE_POINT_REGION, naviPOI.getRegion());
        values.put(Constant.NAVIGATE_POINT_ADDRESS, naviPOI.getAddress());
        values.put(Constant.NAVIGATE_POINT_TYPE, naviPOI.getType());
        getContentResolver().insert(MyContentProvider.STUDENT_URI.buildUpon()
                .appendPath(MyContentProvider.URL_PATH).build(), values);
    }


    public void delete(View view) {
        Uri build = MyContentProvider.STUDENT_URI.buildUpon().appendPath(MyContentProvider.URL_PATH2).build();
        String selection = "lon=? AND lat=?"; //  查询条件
        String[] selectArgs = new String[]{"10002.0", "10001.0"}; // 参数三占位符
        int delete = getContentResolver().delete(build, selection, selectArgs);
        Log.e(TAG, "delete:" + delete);
    }

    public void clear(View view) {
        Uri build = MyContentProvider.STUDENT_URI.buildUpon().appendPath(MyContentProvider.URL_PATH2).build();
        int delete = getContentResolver().delete(build, null, null);
        Log.e(TAG, "delete:" + delete);
    }


    public List<NaviPOI> parseData(Cursor cursor) {
        List<NaviPOI> result = new ArrayList<NaviPOI>();
        while (cursor.moveToNext()) {
            CustomPoint point = new CustomPoint(Double.valueOf(cursor
                    .getString(2)),
                    Double.valueOf(cursor.getString(3)));
            String string1 = cursor.getString(2);
            String string2 = cursor.getString(3);
            NaviPOI naviPOI = new NaviPOI(cursor.getString(1),
                    Double.valueOf(string2),
                    Double.valueOf(string1));
            naviPOI.setId(cursor.getInt(0) + "");
            naviPOI.setLon_i((int) (Double.valueOf(string1) * 1E5));
            naviPOI.setLat_i((int) (Double.valueOf(string2) * 1E5));
            naviPOI.setProvince(cursor.getString(5));
            naviPOI.setCity(cursor.getString(6));
            naviPOI.setRegion(cursor.getString(7));
            naviPOI.setAddress(cursor.getString(4));
            naviPOI.setType(Integer.valueOf(cursor.getString(8)));
            result.add(naviPOI);
        }
        return result;
    }

    @Override
    public void onLoadCanceled(@NonNull Loader loader) {
        Log.e(TAG, "onLoadCanceled：" + loader.getId());
    }

    @Override
    public void onLoadComplete(@NonNull Loader<Object> loader, @Nullable Object data) {
        Log.e(TAG, "onLoadComplete：" + loader.getId());
    }
}