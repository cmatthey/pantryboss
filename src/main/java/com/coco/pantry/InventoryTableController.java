package com.coco.pantry;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Coco
 */
public class InventoryTableController implements TableModelListener, ListSelectionListener {

    private PantryGui pantryGui;
    private InventoryTableModel inventoryTableModel;

    public InventoryTableController(PantryGui pantryGui) {
        this.pantryGui = pantryGui;
        inventoryTableModel = new InventoryTableModel(pantryGui.getAccount_id());
    }

    public InventoryTableModel getInventoryTableModel() {
        return inventoryTableModel;
    }

    public void updateAccount_id() {
        inventoryTableModel.setAccount_id(pantryGui.getAccount_id());
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        System.out.println("TableModelListener tableChanged");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("ListSelectionListener valueChanged");
    }
}
