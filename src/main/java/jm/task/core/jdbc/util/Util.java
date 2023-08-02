package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            try {
                Properties properties = getPropertiesForDatabaseConnection();
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.url", properties.getProperty("url"))
                        .setProperty("hibernate.connection.username", properties.getProperty("username"))
                        .setProperty("hibernate.connection.password", properties.getProperty("password"))
                        .setProperty("hibernate.connection.dialect", properties.getProperty("dialect"))
                        .addAnnotatedClass(User.class);

                entityManagerFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    public static Connection getConnection() {
        Connection connection;
        Properties properties = getPropertiesForDatabaseConnection();

        try {
            connection = DriverManager
                    .getConnection(properties.getProperty("url"),
                            properties.getProperty("username"),
                            properties.getProperty("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    private static Properties getPropertiesForDatabaseConnection() {
        Properties properties = new Properties();

        try {
            Reader reader = new FileReader("src/main/resoures/database.properties");
            properties.load(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}
