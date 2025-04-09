package com.touristinfo.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionUtil {
    private static final String PROPERTIES_FILE = "database.properties";
    private static Properties properties = new Properties();
    
    static {
        try (InputStream input = DBConnectionUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.err.println("Unable to find " + PROPERTIES_FILE);
                throw new RuntimeException("Unable to find database properties file");
            }
            properties.load(input);
            
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        
        return DriverManager.getConnection(url, user, password);
    }
}
