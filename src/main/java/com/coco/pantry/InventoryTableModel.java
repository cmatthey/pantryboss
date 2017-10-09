/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import javax.sql.rowset.CachedRowSet;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Coco
 */
public class InventoryTableModel extends AbstractTableModel {

    public static final String DATABASE_NAME = "pantry";
    public static final String EDIABLE_COLUMN_NAME = "quantity";
    public static final String QUERY_STATEMENT = "SELECT inventory_id, item, quantity, recorder_point FROM inventory WHERE account_id = ";
    public static final String UPDATE_STATEMENT = "UPDATE inventory SET quantity = ? WHERE account_id = ? AND inventory_id = ?";
    private String username = System.getenv("MYSQL_USERNAME");
    private String password = System.getenv("MYSQL_PASSWORD");
    private SQLQuery sQLQuery = new SQLQuery(DATABASE_NAME, username, password);
    private int account_id;
    private String queryStr;
    private String updateStr;
    private PreparedStatement preparedStatement;
    private CachedRowSet cachedrowset;

    public InventoryTableModel(int account_id) {
        this.account_id = account_id;
    }

    private void run() {
        queryStr = QUERY_STATEMENT + account_id;
        cachedrowset = sQLQuery.executeQuery(queryStr);
    }

    @Override
    public int getRowCount() {
        if (cachedrowset == null) {
            run();
        }
        return cachedrowset.size();
    }

    @Override
    public int getColumnCount() {
        if (cachedrowset == null) {
            run();
        }
        try {
            return cachedrowset.getMetaData().getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getValueAt(int row, int col) {
        try {
            cachedrowset.absolute(row + 1);
            Object o = cachedrowset.getObject(col + 1);
            int type = cachedrowset.getMetaData().getColumnType(col + 1);
            switch (type) {
                case Types.TINYINT:
                case Types.INTEGER:
                    return (int) o;
                case Types.VARCHAR:
                    return (String) o;
                default:
                    return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        try {
            cachedrowset.absolute(row + 1);
            int inventory_id = cachedrowset.getInt("inventory_id");
            sQLQuery.executeUpdateInventory(null, (int) value, account_id, inventory_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        try {
            cachedrowset.absolute(row + 1);
            String columnName = cachedrowset.getMetaData().getColumnName(col + 1);
            if (columnName.equals(EDIABLE_COLUMN_NAME)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getColumnName(int col) {
        try {
            return cachedrowset.getMetaData().getColumnName(col + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return super.getColumnName(col);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        //TODO:
    }
}
