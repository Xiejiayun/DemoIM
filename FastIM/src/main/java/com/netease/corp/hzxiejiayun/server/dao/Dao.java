package com.netease.corp.hzxiejiayun.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lab on 16-1-12.
 */
public class Dao {


    public static void main(String[] args) {
        System.out.println("start connect to the database");
        new Dao().connectDB();
        System.out.println("finish connect to the database");
    }

    public void connectDB() {
        String driverName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverName);
            String serverName = "127.0.0.1";
            String database = "imdb";
            String url = "jdbc:mysql://" + serverName + "/" + database;

            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("success connect to the database");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error occurs");
            e.printStackTrace();
        }

    }
}
