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
import java.sql.Types;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Coco
 */
public class SQLQuery {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private String databaseURL;
    private String username;
    private String password;
    private Connection connection = null;
    private Statement statement = null;
    private CachedRowSet cachedRowSet = null;

    public SQLQuery(String databaseName, String username, String password) {
        databaseURL = String.format("jdbc:mysql://localhost/%s?useSSL=false&useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC", databaseName);
        this.username = username;
        this.password = password;
    }

    public CachedRowSet execute(String queryStr, ArrayList<PreparedParameter> params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        CachedRowSet cachedRowSet = null;
        boolean hasResultSet = false;

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database");
            connection = DriverManager.getConnection(databaseURL, username, password);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(queryStr);
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    switch (params.get(i).type) {
                        case Types.INTEGER:
                        case Types.TINYINT:
                            preparedStatement.setInt(i + 1, (int) params.get(i).value);
                            break;
                        case Types.VARCHAR:
                        default:
                            preparedStatement.setString(i + 1, (String) params.get(i).value);
                    }
                }
            }
            // http://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/
            // TODO: delete
            hasResultSet = preparedStatement.execute();
            connection.commit();
            ResultSet result = preparedStatement.getResultSet();
            cachedRowSet = new CachedRowSetImpl();
            if (hasResultSet) {
                cachedRowSet.populate(result);
            }
            preparedStatement.close();
            connection.close();
            System.out.println("Closed connection");
        } catch (SQLException e) {
            System.out.println("JDBC errors");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Errors in Class.forName");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
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
        return cachedRowSet;
    }
}
