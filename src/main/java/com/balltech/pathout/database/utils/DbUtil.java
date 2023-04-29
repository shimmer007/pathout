package com.balltech.pathout.database;

import com.balltech.pathout.database.model.ContentValues;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbUtil {
    public static final HikariConfig CONFIG = new HikariConfig();

    private static final Logger LOG = Logger.getLogger(DbUtil.class);

    {
        CONFIG.setJdbcUrl("jdbc:mysql://localhost:3306/balltech");
        CONFIG.setUsername("root");
        CONFIG.setPassword("perfetto");
        CONFIG.addDataSourceProperty("cachePrepStmts", "true");
        CONFIG.addDataSourceProperty("prepStmtCacheSize", "250");
        CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }

    public static void insert(List<ContentValues> valueList) {
        if (valueList == null || valueList.isEmpty()) {
            LOG.debug("insert empty value list. Pls check again!");
            return;
        }
        HikariDataSource ds = new HikariDataSource(CONFIG);
        try (Connection connection = ds.getConnection()) {
            for (ContentValues values : valueList) {
                String sqlStr = values.getInsertSql();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
                boolean result = preparedStatement.execute();
                LOG.info("Insert result:" + result);
            }
        } catch (SQLException e) {
            LOG.error(e.toString());
        }
    }

    public static void insert(ContentValues values) {
        insert(Arrays.asList(values));
    }

}
