package com.ssl.bigdata.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import java.util.Properties;

public class JDBCUtils {


    //1.获取连接
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        //1.获取配置文件
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        //2.加载驱动
        Class.forName(driverClass);

        //3.获取连接
        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }


    //2.关闭资源
    public static void closeResource(Connection connection, Statement statement) {
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
