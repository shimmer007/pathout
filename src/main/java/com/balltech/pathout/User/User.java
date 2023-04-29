package com.balltech.pathout.User;

import com.balltech.pathout.database.utils.BaseUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class User {
    private static final Logger LOG = Logger.getLogger(User.class);
    public static final String TABLE_NAME = "user";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_NICK_NAME = "user_nick_name";
    public static final String COL_PHONE_NUMBER = "phone_number";
    public static final String COL_USER_NAME = "user_name";
    public static final String COL_ID_CARD = "id_card_number";
    @Getter
    @Setter
    private String mUid;
    @Getter
    @Setter
    private String mUserNickName;
    @Getter
    @Setter
    private String mPhoneNum;
    @Getter
    @Setter
    private String mUserName;
    @Getter
    @Setter
    private String mIdCard;

    public User(String a, String b, String c, String d) {
        mUserNickName = a;
        mPhoneNum = b;
        mUserName = c;
        mIdCard = d;
        mUid = BaseUtil.getHash(a + b + c + d);
    }

    public User(ResultSet resultSet) {
        try {
            mUid = resultSet.getString(COL_USER_ID);
            mUserNickName = resultSet.getString(COL_NICK_NAME);
            mPhoneNum = resultSet.getString(COL_PHONE_NUMBER);
            mUserName = resultSet.getString(COL_USER_NAME);
            mIdCard = resultSet.getString(COL_ID_CARD);
        } catch (SQLException e) {
            LOG.error("init user failed:" + e.toString());
        }
    }
}
