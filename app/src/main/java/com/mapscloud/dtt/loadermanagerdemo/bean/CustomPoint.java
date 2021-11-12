package com.mapscloud.dtt.loadermanagerdemo.bean;

import android.location.Location;
import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by Dev on 2017/12/27.
 */

public class CustomPoint implements Serializable {
    // ===========================================================
    // Constants
    // ===========================================================

    static final long serialVersionUID = 1L;

    // ===========================================================
    // Fields
    // ===========================================================

    private int mLongitudeE6;
    private int mLatitudeE6;
    private int mAltitude;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * 创建地理坐标点
     *
     * @param aLatitudeE6
     *            纬度*1E6
     * @param aLongitudeE6
     *            经度*1E6
     */
    public CustomPoint(final int aLatitudeE6, final int aLongitudeE6) {
        this.mLatitudeE6 = aLatitudeE6;
        this.mLongitudeE6 = aLongitudeE6;
    }

    /**
     * 创建地理坐标点
     *
     * @param aLatitudeE6
     *            纬度*1E6
     * @param aLongitudeE6
     *            经度*1E6
     * @param aAltitude
     *            高程
     */
    public CustomPoint(final int aLatitudeE6, final int aLongitudeE6,
                       final int aAltitude) {
        this.mLatitudeE6 = aLatitudeE6;
        this.mLongitudeE6 = aLongitudeE6;
        this.mAltitude = aAltitude;
    }

    /**
     * 创建地理坐标点
     *
     * @param aLatitude
     *            纬度
     * @param aLongitude
     *            经度
     */
    public CustomPoint(final double aLatitude, final double aLongitude) {
        this.mLatitudeE6 = (int) (aLatitude * 1E6);
        this.mLongitudeE6 = (int) (aLongitude * 1E6);
    }

    /**
     * 创建地理坐标点
     *
     * @param aLatitude
     *            纬度
     * @param aLongitude
     *            经度
     * @param aAltitude
     *            高程
     */
    public CustomPoint(final double aLatitude, final double aLongitude,
                       final double aAltitude) {
        this.mLatitudeE6 = (int) (aLatitude * 1E6);
        this.mLongitudeE6 = (int) (aLongitude * 1E6);
        this.mAltitude = (int) aAltitude;
    }

    /**
     * 创建地理坐标点
     *
     * @param aLocation
     *            位置信息
     */
    public CustomPoint(final Location aLocation) {
        this(aLocation.getLatitude(), aLocation.getLongitude(), aLocation
                .getAltitude());
    }

    /**
     * 创建地理坐标点
     *
     * @param aCustomPoint
     *            地理坐标点
     */
    public CustomPoint(CustomPoint aCustomPoint) {
        this.mLatitudeE6 = aCustomPoint.mLatitudeE6;
        this.mLongitudeE6 = aCustomPoint.mLongitudeE6;
        this.mAltitude = aCustomPoint.mAltitude;
    }

    /**
     * 从double字符串解析地理坐标点
     *
     * @param s
     *            字符串
     * @param spacer
     *            分割符
     * @return 地理坐标点
     */
    public static CustomPoint fromDoubleString(final String s, final char spacer) {
        final int spacerPos1 = s.indexOf(spacer);
        final int spacerPos2 = s.indexOf(spacer, spacerPos1 + 1);

        if (spacerPos2 == -1) {
            return new CustomPoint((int) (Double.parseDouble(s.substring(0,
                    spacerPos1)) * 1E6), (int) (Double.parseDouble(s.substring(
                    spacerPos1 + 1, s.length())) * 1E6));
        } else {
            return new CustomPoint((int) (Double.parseDouble(s.substring(0,
                    spacerPos1)) * 1E6), (int) (Double.parseDouble(s.substring(
                    spacerPos1 + 1, spacerPos2)) * 1E6),
                    (int) Double.parseDouble(s.substring(spacerPos2 + 1,
                            s.length())));
        }
    }

