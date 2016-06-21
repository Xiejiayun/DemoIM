package com.netease.corp.hzxiejiayun.server.dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class DaoUtil {

    private static Connection con = null;


    public DaoUtil() {
    }

    public static Connection getConnection() {
        if (con == null)
            connectDB();
        return con;
    }

    public static void main(String[] args) {
        System.out.println("start connect to the database");
        new DaoUtil().connectDB();
        System.out.println("finish connect to the database");
    }

    /**
     * 连接数据库
     */
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
     *
     * @param sql SQL语句
     * @return 结果集合
     */
    public static List doSql(String sql) {
        List list = new LinkedList();
        ResultSet resultSet = null;
        Statement stmt = null;
        int columns = 1;
        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(sql);
            int column = resultSet.getMetaData().getColumnCount();
            int index = 1;
            while (resultSet.next()) {
                for (int i=1; i<=column; i++) {
                    list.add(resultSet.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 根据SQL语句执行相应的操作
     *
     * @param sql SQL语句
     * @return 结果集合
     */
    public static boolean executeSQL(String sql) {
        boolean result = false;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            result = stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}