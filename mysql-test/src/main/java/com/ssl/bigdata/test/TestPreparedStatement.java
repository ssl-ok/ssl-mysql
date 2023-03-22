package com.ssl.bigdata.test;

import com.ssl.bigdata.util.JDBCUtils;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


public class TestPreparedStatement {

    @Test
    public void testCommonUpdate() {
        String sql = "delete from word1 where id = ?";

        update(sql, 2);
    }

    //通用的增删改操作
    public void update(String sql, Object... args) {//sql中占位符的个数与可变参数的长度相同！
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取连接
            connection = JDBCUtils.getConnection();

            //2.预编译sql语句， 返回PreparedStatement的实例
            preparedStatement = connection.prepareStatement(sql);

            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);

            }

            //4.执行sql
            preparedStatement.execute();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //5.关闭资源
            JDBCUtils.closeResource(connection, preparedStatement);
        }
    }


    //修改word1中的数据
    @Test
    public void testUpdate() {

        //1.获取数据库连接
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            //2.预编译sql语句， 返回PreparedStatement的实例
            String sql = "update word1 set age = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);

            //3.填充占位符
            preparedStatement.setObject(1, 29);
            preparedStatement.setObject(2, 1);

            //4.执行sql
            preparedStatement.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //5.关闭资源
            JDBCUtils.closeResource(connection, preparedStatement);
        }
    }

    //向word1中添加数据
    @Test
    public void testPreparedStatement() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取配置
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

            connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);

            //4.预编译sql语句，返回PreparedStatement的实例
            String sql = "insert into word1(name,age) values(?,?)"; //? 表示占位符
            preparedStatement = connection.prepareStatement(sql);

            //5.填充占位符
            preparedStatement.setString(1, "高启盛");
            preparedStatement.setInt(2, 30);

            //6.执行sql语句
            preparedStatement.execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

            //7.关闭资源
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


