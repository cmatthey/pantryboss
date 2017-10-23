/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

/**
 *
 * @author Coco
 */
public class UserTableController {

    private PantryGui pantryGui;
    private UserTableModel userTableModel;

    public UserTableController(PantryGui pantryGui) {
        this.pantryGui = pantryGui;
        userTableModel = new UserTableModel();
    }

    public Object[] newUser(String username, String password) {
        Object[] userInfo = userTableModel.runInsert(username, password);
        pantryGui.setAccount_id((int) userInfo[0]);
        return userInfo;
    }

    public boolean authenticate(String username, String passwor) {
        Object[] userInfo = userTableModel.runSelect(username);
        return passwor.equals((String) userInfo[2]);
    }
}