    public static CustomPoint fromInvertedDoubleString(final String s,
                                                                               final char spacer) {
        final int spacerPos1 = s.indexOf(spacer);
        final int spacerPos2 = s.indexOf(spacer, spacerPos1 + 1);

        if (spacerPos2 == -1) {
            return new CustomPoint(
                    (int) (Double.parseDouble(s.substring(spacerPos1 + 1,
                            s.length())) * 1E6),
                    (int) (Double.parseDouble(s.substring(0, spacerPos1)) * 1E6));
        } else {
            return new CustomPoint(
                    (int) (Double.parseDouble(s.substring(spacerPos1 + 1,
                            spacerPos2)) * 1E6),
                    (int) (Double.parseDouble(s.substring(0, spacerPos1)) * 1E6),
                    (int) Double.parseDouble(s.substring(spacerPos2 + 1,
                            s.length())));

        }
    }

    /**
     * 从int字符串创建地理坐标点
     *
     * @param s
     *            字符串
     * @return 地理坐标点
     */
    public static CustomPoint fromIntString(final String s) {
        final int commaPos1 = s.indexOf(',');
        final int commaPos2 = s.indexOf(',', commaPos1 + 1);

        if (commaPos2 == -1) {
            return new CustomPoint(Integer.parseInt(s.substring(0, commaPos1)),
                    Integer.parseInt(s.substring(commaPos1 + 1, s.length())));
        } else {
            return new CustomPoint(Integer.parseInt(s.substring(0, commaPos1)),
                    Integer.parseInt(s.substring(commaPos1 + 1, commaPos2)),
                    Integer.parseInt(s.substring(commaPos2 + 1, s.length())));
        }
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // wuzhenlin add at 20130706 begin

    public double getLatitude() {
        return (double) mLatitudeE6 / 1E6;
    }

    public double getLongitude() {
        return (double) mLongitudeE6 / 1E6;
    }


    public int getLongitudeE6() {
        return this.mLongitudeE6;
    }

    public int getLatitudeE6() {
        return this.mLatitudeE6;
    }

    /**
     * 获取高程
     *
     * @return 高程
     */
    public int getAltitude() {
        return this.mAltitude;
    }

    /**
     * 设置经度
     *
     * @param aLongitudeE6
     *            经度*1E6
     */
    public void setLongitudeE6(final int aLongitudeE6) {
        this.mLongitudeE6 = aLongitudeE6;
    }

    /**
     * 设置纬度
     *
     * @param aLatitudeE6
     *            纬度*1E6
     */
    public void setLatitudeE6(final int aLatitudeE6) {
        this.mLatitudeE6 = aLatitudeE6;
    }

    /**
     * 设置经度
     *
     * @param aLongitude
     *            经度
     */
    public void setLongitude(final double aLongitude) {
        this.mLongitudeE6 = (int) (aLongitude * 1E6);
    }

    /**
     * 设置纬度
     *
     * @param aLatitude
     *            纬度
     */
    public void setLatitude(final double aLatitude) {
        this.mLatitudeE6 = (int) (aLatitude * 1E6);
    }

    /**
     * 设置高程
     *
     * @param aAltitude
     *            高程
     */
    public void setAltitude(final int aAltitude) {
        this.mAltitude = aAltitude;
    }

    /**
     * 设置坐标
     *
     * @param aLatitudeE6
     *            纬度*1E6
     * @param aLongitudeE6
     *            经度*1E6
     */
    public void setCoordsE6(final int aLatitudeE6, final int aLongitudeE6) {
        this.mLatitudeE6 = aLatitudeE6;
        this.mLongitudeE6 = aLongitudeE6;
    }

    // ===========================================================
    // Methods from SuperClass/Interfaces
    // ===========================================================

    @Override
    public Object clone() {
        return new CustomPoint(this.mLatitudeE6, this.mLongitudeE6);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(this.mLatitudeE6).append(",")
                .append(this.mLongitudeE6).append(",").append(this.mAltitude)
                .toString();
    }

    public String toCoordinateString() {
        return "(" + getLongitude() + ", " + getLatitude() + ")";
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final CustomPoint rhs = (CustomPoint) obj;
        return rhs.mLatitudeE6 == this.mLatitudeE6
                && rhs.mLongitudeE6 == this.mLongitudeE6
                && rhs.mAltitude == this.mAltitude;
    }

    @Override
    public int hashCode() {
        return 37 * (17 * mLatitudeE6 + mLongitudeE6) + mAltitude;
    }


    private CustomPoint(final Parcel in) {
        this.mLatitudeE6 = in.readInt();
        this.mLongitudeE6 = in.readInt();
        this.mAltitude = in.readInt();
    }




}
