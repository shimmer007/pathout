package com.balltech.pathout.controller;

import com.balltech.pathout.User.User;
import com.balltech.pathout.database.utils.DbUtil;
import com.balltech.pathout.highlight.HighLight;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    private static final Logger LOG = Logger.getLogger(TestController.class);

    @GetMapping("/test")
    public String test() {
        return "Fine";
    }

    @GetMapping("/getUser")
    public User getUser() {
        return new User("jack", "18217583389", "liuwen", "341222199312072097");
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

    @GetMapping("/highList")
    public List<HighLight> getHighLight() {
        return new ArrayList<>();
    }

    @GetMapping("/data")
    public String getUserDataSheet(@RequestParam("name") String user_id) {
        HikariDataSource dataSource = new HikariDataSource(DbUtil.getConfig());
        try (Connection connection = dataSource.getConnection()) {
            String dataSheetResult = "";
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM playerdatasheet WHERE user_id ='" + user_id + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dataSheetResult = dataSheetResult + resultSet.getString(1) +" "+ resultSet.getString(2)
                    +" "+ resultSet.getString(3) + " "+resultSet.getString(4) +" "+ resultSet.getString(5);
                dataSheetResult = dataSheetResult + ";   ";
            }
            return dataSheetResult;
        } catch (SQLException e) {
            System.out.println("xsaxsa");
        }
        return "";
    }
}
