/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

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
    public static final String QUERY_STATEMENT_RECIPE_BY_ACCOUNT = "SELECT DISTINCT recipe.recipe_id, recipe.dish, recipe.img FROM recipe, ingredient "
            + "WHERE recipe.recipe_id = ingredient.recipe_id "
            + "AND ingredient.grocery_id IN (select grocery.grocery_id from inventory, grocery "
            + "WHERE inventory.grocery_id = grocery.grocery_id AND inventory.account_id = ?) ORDER BY recipe.recipe_id";

    private int account_id = -1;
    private Map<Integer, String[]> dishes = new TreeMap<>();
    private SQLQuery sQLQuery;
    private CachedRowSet cachedrowset = null;
    private ResultSetMetaData metadata = null;

    public RecipeTableModel(int account_id) {
        this.account_id = account_id;
        String dbusername = System.getenv("MYSQL_USERNAME");
        String dbpassword = System.getenv("MYSQL_PASSWORD");
        sQLQuery = new SQLQuery(Constants.DATABASE_NAME, dbusername, dbpassword);
        run();
    }

    public void setAccount_id(int account_id) {
        if (account_id != this.account_id) {
            this.account_id = account_id;
            run();
        }
    }

    public Map<Integer, String[]> getDishes() {
        return dishes;
    }

    private void run() {
        try {
            if (account_id == -1) {
                cachedrowset = sQLQuery.execute(QUERY_STATEMENT_RECIPE, null);
            } else {
                ArrayList<PreparedParameter> params = new ArrayList<>();
                params.add(new PreparedParameter(account_id, Types.INTEGER));
                cachedrowset = sQLQuery.execute(QUERY_STATEMENT_RECIPE_BY_ACCOUNT, params);
                while (cachedrowset.next()) {
                    int recipe_id = cachedrowset.getInt("recipe_id");
                    String[] row = new String[2];
                    row[0] = (cachedrowset.getString("dish"));
                    row[1] = (cachedrowset.getString("img"));
                    dishes.put(recipe_id, row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
