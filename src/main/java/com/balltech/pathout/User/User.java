package com.balltech.pathout.User;

import lombok.Getter;
import lombok.Setter;

public class User {
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
    }
}
