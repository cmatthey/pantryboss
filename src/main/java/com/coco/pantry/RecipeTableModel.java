/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import com.coco.pantry.SQLQuery.Param;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Coco
 */
public class RecipeTableModel {

    public static final String QUERY_STATEMENT_RECIPE = "SELECT recipe_id, dish, img FROM recipe";
    public static final String QUERY_STATEMENT_RECIPE_BY_ACCOUNT
            = "SELECT r.recipe_id, r.dish, r.img, iv.inventory_id, g.grocery_id, "
            + "iv.quantity AS total, ig.quantity AS needed "
            + "FROM account a, inventory iv, grocery g, ingredient ig, recipe r "
            + "WHERE a.account_id = iv.account_id AND iv.grocery_id = g.grocery_id "
            + "AND ig.grocery_id = g.grocery_id AND ig.recipe_id = r.recipe_id "
            + "AND iv.quantity >= ig.quantity AND a.account_id = ?";
    private int account_id = -1;
    private Map<Integer, Object[]> dishes = new TreeMap<>();
    private SQLQuery sQLQuery;
    private CachedRowSet cachedrowset = null;
    private ResultSetMetaData metadata = null;

    public RecipeTableModel(int account_id) {
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

    public Map<Integer, Object[]> getDishes() {
        return dishes;
    }

    private void run() {
        try {
            if (account_id == -1) {
                cachedrowset = sQLQuery.execute(QUERY_STATEMENT_RECIPE, null);
            } else {
                ArrayList<Param> params = new ArrayList<>();
                params.add(sQLQuery.new Param(account_id, Types.INTEGER));
                cachedrowset = sQLQuery.execute(QUERY_STATEMENT_RECIPE_BY_ACCOUNT, params);
                metadata = cachedrowset.getMetaData();
                while (cachedrowset.next()) {
                    int recipe_id = cachedrowset.getInt("recipe_id");
                    Object[] row = new Object[7];
                    row[0] = cachedrowset.getString("dish");
                    row[1] = cachedrowset.getString("img");
                    row[2] = cachedrowset.getInt("inventory_id");
                    row[3] = cachedrowset.getInt("grocery_id");
//                    row[4] = cachedrowset.getInt("total");
                    row[4] = cachedrowset.getInt(6);
//                    row[5] = cachedrowset.getInt("needed");
                    row[5] = cachedrowset.getInt(7);
                    dishes.put(recipe_id, row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
