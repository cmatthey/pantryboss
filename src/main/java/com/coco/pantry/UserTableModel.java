/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import java.sql.ResultSetMetaData;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Coco
 */
public class UserTableModel {

    public static final String QUERY_STATEMENT_ACCOUNT
            = "SELECT account_id FROM account WHERE username = ? AND password = ?";

    private int account_id = -1;
    private String username = null;
//    private Map<Integer, Object[]> dishes = new TreeMap<>();
    private SQLQuery sQLQuery;
    private CachedRowSet cachedrowset = null;
    private ResultSetMetaData metadata = null;

    public UserTableModel(int account_id) {
        this.account_id = account_id;
        sQLQuery = new SQLQuery();
        run();
    }

    public void setAccount_id(int account_id) {
        if (account_id != this.account_id) {
            this.account_id = account_id;
            run();
        }
    }

    private void run() {
    }
}
