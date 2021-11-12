package com.mapscloud.dtt.loadermanagerdemo.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.mapscloud.dtt.loadermanagerdemo.bean.CustomPoint;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static final String                                    TAG        = "DBManager";
    private        Context                             mContext;
    private static DBManager mDBManager = null;

    private DBManager(Context context) {
        mContext = context;
    }

    public static DBManager getInstance(Context context) {
        if (mDBManager == null) {
            synchronized (DBManager.class) {
                if (mDBManager == null) {
                    mDBManager = new DBManager(context);
                }
            }
        }

        return mDBManager;
    }


    /**
     * 插入
     */
    public boolean insert(NaviPOI naviPOI) {
        if (naviPOI == null) {
            return false;
        }
        DBHelper helper = new DBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            // 删除原有相同项
            StringBuffer sb = new StringBuffer();
            sb.append("delete from ")
                    .append(DBHelper.NAVIGATE_POINT_TABLE_NAME)
                    .append(" where ").append(DBHelper.NAVIGATE_POINT_LON)
                    .append(" = '").append(naviPOI.getLon())
                    .append("' and ").append(DBHelper.NAVIGATE_POINT_LAT)
                    .append(" = '").append(naviPOI.getLat())
                    .append("' and ").append(DBHelper.NAVIGATE_POINT_NAME)
                    .append(" = '").append(naviPOI.getCn())
                    .append("'");
            db.execSQL(sb.toString());
            // 添加新项
            ContentValues values = new ContentValues();
            values.put(DBHelper.NAVIGATE_POINT_NAME, naviPOI.getCn());
            values.put(DBHelper.NAVIGATE_POINT_LON, naviPOI.getLon());
            values.put(DBHelper.NAVIGATE_POINT_LAT, naviPOI.getLat());
            values.put(DBHelper.NAVIGATE_POINT_PROVINCE, naviPOI.getProvince());
            values.put(DBHelper.NAVIGATE_POINT_CITY, naviPOI.getCity());
            values.put(DBHelper.NAVIGATE_POINT_REGION, naviPOI.getRegion());
            values.put(DBHelper.NAVIGATE_POINT_ADDRESS, naviPOI.getAddress());
            values.put(DBHelper.NAVIGATE_POINT_TYPE, naviPOI.getType());
            return db.insert(DBHelper.NAVIGATE_POINT_TABLE_NAME, null, values) != -1;
        } catch (Exception e) {
            Log.e(TAG, "error:" + e);
            return false;
        } finally {
             db.close();
        }
    }

    /**
     * 删除
     */
    public void delete() {

    }

    /**
     * 修改
     */
    public void update() {

    }

    /**
     * 查询
     *
     * @return
     */
    public List<NaviPOI> query() {
        List<NaviPOI> result = new ArrayList<NaviPOI>();
        DBHelper helper = new DBHelper(mContext);
        if (helper != null) {
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor mCursor = null;
            try {
                StringBuffer sb = new StringBuffer();
                sb.append("select ").append(DBHelper.NAVIGATE_POINT_NAME)
                        .append(",").append(DBHelper.NAVIGATE_POINT_LON)
                        .append(",").append(DBHelper.NAVIGATE_POINT_LAT)
                        .append(",").append(DBHelper.NAVIGATE_POINT_ID)
                        .append(",").append(DBHelper.NAVIGATE_POINT_PROVINCE)
                        .append(",").append(DBHelper.NAVIGATE_POINT_CITY)
                        .append(",").append(DBHelper.NAVIGATE_POINT_REGION)
                        .append(",").append(DBHelper.NAVIGATE_POINT_ADDRESS)
                        .append(",").append(DBHelper.NAVIGATE_POINT_TYPE)
                        .append(" from ")
                        .append(DBHelper.NAVIGATE_POINT_TABLE_NAME)
                        .append(" order by ")
                        .append(DBHelper.NAVIGATE_POINT_ID).append(" desc");
                mCursor = db.rawQuery(sb.toString(), new String[]{});
                while (mCursor.moveToNext()) {
                    CustomPoint point = new CustomPoint(Double.valueOf(mCursor
                            .getString(2)),
                            Double.valueOf(mCursor.getString(1)));
                    point.setAltitude(Integer.valueOf(mCursor.getString(3)));
                    String string1 = mCursor.getString(1);
                    String string2 = mCursor.getString(2);
                    NaviPOI naviPOI = new NaviPOI(mCursor.getString(0),
                            Double.valueOf(string2),
                            Double.valueOf(string1));
                    naviPOI.setId(mCursor.getInt(3) + "");
                    naviPOI.setLon_i((int)(Double.valueOf(string1) * 1E5));
                    naviPOI.setLat_i((int)(Double.valueOf(string2) * 1E5));
                    naviPOI.setProvince(mCursor.getString(4));
                    naviPOI.setCity(mCursor.getString(5));
                    naviPOI.setRegion(mCursor.getString(6));
                    naviPOI.setAddress(mCursor.getString(7));
                    naviPOI.setType(Integer.valueOf(mCursor.getString(8)));
                    result.add(naviPOI);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mCursor != null) {
                    mCursor.close();
                }
                db.close();
            }
        }
        return result;
    }

    /**
     * 清空
     */
    public void clear() {
        // TODO Auto-generated method stub
        DBHelper helper = new DBHelper(mContext);
        if (helper != null) {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                StringBuffer sb = new StringBuffer();
                sb.append("delete from ").append(
                        DBHelper.NAVIGATE_POINT_TABLE_NAME);
                db.execSQL(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }

        }
    }


    public void deleteSearchHistoryItem(long id) {
        String idstr = "_id";
        DBHelper helper = new DBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        final String sql = " delete from navigatepoint where " + idstr + " like ?; ";
        db.execSQL(sql, new String[]{String.valueOf(id)});
    }

}
