/*
 * Copywrite(c) 2017 Coco Matthey
 */
package com.coco.pantry;

import java.util.Map;

/**
 *
 * @author Coco
 */
public class RecipeTableController {

    private RecipeTableModel recipeTableModel;
    private PantryGui pantryGui;

    public RecipeTableController(PantryGui pantryGui) {
        this.pantryGui = pantryGui;
        recipeTableModel = new RecipeTableModel(pantryGui.getAccount_id());
    }

    public RecipeTableModel getRecipeTableModel() {
        return recipeTableModel;
    }

    public void updateAccount_id() {
        recipeTableModel.setAccount_id(pantryGui.getAccount_id());
    }

    public Map<Integer, Object[]> getDishes() {
        return recipeTableModel.getDishes();
    }
}
