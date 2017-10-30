package com.coco.pantry;

import com.coco.pantry.SQLQuery.Param;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Coco
 */
public class InventoryTableModel extends AbstractTableModel {

    public static final String EDIABLE_COLUMN_NAME = "quantity";
    public static final String QUERY_STATEMENT_INVENTORY = "SELECT inventory_id, item, quantity, recorder_point FROM inventory iv, grocery g WHERE account_id = ? AND iv.grocery_id = g.grocery_id";
    public static final String UPDATE_STATEMENT_INVENTORY = "UPDATE inventory SET quantity = ? WHERE account_id = ? AND inventory_id = ?";

    private int account_id;
    private SQLQuery sQLQuery;
    private CachedRowSet cachedrowsetSelect = null;
    private CachedRowSet cachedrowsetUpdate = null;
    private ResultSetMetaData metadata = null;
    private int numcols = 0;
    private int numrows = 0;

    public InventoryTableModel(int account_id) {
        this.account_id = account_id;
        sQLQuery = new SQLQuery();
        runSelect();
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
        runSelect();
    }

    private void runSelect() {
        ArrayList<Param> params = new ArrayList<>();
        params.add(sQLQuery.new Param(account_id, Types.INTEGER));
        cachedrowsetSelect = sQLQuery.execute(QUERY_STATEMENT_INVENTORY, params);
        numrows = cachedrowsetSelect.size();
        try {
            metadata = cachedrowsetSelect.getMetaData();
            numcols = metadata.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void runUpdate(int quantity, int inventory_id) {
        ArrayList<Param> params = new ArrayList<>();
        params.add(sQLQuery.new Param(quantity, Types.INTEGER));
        params.add(sQLQuery.new Param(account_id, Types.INTEGER));
        params.add(sQLQuery.new Param(inventory_id, Types.INTEGER));
        cachedrowsetUpdate = sQLQuery.execute(UPDATE_STATEMENT_INVENTORY, params);
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
            cachedrowsetSelect.absolute(row + 1);
            Object o = cachedrowsetSelect.getObject(col + 1);
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
            cachedrowsetSelect.absolute(row + 1);
            switch (metadata.getColumnType(col + 1)) {
                case Types.INTEGER:
                    cachedrowsetSelect.updateObject(col + 1, Integer.parseInt((String) value));
                    break;
                case Types.VARCHAR:
                    cachedrowsetSelect.updateObject(col + 1, (String) value);
                    break;
                default:
                    cachedrowsetSelect.updateObject(col + 1, value);
            }
            runUpdate(Integer.parseInt((String) value), cachedrowsetSelect.getInt("inventory_id"));
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
}
