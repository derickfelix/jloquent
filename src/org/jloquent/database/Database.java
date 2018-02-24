/*
 * The MIT License
 *
 * Copyright 2018 Derick Felix.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jloquent.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author derickfelix
 * @date Feb 24, 2018
 */
public class Database {
    
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jloquent";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static Connection connection;
    
    public static Connection open() {
        try {
            
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to open connection: " + e);
        }
        
        return connection;
    }
    
    public static void execute(String sql) {
        Database.open();
        try (Statement statement = connection.createStatement()) {    
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Failed to execute sql: " + e);
        }
        Database.close();
    }
    public static ResultSet executeQuery(String sql) {
        Database.open();
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {    
            
            return resultSet;
        } catch (SQLException e) {
            System.err.println("Failed to execute sql: " + e);
        }
        Database.close();
        
        return null;
    }
    
    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch(SQLException e) {
            System.err.println("Failed to close connection: " + e);
        }
    }   
}