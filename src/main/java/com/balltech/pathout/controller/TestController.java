package com.balltech.pathout.controller;

import com.balltech.pathout.User.User;
import com.balltech.pathout.database.model.ContentValues;
import com.balltech.pathout.database.utils.DbUtil;
import com.google.protobuf.StringValue;
import com.mysql.cj.log.Log;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

@RestController
public class TestController {
    private static final Logger LOG = Logger.getLogger(TestController.class);

    @GetMapping("/test")
    public String test() {
        return "Fine";
    }

    @GetMapping("/addUser")
    public boolean addUser() {
        User user = new User("jack", "18217583389", "liuwen", "341222199312072097");
        if (!DbUtil.isUserValid(user)) {
            LOG.error("User exist alread, pls check again!");
            return false;
        }

        return DbUtil.addUser(user);
    }
}
