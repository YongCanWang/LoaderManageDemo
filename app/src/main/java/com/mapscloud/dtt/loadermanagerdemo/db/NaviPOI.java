package com.mapscloud.dtt.loadermanagerdemo.db;



import com.mapscloud.dtt.loadermanagerdemo.BR;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * @author TomCan
 * @description:
 * @date :2019/4/24 14:15
 */
public class NaviPOI extends BaseObservable implements Serializable {
    private static final long   serialVersionUID = 1L;
    private static final String TAG              = "NaviPOI";

    private String id;
    private String tag;
    private int    QRCode;
    private String cn = ""; // # 中文名称
    private double lat; // # 纬度
    private double lon; // # 经度

    private double alt;// 高度
    private long   tid; // # 主题编码
    private long   tc; // # 地名编码
    private long   pac; // # 区域编码

    private int    libtype; // # 来源地名库
    private int    continent_code; // # 洲编号
    private String name;
    private String address;
    private String province;
    private String city;
    private String region = ""; // # 区域详情（国家/省/市）
    private int    distance;
    private String town;
    private String street;
    private String floor;

    private double center_lon; // # 基础库项，区域地图中心点坐标
    private double center_lat;
    private int    zoom_level; // # 基础库项，区域显示地图缩放级别
    private int    zoom_level_min;
    private int    zoom_level_max;

    private int lat_i; // # 纬度
    private int lon_i; // # 经度

    private int type;

    private ArrayList<NaviPOI> childrenPOI;

    private boolean isPan;




    public NaviPOI() {
        this(0.0, 0.0);
    }


    public NaviPOI(double lat, double lon) {
        this("", lat, lon);
    }

    public NaviPOI(String cn, double lat, double lon) {
        this(cn, lat, lon, 0.0);
    }


    public NaviPOI(String cn, double lat, double lon, double alt) {
        this.cn = cn;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.lat_i = (int) (lat * 1E5);
        this.lon_i = (int) (lon * 1E5);
        notifyChange();
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static String getTAG() {
        return TAG;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getQRCode() {
        return QRCode;
    }

    public void setQRCode(int QRCode) {
        this.QRCode = QRCode;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public long getTc() {
        return tc;
    }

    public void setTc(long tc) {
        this.tc = tc;
    }

    public long getPac() {
        return pac;
    }

    public void setPac(long pac) {
        this.pac = pac;
    }

    public int getLibtype() {
        return libtype;
    }

    public void setLibtype(int libtype) {
        this.libtype = libtype;
    }

    public int getContinent_code() {
        return continent_code;
    }

    public void setContinent_code(int continent_code) {
        this.continent_code = continent_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public double getCenter_lon() {
        return center_lon;
    }

    public void setCenter_lon(double center_lon) {
        this.center_lon = center_lon;
    }

    public double getCenter_lat() {
        return center_lat;
    }

    public void setCenter_lat(double center_lat) {
        this.center_lat = center_lat;
    }

    public int getZoom_level() {
        return zoom_level;
    }

    public void setZoom_level(int zoom_level) {
        this.zoom_level = zoom_level;
    }

    public int getZoom_level_min() {
        return zoom_level_min;
    }

    public void setZoom_level_min(int zoom_level_min) {
        this.zoom_level_min = zoom_level_min;
    }

    public int getZoom_level_max() {
        return zoom_level_max;
    }

    public void setZoom_level_max(int zoom_level_max) {
        this.zoom_level_max = zoom_level_max;
    }

    public int getLat_i() {
        return lat_i;
    }

    public void setLat_i(int lat_i) {
        this.lat_i = lat_i;
    }

    public int getLon_i() {
        return lon_i;
    }

    public void setLon_i(int lon_i) {
        this.lon_i = lon_i;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<NaviPOI> getChildrenPOI() {
        return childrenPOI;
    }

    public void setChildrenPOI(ArrayList<NaviPOI> childrenPOI) {
        this.childrenPOI = childrenPOI;
    }

    public boolean isPan() {
        return isPan;
    }

    public void setPan(boolean pan) {
        isPan = pan;
    }


    @Override
    public String toString() {
        return "NaviPOI{" +
                "id='" + id + '\'' +
                ", tag='" + tag + '\'' +
                ", QRCode=" + QRCode +
                ", cn='" + cn + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", alt=" + alt +
                ", tid=" + tid +
                ", tc=" + tc +
                ", pac=" + pac +
                ", libtype=" + libtype +
                ", continent_code=" + continent_code +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", distance=" + distance +
                ", town='" + town + '\'' +
                ", street='" + street + '\'' +
                ", floor='" + floor + '\'' +
                ", center_lon=" + center_lon +
                ", center_lat=" + center_lat +
                ", zoom_level=" + zoom_level +
                ", zoom_level_min=" + zoom_level_min +
                ", zoom_level_max=" + zoom_level_max +
                ", lat_i=" + lat_i +
                ", lon_i=" + lon_i +
                ", type=" + type +
                ", childrenPOI=" + childrenPOI +
                ", isPan=" + isPan +
                '}';
    }
}
