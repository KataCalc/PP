package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DRIVER_1 = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME_1 = "root";
    private static final String PASSWORD_1 = "root1";
    private static final String URL_1 = "jdbc:mysql://localhost:3306/dbluser";

public Connection getConnection(){
    Connection connection = null;
    try {

        Class.forName(DRIVER_1);
        connection = DriverManager.getConnection(URL_1,USERNAME_1,PASSWORD_1);
        System.out.println("Connection OK");
        connection.setAutoCommit(false);
        connection.commit();

    } catch (ClassNotFoundException | SQLException e) {
        System.out.println("Connection ERROR");
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


    }
    return connection;
}
    }





