package com.mapscloud.dtt.loadermanagerdemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.mapscloud.dtt.loadermanagerdemo.bean.HistoryRouteBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航历史记录API
 *
 * @author wuzhenlin
 */
public class NavigationHistoryProvider {
    private              String TAG              = "NavigationHistoryProvider";
    private static final String DATABASE_NAME    = "history.db";
    private static final int    DATABASE_VERSION = 1;

    private static NavigationHistoryProvider provider;
    private        NavigationHistoryDatabase                           databaseHelp;

    public static NavigationHistoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }
        throw new RuntimeException("no init instance exception");
    }

    public static synchronized NavigationHistoryProvider init(Context context) {
        if (provider == null) {
            if (context == null) {
                throw new NullPointerException("context is null");
            }
            provider = new NavigationHistoryProvider(context);
        }
        return provider;
    }

    public static synchronized void distory() {
        if (getInstance() != null) {
            getInstance().databaseHelp.close();
            getInstance().databaseHelp = null;
            provider = null;
        }
    }

    private NavigationHistoryProvider(Context context) {
        databaseHelp = new NavigationHistoryDatabase(context);
    }


    /**
     * 清除所有的历史记录。
     */
    public void clearHistory() {
        SQLiteDatabase db = databaseHelp.getWritableDatabase();
        final String sql = "delete from historys";
        db.execSQL(sql);
    }


    public void deleteHistoryByID(long id) {
        String idstr = "_id";
        SQLiteDatabase db = databaseHelp.getWritableDatabase();
        final String sql = " delete from historys where " + idstr + " like ?; ";
        db.execSQL(sql, new String[]{String.valueOf(id)});
    }


    /**
     * 保存一条路径查询记录。
     *
     * @param start 开始点POI
     * @param end   结束点POI
     * @param mid   途径点POI列表
     * @param type  路径规划方式： 1、驾车，2、公交，3、步行。
     */
    public void saveHistory(NaviPOI start, NaviPOI end,
                            List<NaviPOI> mid, int type) {
        SQLiteDatabase db = databaseHelp.getWritableDatabase();
        db.beginTransaction();
        try {

            String routeName = joinRouteName(start, end, mid);
            // 删除原有相同项
            StringBuffer sb = new StringBuffer();
            sb.append("delete from ")
                    .append(NavigationHistoryDatabase.TABLE_HISTORY)
                    .append(" where ").append(TargetHistoryColumns.HISTORY_TITLE)
                    .append(" = '").append(routeName)
                    .append("'");
            db.execSQL(sb.toString());

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO ")
                    .append(NavigationHistoryDatabase.TABLE_HISTORY)
                    .append("(").append(TargetHistoryColumns.HISTORY_TITLE)
                    .append(",").append(TargetHistoryColumns.HISTORY_TIME)
                    .append(",").append(TargetHistoryColumns.HISTORY_TYPE)
                    .append(")").append(" VALUES(?,?,?)");

            SQLiteStatement statement = db.compileStatement(insertSql
                    .toString());

            statement.bindString(1, routeName);
            statement.bindLong(2, System.currentTimeMillis());
            statement.bindLong(3, type);
            statement.executeInsert();
            final String queryRowIDSql = "select last_insert_rowid() from "
                    + NavigationHistoryDatabase.TABLE_HISTORY;
            SQLiteStatement queryRowIdStatement = db
                    .compileStatement(queryRowIDSql);
            long strid = queryRowIdStatement.simpleQueryForLong();
            insertSql = new StringBuilder();
            insertSql.append("INSERT INTO ")
                    .append(NavigationHistoryDatabase.TABLE_TARGET_POINT)
                    .append("(").append(TargetPointColumns.TARGET_TITLE)
                    .append(",").append(TargetPointColumns.TARGET_X)
                    .append(",").append(TargetPointColumns.TARGET_Y)
                    .append(",").append(TargetPointColumns.TARGET_INDEX)
                    .append(",").append(TargetPointColumns.HISTORY_ID)
                    .append(")").append(" VALUES(?,?,?,?,?)");

            SQLiteStatement insertTargetStatement = db
                    .compileStatement(insertSql.toString());
            insertTargetPoint(insertTargetStatement, start, strid, 0);
            if (mid != null && !mid.isEmpty()) {
                for (int index = 1; index <= mid.size(); index++) {
                    insertTargetPoint(insertTargetStatement,
                            mid.get(index - 1), strid, index);
                }
            }
            insertTargetPoint(insertTargetStatement, end, strid, 10);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static String joinRouteName(NaviPOI start, NaviPOI end,
                                       List<NaviPOI> mid) {
        StringBuilder sb = new StringBuilder();
        if (start != null) {
            sb.append(start.getCn()).append(" --> ");
        }
        if (mid != null && !mid.isEmpty()) {
            for (int i = 0; i < mid.size(); i++) {
                sb.append(mid.get(i).getCn());
                sb.append(" --> ");
            }
        }
        if (end != null) {
            sb.append(end.getCn());
        }
        return sb.toString();
    }

    /**
     * 获取所有的历史记录。
     *
     * @return 历史记录列表
     */
    public List<HistoryRouteBean> queryHistorys() {
        List<HistoryRouteBean> historys = null;
        SQLiteDatabase db = databaseHelp.getReadableDatabase();
        // select
        // a._id,a.history_title,a.type,b._id,b.target_title,b.target_x,b.target_y,b.item_index,b.history_id
        // from historys a join target_points b on a._id = b.history_id;
        final String sql = "select * from historys order by time desc";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor != null) {
                historys = new ArrayList<HistoryRouteBean>();
                HistoryRouteBean item = null;
                while (cursor.moveToNext()) {
                    item = new HistoryRouteBean();
                    String historyTitle = cursor
                            .getString(cursor
                                    .getColumnIndex(TargetHistoryColumns.HISTORY_TITLE));
                    long time = cursor.getLong(cursor
                            .getColumnIndex(TargetHistoryColumns.HISTORY_TIME));
                    int type = cursor.getInt(cursor
                            .getColumnIndex(TargetHistoryColumns.HISTORY_TYPE));
                    long id = cursor.getLong(cursor
                            .getColumnIndex(TargetHistoryColumns._ID));
                    item.id = id;
                    item.routeName = historyTitle;
                    item.time = time;
                    item.routeType = type;
                    setItemPoint(db, id, item);
                    historys.add(item);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return historys;
    }

    private void setItemPoint(SQLiteDatabase db, long id, HistoryRouteBean item) {
        final String sql = "select * from target_points where history_id = ?";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor
                            .getColumnIndex(TargetPointColumns.TARGET_TITLE));
                    int index = cursor.getInt(cursor
                            .getColumnIndex(TargetPointColumns.TARGET_INDEX));
                    int x = cursor.getInt(cursor
                            .getColumnIndex(TargetPointColumns.TARGET_X));
                    int y = cursor.getInt(cursor
                            .getColumnIndex(TargetPointColumns.TARGET_Y));
                    NaviPOI point = new NaviPOI(x / 1E5, y / 1E5);
                    point.setCn(name);
                    point.setLat_i(x);
                    point.setLon_i(y);

                    if (index == 0) {
                        item.start = point;
                    } else if (index == 10) {
                        item.end = point;
                    } else if (index > 0 && index < 10) {
                        item.way_points.add(index - 1, point);
                    } else {
                        throw new RuntimeException("point index error !");
                    }

                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void insertTargetPoint(SQLiteStatement statement,
                                   NaviPOI point, long strid, int index) {

        statement.bindString(1, point.getCn());
        statement.bindLong(2, point.getLat_i());
        statement.bindLong(3, point.getLon_i());
        statement.bindLong(4, index);
        statement.bindLong(5, strid);
        statement.executeInsert();
    }

    private class NavigationHistoryDatabase extends SQLiteOpenHelper {

        private static final String TABLE_TARGET_POINT = "target_points";
        private static final String TABLE_HISTORY      = "historys";

        public NavigationHistoryDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            StringBuilder sb1 = new StringBuilder();
            sb1.append("CREATE TABLE ").append(TABLE_HISTORY).append(" (")
                    .append(TargetHistoryColumns._ID)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                    .append(TargetHistoryColumns.HISTORY_TITLE)
                    .append(" TEXT,").append(TargetHistoryColumns.HISTORY_TYPE)
                    .append(" INTEGER DEFAULT 1,")
                    .append(TargetHistoryColumns.HISTORY_TIME)
                    .append(" INTEGER);");
            db.execSQL(sb1.toString());

            StringBuilder sb2 = new StringBuilder();
            sb2.append("CREATE TABLE ").append(TABLE_TARGET_POINT).append(" (")
                    .append(TargetHistoryColumns._ID)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                    .append(TargetPointColumns.TARGET_TITLE).append(" TEXT,")
                    .append(TargetPointColumns.TARGET_X)
                    .append(" INTEGER NOT NULL,")
                    .append(TargetPointColumns.TARGET_Y)
                    .append(" INTEGER NOT NULL,")
                    .append(TargetPointColumns.TARGET_INDEX)
                    .append(" INTEGER NOT NULL,")
                    .append(TargetPointColumns.HISTORY_ID)
                    .append(" INTEGER NOT NULL);");
            db.execSQL(sb2.toString());

            // 触发器，删除historys表中的记录之前触发删除target_points中history_id与该条记录id相同的记录。
            StringBuilder sb3 = new StringBuilder();
            sb3.append("CREATE TRIGGER delete_history ")
                    .append(" BEFORE DELETE ON ").append(TABLE_HISTORY)
                    .append(" BEGIN ").append("DELETE FROM ")
                    .append(TABLE_TARGET_POINT).append(" WHERE ")
                    .append(TargetPointColumns.HISTORY_ID).append(" = OLD.")
                    .append(TargetHistoryColumns._ID).append(";")
                    .append(" END;");
            db.execSQL(sb3.toString());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public static final class TargetPointColumns implements BaseColumns {
        public static final String TARGET_TITLE = "target_title"; // 点名称
        public static final String TARGET_X     = "target_x"; // 纬度
        public static final String TARGET_Y     = "target_y"; // 经度
        public static final String TARGET_INDEX = "item_index"; // 0、起点；1-9、途径点;10、终点
        public static final String HISTORY_ID   = "history_id"; // 历史表id
    }

    public static final class TargetHistoryColumns implements BaseColumns {
        public static final String HISTORY_TITLE = "history_title"; // 历史记录标题
        public static final String HISTORY_TYPE  = "type"; // 1、驾车，2、公交，3、步行。
        public static final String HISTORY_TIME  = "time"; // 时间
    }

}
