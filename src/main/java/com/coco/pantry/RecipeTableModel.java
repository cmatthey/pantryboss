package com.coco.pantry;

import com.coco.pantry.SQLQuery.Param;
import com.google.common.base.Strings;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Coco
 */
public class RecipeTableModel {

    public static final String SELECT_RECIPE_STATEMENT = "SELECT recipe_id, dish, img FROM recipe";
    public static final String SELECT_RECIPE_BY_ACCOUNT_STATEMENT
            = "SELECT r.recipe_id, r.dish, r.img, iv.inventory_id, g.grocery_id, "
            + "iv.quantity AS total, ig.quantity AS needed "
            + "FROM account a, inventory iv, grocery g, ingredient ig, recipe r "
            + "WHERE a.account_id = iv.account_id AND iv.grocery_id = g.grocery_id "
            + "AND ig.grocery_id = g.grocery_id AND ig.recipe_id = r.recipe_id "
            + "AND iv.quantity >= ig.quantity AND a.account_id = ?";
    final String UPDATE_STATEMENT = "UPDATE inventory SET quantity = CASE %s END WHERE inventory_id in (%s) AND account_id = ?";
    final String WHEN_PATTERN = "WHEN inventory_id = ? then ?";

    private int account_id = -1;
    private Map<Integer, String[]> dishesSimple = new TreeMap<>();
    private Map<Integer, Map<Integer, Integer>> dishesComplete = new TreeMap<>();
    private SQLQuery sQLQuery;
    private CachedRowSet cachedrowset = null;
    private ResultSetMetaData metadata = null;

    public RecipeTableModel(int account_id) {
        this.account_id = account_id;
        sQLQuery = new SQLQuery();
        viewRecipes();
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
        viewRecipes();
    }

    public Map<Integer, String[]> getDishesSimple() {
        return dishesSimple;
    }

    public Map<Integer, Map<Integer, Integer>> getDishesComplete() {
        return dishesComplete;
    }

    private void viewRecipes() {
        try {
            if (account_id == -1) {
                cachedrowset = sQLQuery.execute(SELECT_RECIPE_STATEMENT, null);
            } else {
                cachedrowset = sQLQuery.execute(SELECT_RECIPE_BY_ACCOUNT_STATEMENT, new ArrayList<Param>(Arrays.asList(sQLQuery.new Param(account_id, Types.INTEGER))));
                metadata = cachedrowset.getMetaData();
                while (cachedrowset.next()) {
                    int recipe_id = cachedrowset.getInt("recipe_id");
                    String[] row = new String[2];
                    row[0] = cachedrowset.getString("dish");
                    row[1] = cachedrowset.getString("img");
                    int inventory_id = cachedrowset.getInt("inventory_id");
                    int grocery_id = cachedrowset.getInt("grocery_id");
                    /* Query used here: SELECT r.recipe_id, r.dish, r.img, iv.inventory_id, g.grocery_id, iv.quantity AS total, ig.quantity AS needed FROM account a, inventory iv, grocery g, ingredient ig, recipe r WHERE a.account_id = iv.account_id AND iv.grocery_id = g.grocery_id AND ig.grocery_id = g.grocery_id AND ig.recipe_id = r.recipe_id AND iv.quantity >= ig.quantity AND a.account_id = 5
                     * The CachedRowSet does not recognized AS total and AS needed
                     * We have to use cachedrowset.getInt(6) instead of getInt("total")
                     * and cachedrowset.getInt(7) instead of getInt("needed")
                     */
                    int total = cachedrowset.getInt(6);
                    int needed = cachedrowset.getInt(7);
                    dishesSimple.put(recipe_id, row);
                    if (dishesComplete.containsKey(recipe_id)) {
                        dishesComplete.get(recipe_id).put(grocery_id, total - needed);
                    } else {
                        Map<Integer, Integer> m = new TreeMap<>();
                        m.put(grocery_id, total - needed);
                        dishesComplete.put(recipe_id, m);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void consume(int recipe_id) {
        Map<Integer, Integer> ingredients = dishesComplete.get(recipe_id);
        List<SQLQuery.Param> params = new ArrayList<SQLQuery.Param>();
        for (Integer key : ingredients.keySet()) {
            params.add(sQLQuery.new Param(key, Types.INTEGER));
        }
        for (Integer value : ingredients.values()) {
            params.add(sQLQuery.new Param(value, Types.INTEGER));
        }
        for (Integer key : ingredients.keySet()) {
            params.add(sQLQuery.new Param(key, Types.INTEGER));
        }
        params.add(sQLQuery.new Param(account_id, Types.INTEGER));
        System.out.println(String.format(UPDATE_STATEMENT, Strings.repeat(WHEN_PATTERN, ingredients.size()), Strings.repeat("?, ", ingredients.size()).replaceAll(", $", "")));
        sQLQuery.execute(String.format(UPDATE_STATEMENT, Strings.repeat(WHEN_PATTERN, ingredients.size()), Strings.repeat("?, ", ingredients.size()).replaceAll(", $", "")), params);
        viewRecipes();
    }
}
