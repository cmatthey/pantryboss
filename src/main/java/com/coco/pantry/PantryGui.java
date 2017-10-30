package com.coco.pantry;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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

    private int account_id = -1;
    private String username = null;

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
        window.setMinimumSize(new Dimension(width, height));
        window.setTitle(title);
        window.setContentPane(createCardsPanel());
        window.setVisible(true);
        window.pack();
        return window;
    }

    public JFrame initComponents() {
        JFrame window = initWindow("Pantry Boss", 1100, 1100);
        cards = createCardsPanel();
        window.setContentPane(cards);
        if (account_id == -1) {
            createLoginDialog(window);
        }
        return window;
    }

    public JPanel createWelcomePanel() {
        JPanel card = new JPanel(new BorderLayout());
        JLabel title = new JLabel("What's for dinner?", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
        title.setForeground(Color.RED);
        card.add(title, BorderLayout.NORTH);
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(ROW_COUNT, COL_COUNT, 20, 20));
        recipeButtons = new ArrayList<>();
        for (int i = 0; i < ROW_COUNT * COL_COUNT; i++) {
            JButton button = new JButton();
            button.setSize(525, 350);
            recipeButtons.add(button);
            grid.add(button);
        }
        displayRecipes();
        card.add(grid, BorderLayout.CENTER);
        return card;
    }

    public JPanel createInventoryPanel() {
        JPanel card = new JPanel(new BorderLayout());
        jTable = new JTable(inventoryTableController.getInventoryTableModel());
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
                recipeTableController.updateAccount_id();
                inventoryTableController.updateAccount_id();
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
                username = "";
                recipeTableController.updateAccount_id();
                inventoryTableController.updateAccount_id();
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
        JDialog loginDialog = new JDialog(window);
        loginDialog.setSize(300, 300);
        loginDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
            userTableController.authenticate(username, password);
            if (account_id == -1) {
                errLabel.setText("Login failed. Please try again.");
                usernameTextField.setText("");
                passwordField.setText("");
            } else {
                recipeTableController.updateAccount_id();
                inventoryTableController.updateAccount_id();
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

    public void createSignupDialog(JFrame window) {
        JDialog signupDialog = new JDialog(window);
        signupDialog.setSize(300, 300);
        signupDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
            if (username.isEmpty()) {
                errLabel.setText("Username cannot be empty. Please try again.");
                usernameTextField.setText("");
                passwordField.setText("");
            } else {
                userTableController.newUser(username, password);
                if (account_id == -1) {
                    errLabel.setText("Username is taken. Please try again.");
                    usernameTextField.setText("");
                    passwordField.setText("");
                } else {
                    recipeTableController.updateAccount_id();
                    inventoryTableController.updateAccount_id();
                    signupDialog.setVisible(false);
                    signupDialog.dispose();
                }
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

    public void displayRecipes() {
        if (account_id == -1) {
            for (int i = 0; i < ROW_COUNT * COL_COUNT; i++) {
                String placeholderImg = "/images/recipe.png";
                URL resource = this.getClass().getResource(placeholderImg);
                JButton button = recipeButtons.get(i);
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setText("Pick a recipe");
                button.setIcon(new ImageIcon(resource));
                button.setEnabled(false);
            }
        } else {
            RecipeTableModel recipeTableModel = recipeTableController.getRecipeTableModel();
            TreeMap<Integer, String[]> dishes = (TreeMap) recipeTableModel.getDishesSimple();
            Iterator iterator = dishes.entrySet().iterator();
            for (int i = 0; i < ROW_COUNT * COL_COUNT /*&& i < dishes.size()*/; i++) {
                JButton button = recipeButtons.get(i);
                if (iterator.hasNext()) {
                    Map.Entry<Integer, String[]> entry = (Map.Entry) iterator.next();
                    URL resource = this.getClass().getResource(entry.getValue()[1]);
                    button.setText(entry.getValue()[0]);
                    button.setIcon(new ImageIcon(resource));
                    button.setEnabled(true);
                    button.addActionListener((ActionEvent e) -> {
                        int choice = createConfirmOptionPane(entry.getValue()[0]);
                        if (choice == JOptionPane.YES_OPTION) {
                            recipeTableController.consume();
                        }
                    });
                } else {
                    String placeholderImg = "/images/recipe.png";
                    URL resource = this.getClass().getResource(placeholderImg);
                    button.setText("Pick a recipe");
                    button.setIcon(new ImageIcon(resource));
                }
            }
        }
    }

    public static void main(String[] args) {
        PantryGui pantry = new PantryGui(-1);
    }
}
