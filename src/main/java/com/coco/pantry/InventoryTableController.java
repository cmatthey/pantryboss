/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Coco
 */
public class InventoryTableController implements RowSetListener, TableModelListener, ListSelectionListener, CellEditorListener {

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
        System.out.println("RowSetListener rowSetChanged");
    }

    @Override
    public void rowChanged(RowSetEvent event) {
        System.out.println("RowSetListener rowChanged");
    }

    @Override
    public void cursorMoved(RowSetEvent event) {
        System.out.println("RowSetListener cursorMoved");
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        System.out.println("TableModelListener tableChanged");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("ListSelectionListener valueChanged");
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        System.out.println("CellEditorListener editingStopped");
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
        System.out.println("CellEditorListener editingCanceled");
    }
}
