package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static SessionFactory sessionFactory;
    private static final String DRIVER_1 = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME_1 = "root";
    private static final String PASSWORD_1 = "root1";
    private static final String URL_1 = "jdbc:mysql://localhost:3306/dbluser";

    public Connection getConnection() {
        Connection connection = null;
        try {

            Class.forName(DRIVER_1);
            connection = DriverManager.getConnection(URL_1, USERNAME_1, PASSWORD_1);
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

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/dbluser?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root1");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Connection OK");
            } catch (Exception e) {
                System.out.println("Connection ERROR");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

