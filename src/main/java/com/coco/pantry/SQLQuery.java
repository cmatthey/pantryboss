/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Coco
 */
public class SQLQuery {

    private String databaseURL;
    private String username;
    private String password;

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public SQLQuery(String databaseName, String username, String password) {
        databaseURL = "jdbc:mysql://localhost/" + databaseName + "?useSSL=false&useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC";
        this.username = username;
        this.password = password;
    }

    public CachedRowSet executeQuery(String query) {
        Connection connection = null;
        Statement statement = null;
        CachedRowSet cachedRowSet;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database");
            connection = DriverManager.getConnection(databaseURL, username, password);

            System.out.println("Executing a query");
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(result);

            connection.close();
            System.out.println("Closed connection");
            result.close();
            statement.close();
            return cachedRowSet;
        } catch (SQLException e) {
            System.out.println("JDBC errors");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Errors in Class.forName");
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Closed connection");
        }
        return null;
    }

    public int executeUpdateInventory(PreparedStatement preparedStatement, int quantity, int account_id, int inventory_id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database");
            connection = DriverManager.getConnection(databaseURL, username, password);

            System.out.println("Updating inventory");
            String updateString = "UPDATE inventory SET quantity = ? WHERE account_id = ? AND inventory_id = ?";
            statement = connection.prepareStatement(updateString);
            statement.setInt(1, quantity);
            statement.setInt(2, account_id);
            statement.setInt(3, inventory_id);
            System.out.println(statement.toString());
            int rows = statement.executeUpdate();

            connection.close();
            System.out.println("Closed connection");
            statement.close();

            return rows;
        } catch (SQLException e) {
            System.out.println("JDBC errors");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Errors in Class.forName");
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Closed connection");
        }
        return 0;
    }

    public static void main(String[] args) {
        String username = System.getenv("MYSQL_USERNAME");
        String password = System.getenv("MYSQL_PASSWORD");
        SQLQuery sqlQuery = new SQLQuery("pantry", username, password);
        try {
            CachedRowSet cachedrowset = sqlQuery.executeQuery("SELECT account_id, username, password FROM account");
            while (cachedrowset.next()) {
                int account_id = cachedrowset.getInt("account_id");
                String u = cachedrowset.getString("username");
                String p = cachedrowset.getString("password");

                System.out.print(String.format("account_id: %s", account_id));
                System.out.print(String.format("username: %s", u));
                System.out.print(String.format("password: %s", p));
            }
        } catch (SQLException e) {
            System.out.println("A SQLException occurred.");
            e.printStackTrace();
        }
        int r = sqlQuery.executeUpdateInventory(null, 77, 1, 1);
        System.out.println("r: " + r);
    }
}
