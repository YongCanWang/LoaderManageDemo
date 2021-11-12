package com.mapscloud.dtt.loadermanagerdemo.bean;


import com.mapscloud.dtt.loadermanagerdemo.db.NaviPOI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoryRouteBean implements Serializable {

    public long          id;
    public String        routeName;
    public long          time;
    public int           routeType; // 1、驾车，2、公交，3、步行。
    public NaviPOI       start;
    public NaviPOI       end;
    public List<NaviPOI> way_points = new ArrayList<NaviPOI>();

}
