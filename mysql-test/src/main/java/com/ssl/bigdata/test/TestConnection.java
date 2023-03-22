package com.ssl.bigdata.test;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class TestConnection {
    @Test
    public void testConnection () throws IOException, ClassNotFoundException, SQLException {

        //1.获取配置文件
        InputStream resourceAsStream = TestConnection.class.getClassLoader().getResourceAsStream("jdbc.properties");
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

        //4.打印获取信息
        System.out.println(connection);

    }
}
