package com.mapscloud.dtt.loadermanagerdemo.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.loader.content.CursorLoader;

import com.mapscloud.dtt.loadermanagerdemo.bean.Constant;
import com.mapscloud.dtt.loadermanagerdemo.provider.MyContentProvider;

/**
 * @author TomCan
 * @description:
 * @date :2021/10/14 14:55
 */
public class POILoader2 extends CursorLoader {
    String TAG = "POILoader";

    public POILoader2(@NonNull Context context) {
        super(context);
        setUri(MyContentProvider.STUDENT_URI.buildUpon().appendPath(MyContentProvider.URL_PATH2).build());
        setProjection(Constant.PROJECTION);
        setSelection(null);
        setSelectionArgs(null);
        setSortOrder(null);
    }

}
