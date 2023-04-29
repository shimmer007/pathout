package com.balltech.pathout.database.model;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ContentValues {
    private static final Logger LOG = Logger.getLogger(ContentValues.class);
    private static final int DEFAULT_SIZE = 20;
    private Map<String, String> mValues;
    private String mTableName;

    public ContentValues(String tableName) {
        if (tableName == null || tableName.isEmpty()) {
            LOG.error("Invalid tableName not supported");
            return;
        }
        mValues = new HashMap<>(DEFAULT_SIZE);
        mTableName = tableName;
    }

    public void put(final String column, final String value) {
        mValues.put(column, value);
    }

    public String getInsertSql() {
        String sqlStr = "INSERT INTO " + mTableName + "(";
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

    public String getQueryStr(String andOr) {
        String sqlStr = "SELECT * FROM " + mTableName + " WHERE ";
        for (Map.Entry<String, String> entry : mValues.entrySet()) {
            sqlStr += entry.getKey() + " = " + entry.getValue() + " " + andOr + " ";
        }
        sqlStr += "1 = 1";
        return sqlStr;
    }

    public String getQueryOrStr() {
        return getQueryStr("OR");
    }

    public String getQueryAndStr() {
        return getQueryStr("AND");
    }
}
