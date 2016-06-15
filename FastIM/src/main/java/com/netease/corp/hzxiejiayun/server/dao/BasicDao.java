package com.netease.corp.hzxiejiayun.server.dao;

import java.sql.*;

/**
 * Created by lab on 16-1-12.
 */
public class BasicDao {

    private static Connection con = null;

    public static Connection getConnection() {
        if (con == null)
            connectDB();
        return con;
    }
    public static void main(String[] args) {
        System.out.println("start connect to the database");
        new BasicDao().connectDB();
        System.out.println("finish connect to the database");
    }

    private static void connectDB() {
        String driverName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverName);
            String serverName = "127.0.0.1";
            String database = "imdb";
            String url = "jdbc:mysql://" + serverName + "/" + database;
            String username = "root";
            String password = "";
            con = DriverManager.getConnection(url, username, password);
            System.out.println("success connect to the database");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error occurs");
            e.printStackTrace();
        }

    }

    /**
     * 根据SQL语句执行相应的操作
     * @param sql SQL语句
     * @return 结果集合
     */
    public ResultSet doSql(String sql) {
        ResultSet resultSet = null;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return  resultSet;
    }
}
