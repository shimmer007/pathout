package com.balltech.pathout.database.utils;

import com.balltech.pathout.User.User;
import com.balltech.pathout.database.model.ContentValues;
import com.balltech.pathout.highlight.HighLight;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DbUtil {
    public static final String TABLE_USER = "user";
    public static final String TABLE_HIGHLIGHT = "highlight";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USER_NICK_NAME = "user_nick_name";
    public static final String COL_USER_PHONE_NUM = "phone_number";
    public static final String COL_USER_NAME = "user_name";
    public static final String COL_USER_ID_CARD = "id_card_number";
    private static final HikariConfig CONFIG = new HikariConfig();

    private static final Logger LOG = Logger.getLogger(DbUtil.class);

    public static Set<User> getAllUser() {
        HikariDataSource ds = new HikariDataSource(getConfig());
        try (Connection connection = ds.getConnection()) {
            ContentValues values = new ContentValues(User.TABLE_NAME);
            PreparedStatement statement = connection.prepareStatement(values.getQueryAndStr());
            ResultSet resultSet = statement.executeQuery();
            Set<User> result = new HashSet<>();
            while (resultSet != null && resultSet.next()) {
                result.add(new User(resultSet));
            }
            return result;
        } catch (SQLException e) {
            LOG.error("Error when get highlight:" + e.toString());
        }
        return new HashSet<>();
    }

    public static HikariConfig getConfig() {
        if (CONFIG.getUsername() == null || CONFIG.getUsername().isEmpty()) {
            synchronized (DbUtil.class) {
                CONFIG.setJdbcUrl("jdbc:mysql://localhost:3306/balltech");
                CONFIG.setUsername("root");
                CONFIG.setPassword("perfetto");
                CONFIG.addDataSourceProperty("cachePrepStmts", "true");
                CONFIG.addDataSourceProperty("prepStmtCacheSize", "250");
                CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            }
        }
        return CONFIG;
    }

    public static List<HighLight> getHighLights(User users) {
        if (users == null) {
            LOG.error("Invalid user id.");
            return new ArrayList<>();
        }
        HikariDataSource ds = new HikariDataSource(getConfig());
        try (Connection connection = ds.getConnection()) {
            ContentValues values = new ContentValues(HighLight.TABLE_NAME);
            values.put(HighLight.USER_ID, users.getMUid());
            PreparedStatement statement = connection.prepareStatement(values.getQueryAndStr());
            ResultSet resultSet = statement.executeQuery();
            List<HighLight> result = new ArrayList<>(10);
            while (resultSet != null && resultSet.next()) {
                result.add(new HighLight(resultSet));
            }
            return result;
        } catch (SQLException e) {
            LOG.error("Error when get highlight:" + e.toString());
        }
        return new ArrayList<>();
    }

    /**
     * 插入一个数据列表到数据库
     *
     * @param valueList
     */
    public static void insert(List<ContentValues> valueList) {
        if (valueList == null || valueList.isEmpty()) {
            LOG.debug("insert empty value list. Pls check again!");
            return;
        }
        HikariDataSource ds = new HikariDataSource(getConfig());
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

    /**
     * 插入单条数据到
     *
     * @param values
     */
    public static void insert(ContentValues values) {
        insert(Arrays.asList(values));
    }

    /**
     * 检查用户在数据库里是否已经存在
     *
     * @param user
     * @return
     */
    public static boolean isUserValid(User user) {
        if (user == null) {
            LOG.error("Invalid user check isValid!");
            return false;
        }

        ContentValues contentValues = new ContentValues("user");
        contentValues.put(COL_USER_PHONE_NUM, user.getMPhoneNum());
        contentValues.put(COL_USER_ID_CARD, user.getMIdCard());
        HikariDataSource ds = new HikariDataSource(getConfig());
        try (Connection connection = ds.getConnection()) {
            String sqlStr = contentValues.getQueryOrStr();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            LOG.error(e.toString());
        }
        return false;
    }

    /**
     * 添加user到数据库里. 这里不会走有效性校验
     *
     * @param user
     * @return
     */
    public static boolean addUser(User user) {
        if (user == null) {
            LOG.error("Invalid user.");
            return false;
        }

        ContentValues values = new ContentValues(TABLE_USER);
        values.put(COL_USER_ID, user.getMUid());
        values.put(COL_USER_NICK_NAME, user.getMUserNickName());
        values.put(COL_USER_PHONE_NUM, user.getMPhoneNum());
        values.put(COL_USER_NAME, user.getMUserName());
        values.put(COL_USER_ID_CARD, user.getMIdCard());
        HikariDataSource ds = new HikariDataSource(getConfig());
        try (Connection con = ds.getConnection()) {
            PreparedStatement statement = con.prepareStatement(values.getInsertSql());
            boolean isSuccess = statement.execute();
            LOG.info("add user" + isSuccess);
        } catch (SQLException e) {
            LOG.error("Sql exception when add user:" + e.toString());
        }
        return false;
    }
}
