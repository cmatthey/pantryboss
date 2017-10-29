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

    public boolean newUser(String username, String password) {
        Object[] userInfo = userTableModel.runSelect(username);
        if ((int) userInfo[0] == -1) {
            userInfo = userTableModel.runInsert(username, password);
            pantryGui.setAccount_id((int) userInfo[0]);
            pantryGui.setUsername(username);
            return true;
        }
        return false;
    }

    public boolean authenticate(String username, String passwor) {
        Object[] userInfo = userTableModel.runSelect(username);
        if (passwor.equals((String) userInfo[2])) {
            pantryGui.setAccount_id((int) userInfo[0]);
            pantryGui.setUsername(username);
            return true;
        }
        return false;
    }
}
// TODO: ensure username cannot be empty
