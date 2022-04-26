package com.mapscloud.dtt.loadermanagerdemo.loader;

import android.content.Context;
import android.database.Cursor;

import com.mapscloud.dtt.loadermanagerdemo.bean.Constant;
import com.mapscloud.dtt.loadermanagerdemo.provider.MyContentProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;

/**
 * @author TomCan
 * @description:
 * @date :2021/10/14 14:55
 */
public class POILoader extends CursorLoader {
    private final Context context;
    String TAG = "POILoader";

    public POILoader(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Nullable
    @Override
    protected Cursor onLoadInBackground() {
        Cursor query = context.getContentResolver()
                .query(MyContentProvider.STUDENT_URI.buildUpon().appendPath(MyContentProvider.URL_PATH).build(),
                        Constant.PROJECTION,
                        null, null, null);
        return query;
    }


}
