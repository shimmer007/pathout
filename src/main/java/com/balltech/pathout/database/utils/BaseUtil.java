package com.balltech.pathout.database.utils;

import com.balltech.pathout.database.model.Point;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BaseUtil {
    private static final Logger LOG = Logger.getLogger(BaseUtil.class);

    /**
     * 把数据库存储的数据形式转换成List<Point>
     *
     * @param dbShootPoint
     * @return
     */
    public static List<Point> convertToListPoint(String dbShootPoint) {
        if (dbShootPoint == null || dbShootPoint.isEmpty()) {
            LOG.warn("No shoot point found");
            return new ArrayList<>();
        }

        String[] pointArray = dbShootPoint.split(";");
        if (pointArray == null) {
            LOG.warn("Invalid point array from shoot point str");
            return new ArrayList<>();
        }
        List<Point> result = new ArrayList<>(pointArray.length);
        for (String tmp : pointArray) {
            String[] point = tmp.split(",");
            if (point == null || point.length != 2) {
                LOG.error("Error point data stored:" + (point == null ? 0 : point.length));
                continue;
            }
            result.add(new Point(Float.valueOf(point[0]), Float.valueOf(point[1])));
        }
        return result;
    }

    /**
     * 把pointList转换成数据库可以存储的数据形式
     *
     * @param pointList
     * @return
     */
    public static String converToDbShootPoint(List<Point> pointList) {
        if (pointList == null || pointList.isEmpty()) {
            LOG.warn("Invalid input pointList.");
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Point point : pointList) {
            stringBuilder.append(point.getX()).append(",").append(point.getY()).append(";");
        }
        return stringBuilder.toString();
    }
}
