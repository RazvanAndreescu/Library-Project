package com.sda.practical.database;

import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.LoggerUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.spi.ServiceException;

import java.util.Properties;

public class HibernateUtils {

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() throws DatabaseConnectionException, UnknownException {
        int counter = 3;
        if (sessionFactory == null) {
            synchronized (HibernateUtils.class) {
                while (sessionFactory == null && --counter >= 0) {
                    try {
                        org.hibernate.cfg.Configuration configuration = new Configuration();
                        // Hibernate settings equivalent to hibernate.cfg.xml's properties
                        Properties settings = new Properties();
                        settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                        settings.put(Environment.URL, "jdbc:mysql://localhost:3306/ppdatabase?serverTimezone=Europe/Bucharest");
                        settings.put(Environment.USER, "root");
                        settings.put(Environment.PASS, "xxxxx");
                        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                        settings.put(Environment.SHOW_SQL, "true");
                        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                        /*settings.put(Environment.HBM2DDL_AUTO, "create-drop");*/
                        configuration.setProperties(settings);
                        configuration.addAnnotatedClass(AuthorEntity.class);
                        configuration.addAnnotatedClass(BookEntity.class);
                        sessionFactory = configuration.buildSessionFactory();
                    } catch (ServiceException e) {
                        LoggerUtils.print("Retry to connect to the database!");
                        if (counter < 2) {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException ex) {
                                LoggerUtils.print(ex.getMessage());
                            }
                        }
                        if (counter == 0) {
                            throw new DatabaseConnectionException(e.getMessage(), e, "Can't connect to the database!");
                        }
                    } catch (Exception e) {
                        throw new UnknownException(e.getMessage(), e, "An unknown exception has occur!");
                    }
                }
            }
        }
        return sessionFactory;
    }
}
