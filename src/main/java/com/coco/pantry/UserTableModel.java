package com.coco.pantry;

import com.coco.pantry.SQLQuery.Param;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Coco
 */
public class UserTableModel /* implements ButtonModel, Document */ {

    public static final String SELECT_STATEMENT
            = "SELECT account_id, username, password FROM account WHERE username = ?";
    public static final String INSERT_STATEMENT
            = "INSERT INTO account (username, password) VALUES (?, ?)";

    private Object[] userInfo;
    private SQLQuery sQLQuery = new SQLQuery();
    private CachedRowSet cachedrowset = null;
    private ResultSetMetaData metadata = null;

    public Object[] runSelect(String username) {
        userInfo = new Object[]{-1, null, null};
        cachedrowset = sQLQuery.execute(SELECT_STATEMENT, new ArrayList<Param>(Arrays.asList(sQLQuery.new Param(username, Types.VARCHAR))));
        if (cachedrowset.size() > 0) {
            try {
                cachedrowset.first();
                userInfo = new Object[]{cachedrowset.getInt("account_id"),
                    cachedrowset.getString("username"),
                    cachedrowset.getString("password")};
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    public Object[] runInsert(String username, String password) {
        runSelect(username);
        if ((int) userInfo[0] == -1) {
            cachedrowset = sQLQuery.execute(INSERT_STATEMENT, new ArrayList<Param>(Arrays.asList(
                    sQLQuery.new Param(username, Types.VARCHAR),
                    sQLQuery.new Param(username, Types.VARCHAR))));
        }
        return runSelect(username);
    }
}
