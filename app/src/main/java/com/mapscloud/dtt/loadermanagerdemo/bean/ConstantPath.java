package com.mapscloud.dtt.loadermanagerdemo.bean;

import android.os.Environment;

public class ConstantPath {

    public static final String INTER_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    // 该项目相关文件存储路径
    public static final String SHEET_APP_PATH = INTER_ROOT_PATH + "/mapplus/TF/";

    // 元数据筛选配置文件名及文件路径
    public static final String METADATA_FILE_NAME = "jietubiao.json";
    public static final String METADATA_FILE_PATH = SHEET_APP_PATH + METADATA_FILE_NAME;

    // 图幅和行政区域数据库文件名及文件路径
    public static final String SEARCH_DB_FILE_NAME = "jietubiao.sqlite";
    public static final String SEARCH_DB_FILE_PATH = SHEET_APP_PATH + SEARCH_DB_FILE_NAME;

    // 导出目录
    public static final String SHEET_EXPORT_PATH = SHEET_APP_PATH + "export/";

}
