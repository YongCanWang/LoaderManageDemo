package com.mapscloud.dtt.loadermanagerdemo.bean;

import android.os.Environment;

/**
 * @author TomCan
 * @description:
 * @date :2021/10/20 10:53
 */
public class Constant {


    // 导航点收藏表
    public static final String NAVIGATE_POINT_TABLE_NAME = "navigatepoint"; // 表名
    public static final String NAVIGATE_POINT_ID         = "_id"; // 字段
    public static final String NAVIGATE_POINT_NAME       = "name";
    public static final String NAVIGATE_POINT_LON        = "lon";
    public static final String NAVIGATE_POINT_LAT        = "lat";
    public static final String NAVIGATE_POINT_ADDRESS    = "address";
    public static final String NAVIGATE_POINT_PROVINCE   = "province";
    public static final String NAVIGATE_POINT_CITY       = "city";
    public static final String NAVIGATE_POINT_REGION     = "region";
    public static final String NAVIGATE_POINT_TYPE       = "type";


    public static final String[] PROJECTION = new String[]{NAVIGATE_POINT_ID,
            NAVIGATE_POINT_NAME, NAVIGATE_POINT_LON, NAVIGATE_POINT_LAT,
            NAVIGATE_POINT_ADDRESS, NAVIGATE_POINT_PROVINCE, NAVIGATE_POINT_CITY, NAVIGATE_POINT_REGION, NAVIGATE_POINT_TYPE};


    public static final String INTER_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    // 该项目相关文件存储路径
    public static final String SHEET_APP_PATH = INTER_ROOT_PATH + "/mapplus/TF/";
    // 图幅和行政区域数据库文件名及文件路径
    public static final String SEARCH_DB_FILE_NAME = "jietubiao.sqlite";
    public static final String SEARCH_DB_FILE_PATH = SHEET_APP_PATH + SEARCH_DB_FILE_NAME;

}
