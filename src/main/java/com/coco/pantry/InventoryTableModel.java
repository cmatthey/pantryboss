/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
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

    public static final String EDIABLE_COLUMN_NAME = "quantity";
    public static final String QUERY_STATEMENT = "SELECT inventory_id, item, quantity, recorder_point FROM inventory WHERE account_id = %s";
    public static final String UPDATE_STATEMENT = "UPDATE inventory SET quantity = ? WHERE account_id = ? AND inventory_id = ?";

    private String username = System.getenv("MYSQL_USERNAME");
    private String password = System.getenv("MYSQL_PASSWORD");
    private SQLQuery sQLQuery = new SQLQuery(Constants.DATABASE_NAME, username, password);

    private int account_id;
    private String queryStr = null;
    private String updateStr = null;
    private PreparedStatement preparedStatement = null;
    private CachedRowSet cachedrowset = null;
    private ResultSetMetaData metadata = null;
    private int numcols = 0;
    private int numrows = 0;

    public InventoryTableModel(int account_id) {
        this.account_id = account_id;
        run();
    }

    private void run() {
        queryStr = String.format(QUERY_STATEMENT, account_id);
        cachedrowset = sQLQuery.executeQuery(queryStr);
        try {
            metadata = cachedrowset.getMetaData();
            numcols = metadata.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        numrows = cachedrowset.size();
    }

    @Override
    public int getRowCount() {
        return numrows;
    }

    @Override
    public int getColumnCount() {
        return numcols;
    }

    @Override
    public Object getValueAt(int row, int col) {
        try {
            cachedrowset.absolute(row + 1);
            Object o = cachedrowset.getObject(col + 1);
            int type = metadata.getColumnType(col + 1);
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
            cachedrowset.moveToCurrentRow();// TODO: check this line The line is not edited correction
            switch (metadata.getColumnType(col + 1)) {
                case Types.INTEGER:
                    cachedrowset.updateObject(col + 1, Integer.parseInt((String) value));
                    System.out.println(String.format("Set value (int) at %d, %d", row + 1, col + 1));
                    break;
                case Types.VARCHAR:
                    cachedrowset.updateObject(col + 1, (String) value);
                    System.out.println(String.format("Set value (String) at %d, %d", row + 1, col + 1));
                    break;
                default:
                    System.out.println(String.format("Set value (Object) at %d, %d", row + 1, col + 1));
                    cachedrowset.updateObject(col + 1, value);
            }
            // TODO: clean up the code
//            int inventory_id = cachedrowset.getInt("inventory_id");
//            sQLQuery.executeUpdateInventory(null, (int) value, account_id, inventory_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        try {
            String columnName = metadata.getColumnName(col + 1);
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
            return metadata.getColumnName(col + 1);
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
