package com.joysistvi.recordingapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    // Database connection parameters
    private final static String URL = "jdbc:mysql://localhost:3306/songs_db";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";


    // ducking exception
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD); // connection object
    }

}
