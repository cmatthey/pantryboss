/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Coco
 */
public class UserTableController implements ActionListener {

    private PantryGui pantryGui;
    private UserTableModel userTableModel;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ActionListener actionPerformed");
    }
}
