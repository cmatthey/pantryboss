/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

/**
 *
 * @author Coco
 */
public class InventoryTableController implements RowSetListener {

    private PantryGui pantryGui;
    private InventoryTableModel inventoryTableModel;

    public InventoryTableController(PantryGui pantryGui) {
        this.pantryGui = pantryGui;
        inventoryTableModel = new InventoryTableModel(pantryGui.getAccount_id());
    }

    public InventoryTableModel getInventoryTableModel() {
        return inventoryTableModel;
    }

    public void account_idChanged() {
        inventoryTableModel.setAccount_id(pantryGui.getAccount_id());
    }

    @Override
    public void rowSetChanged(RowSetEvent event) {
        System.out.println("rowSetChanged");
    }

    @Override
    public void rowChanged(RowSetEvent event) {
        System.out.println("rowChanged");
    }

    @Override
    public void cursorMoved(RowSetEvent event) {
        System.out.println("cursorMoved");
    }
}
