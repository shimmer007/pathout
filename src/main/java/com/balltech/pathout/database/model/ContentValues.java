package com.balltech.pathout.database.model;

import java.util.HashMap;
import java.util.Map;

public class ContentValues {
    private static final int DEFAULT_SIZE = 20;

    Map<String, String> mValues;

    public ContentValues() {
        mValues = new HashMap<>(DEFAULT_SIZE);
    }

    public void put(final String column, final String value) {
        mValues.put(column, value);
    }

    public String getInsertSql() {
        String sqlStr = "INSERT INTO user (";
        int index = 0;
        String columnVals = "";
        String valuesStr = "";
        for (Map.Entry<String, String> entry : mValues.entrySet()) {
            if (index > 0) {
                columnVals += ",";
                valuesStr += ",";
            }
            columnVals += entry.getKey();
            valuesStr += "'" + entry.getValue() + "'";
            index++;
        }
        sqlStr += columnVals + ") VALUES (" + valuesStr + ")";
        return sqlStr;
    }
}
