package com.balltech.pathout.datasheet;

import com.balltech.pathout.database.utils.BaseUtil;
import com.balltech.pathout.database.utils.DbUtil;
import com.balltech.pathout.database.model.Point;
import com.mysql.cj.log.Log;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

/**
 * 球员最全数据信息. 包含: 场均得分, 场均篮板, 场均助攻等
 */
public class PlayerDataSheet {
    private static final Logger LOG = Logger.getLogger(PlayerDataSheet.class);
    private String mUid;
    private float mPpg;
    private float mRpg;
    private float mApg;
    private List<Point> mShootPoint;

    public PlayerDataSheet(String id) {
        mUid = id;
        initData(id);
    }

    private void initData(String id) {
        HikariDataSource ds = new HikariDataSource(DbUtil.CONFIG);
        try (Connection connection = ds.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM playerdatasheet where user_id = "
                + mUid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet != null || resultSet.next()) {
                mApg = resultSet.getFloat(0);
                mRpg = resultSet.getFloat(1);
                mApg = resultSet.getFloat(2);
                mShootPoint = BaseUtil.convertToListPoint(resultSet.getString(3));
            }
        } catch (SQLException e) {
            LOG.error("init player data sheet data failed:" + e.toString());
        }
    }
}
