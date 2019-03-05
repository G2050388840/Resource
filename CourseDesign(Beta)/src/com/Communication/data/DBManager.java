package com.Communication.data;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager
{
    private static String driver;
    private static String url;
    static
    {
        getInstance();
    }

    private static void getInstance()
    {
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://10.6.49.207/user";
        try
        {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized static Connection getConnection()
    {
        Connection conn = null;
        try
        {
            Properties prop = new Properties();
            prop.put("charset","gbk");
            conn = DriverManager.getConnection(url,"root","123456");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return conn;
    }
}
