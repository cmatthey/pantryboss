/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Coco
 */
public class InventoryTableController implements CellEditorListener {

    private GUIHelper gUIHelper;
    private InventoryTableModel inventoryTableModel;

    public InventoryTableController(GUIHelper gUIHelper) {
        this.gUIHelper = gUIHelper;
        inventoryTableModel = new InventoryTableModel(gUIHelper.getAccount_id());
    }

    public InventoryTableModel getInventoryTableModel() {
        return inventoryTableModel;
    }

    @Override
    public void editingStopped(ChangeEvent e) {

    }

    @Override
    public void editingCanceled(ChangeEvent e) {
    }

}
