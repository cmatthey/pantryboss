/*
 * Copywrite(c) 2017 Coco Matthey
 */
package com.coco.pantry;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.sql.rowset.CachedRowSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Coco
 */
public class PantryGui {

    public static final int ROW_COUNT = 2;
    public static final int COL_COUNT = 2;
    public static final String QUERY_STATEMENT_ACCOUNT
            = "SELECT account_id FROM account WHERE username = ? AND password = ?";

    private int account_id = -1;
    private String username = null;
    private String password = null;
    private SQLQuery sQLQuery = new SQLQuery();

    private JFrame window;
    private JPanel cards;
    private JTable jTable;
    private ArrayList<JButton> recipeButtons;
    private UserTableController userTableController;
    private InventoryTableController inventoryTableController;
    private RecipeTableController recipeTableController;
    private CachedRowSet cachedrowset = null;
    private ResultSetMetaData metadata = null;

    public PantryGui(int account_id) {
        this.account_id = account_id;
        userTableController = new UserTableController(this);
        inventoryTableController = new InventoryTableController(this);
        recipeTableController = new RecipeTableController(this);
        window = initComponents();
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JFrame initWindow(String title, int width, int height) {
        window = new JFrame();
        window.setJMenuBar(createMenuBar());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(width, height);
        window.setTitle(title);
        window.setContentPane(createCardsPanel());
        window.setVisible(true);
        window.pack();
        return window;
    }

    public JFrame initWindow(String title) {
        return initWindow(title, 600, 600);
    }

    public JFrame initWindow() {
        return this.initWindow("", 600, 600);
    }

    public JFrame initComponents() {
        JFrame window = initWindow("Pantry Boss", 600, 600);
        cards = createCardsPanel();
        window.setContentPane(cards);
        createLoginDialog(window);
        return window;
    }

    public JPanel createWelcomePanel() {
        JPanel card = new JPanel(new BorderLayout());
        JLabel title = new JLabel("What's for dinner?", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
        title.setForeground(Color.ORANGE);
        card.add(title, BorderLayout.NORTH);
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(ROW_COUNT, COL_COUNT, 20, 20));
        recipeButtons = new ArrayList<>();
        for (int i = 0; i < ROW_COUNT * COL_COUNT; i++) {
            JButton button = new JButton();
            // TODO: troubleshot
            button.setSize(525, 350);
            recipeButtons.add(button);
            grid.add(button);
        }
        setRecipeImages();
        card.add(grid, BorderLayout.CENTER);
        return card;
    }

    public JPanel createInventoryPanel() {
        JPanel card = new JPanel(new BorderLayout());
        jTable = new JTable(inventoryTableController.getInventoryTableModel());
        jTable.getModel().addTableModelListener(inventoryTableController);
        jTable.getSelectionModel().addListSelectionListener(inventoryTableController);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        card.add(jScrollPane, BorderLayout.CENTER);
        return card;
    }

    public JPanel createCardsPanel() {
        JPanel cards = new JPanel(new CardLayout());
        cards.add(createWelcomePanel(), "Cook");
        cards.add(createInventoryPanel(), "Restock");
        cards.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.ORANGE, Color.lightGray));
        return cards;
    }

    public JMenuBar createMenuBar() {
        final String[] menuItemManage = {"Cook", "Restock"};
        final String[] menuItemLogout = {"Logout"};
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMmenu = new JMenu("Manage");
        for (String text : menuItemManage) {
            JMenuItem jMenuItem = new JMenuItem(text);
            jMenuItem.addActionListener((ActionEvent e) -> {
                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, text);
            });
            jMmenu.add(jMenuItem);
        }
        jMenuBar.add(jMmenu);
        jMmenu = new JMenu("Logout");
        for (String text : menuItemLogout) {
            JMenuItem jMenuItem = new JMenuItem(text);
            jMenuItem.addActionListener((ActionEvent e) -> {
                account_id = -1;
                recipeTableController.updateAccount_id();
                setRecipeImages();
                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, "Cook");
                createLoginDialog(window);
            });
            jMmenu.add(jMenuItem);
        }
        jMenuBar.add(jMmenu);
        return jMenuBar;
    }

    public void createLoginDialog(JFrame window) {
        if (account_id == -1) {
            JDialog loginDialog = new JDialog(window);
            loginDialog.setSize(300, 300);
            loginDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            // TODO: Question - How to make the size automatically the right size
//            loginDialog.pack();
            JPanel dialogPanel = new JPanel(new GridBagLayout());
            JLabel errLabel = new JLabel("", SwingConstants.LEFT);
            errLabel.setForeground(Color.red);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0;
            dialogPanel.add(errLabel, c);
            JLabel uLabel = new JLabel("Username", SwingConstants.LEFT);
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            dialogPanel.add(uLabel, c);
            JTextField usernameTextField = new JTextField(10);
            usernameTextField.setToolTipText("Enter Username");
            usernameTextField.setMinimumSize(usernameTextField.getPreferredSize());
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 1;
            dialogPanel.add(usernameTextField, c);
            // TODO: Question - does not seem difference
            JLabel pLabel = new JLabel("Password", SwingConstants.LEFT);
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 2;
            dialogPanel.add(pLabel, c);
            JPasswordField passwordField = new JPasswordField(10);
            passwordField.setToolTipText("Enter Password");
            passwordField.setMinimumSize(passwordField.getPreferredSize());
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 2;
            dialogPanel.add(passwordField, c);
            JButton loginButton = new JButton("Log in");
            loginButton.addActionListener((ActionEvent e) -> {
                String username = usernameTextField.getText();
                String password = String.valueOf(passwordField.getPassword());
//                int account_id = authenticate(username, password);
                userTableController.authenticate(username, password);
                if (account_id == -1) {
                    errLabel.setText("Login failed. Please try again.");
                    usernameTextField.setText("");
                    passwordField.setText("");
                } else {
                    recipeTableController.updateAccount_id();
                    setRecipeImages();
                    inventoryTableController.account_idChanged();
                    updateTable(jTable);
                    loginDialog.setVisible(false);
                    loginDialog.dispose();
                }
            });
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 3;
            dialogPanel.add(loginButton, c);
            JLabel rLable = new JLabel("No account yet", SwingConstants.LEFT);
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 5;
            dialogPanel.add(rLable, c);
            JButton signupButton = new JButton("Sign up");
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 5;
            signupButton.addActionListener((ActionEvent e) -> {
                loginDialog.setVisible(false);
                loginDialog.dispose();
                createSignupDialog(window);
            });
            dialogPanel.add(signupButton, c);
            loginDialog.setContentPane(dialogPanel);
            loginDialog.setVisible(true);
        }
    }

    public void createSignupDialog(JFrame window) {
        JDialog signupDialog = new JDialog(window);
        signupDialog.setSize(300, 300);
        signupDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // TODO: Question - How to make the size automatically the right size
//            loginDialog.pack();
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        JLabel errLabel = new JLabel("", SwingConstants.LEFT);
        errLabel.setForeground(Color.red);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        dialogPanel.add(errLabel, c);
        JLabel uLabel = new JLabel("Username", SwingConstants.LEFT);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        dialogPanel.add(uLabel, c);
        JTextField usernameTextField = new JTextField(10);
        usernameTextField.setToolTipText("Enter Username");
        usernameTextField.setMinimumSize(usernameTextField.getPreferredSize());
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        dialogPanel.add(usernameTextField, c);
        JLabel pLabel = new JLabel("New Password", SwingConstants.LEFT);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        dialogPanel.add(pLabel, c);
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setToolTipText("Enter Password");
        passwordField.setMinimumSize(passwordField.getPreferredSize());
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        dialogPanel.add(passwordField, c);
        JButton signupButton = new JButton("Sign up");
        signupButton.addActionListener((ActionEvent e) -> {
            String username = usernameTextField.getText();
            String password = String.valueOf(passwordField.getPassword());
            userTableController.newUser(username, password);
            if (account_id == -1) {
                errLabel.setText("Username is taken. Please try again.");
                usernameTextField.setText("");
                passwordField.setText("");
            } else {
                recipeTableController.updateAccount_id();
                setRecipeImages();
                inventoryTableController.account_idChanged();
                updateTable(jTable);
                signupDialog.setVisible(false);
                signupDialog.dispose();
            }
        });
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        dialogPanel.add(signupButton, c);
        signupDialog.setContentPane(dialogPanel);
        signupDialog.setVisible(true);
    }

    public int createConfirmOptionPane(String dish) {
        int choice = JOptionPane.showConfirmDialog(window,
                String.format("You chose %s. Click Yes to proceed.", dish),
                "Confirm your choice",
                JOptionPane.YES_NO_OPTION);
        return choice;
    }

    private void setRecipeImages() {
        RecipeTableModel recipeTableModel = recipeTableController.getRecipeTableModel();
        TreeMap<Integer, Object[]> dishes = (TreeMap) recipeTableModel.getDishes();
        Iterator iterator = dishes.entrySet().iterator();
        int[] grocery_ids = new int[dishes.size()];
        int[] quantity = new int[dishes.size()];
        for (int i = 0; i < ROW_COUNT * COL_COUNT && i < dishes.size(); i++) {
            if (iterator.hasNext()) {
                Map.Entry<Integer, Object[]> entry = (Map.Entry) iterator.next();
                URL resource = this.getClass().getResource((String) entry.getValue()[1]);
                JButton button = recipeButtons.get(i);
                button.setIcon(new ImageIcon(resource));
                button.setEnabled(true);
                button.addActionListener((ActionEvent e) -> {
                    int choice = createConfirmOptionPane((String) entry.getValue()[0]);
                    if (choice == JOptionPane.YES_OPTION) {
                        System.out.println((String) entry.getValue()[0] + " is selected");
                        // TODO: to further deduct the inventory
                        // https://stackoverflow.com/questions/25674737/mysql-update-multiple-rows-with-different-values-in-one-query
//                        String cols = String.join(",", (String) (dishes.values())[3]);
                        String update_inventory_statement
                                = "UPDATE inventory SET quantity = "
                                + "ELT(grocery_id, 15, 15) WHERE grocery_id in (1, 2) AND account_id = ?";
                    }
                });
            }
        }
    }

    public void updateTable(JTable jTable) {
        jTable.setModel(inventoryTableController.getInventoryTableModel());
    }

    public static void main(String[] args) {
        // TODO: serialization
        PantryGui pantry = new PantryGui(-1);
    }
}
