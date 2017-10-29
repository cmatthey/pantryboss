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

    public Map<Integer, String[]> getDishes() {
        return recipeTableModel.getDishesSimple();
    }

    public void consume() {
        recipeTableModel.consume(pantryGui.getAccount_id());
        pantryGui.setRecipeImages();
    }
}
