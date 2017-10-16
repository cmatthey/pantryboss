/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

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

    public void account_idChanged() {
        recipeTableModel.setAccount_id(pantryGui.getAccount_id());
    }
}
