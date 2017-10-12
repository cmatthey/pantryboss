/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
public class GUIHelper {

    public static final int ROW_COUNT = 2;
    public static final int COL_COUNT = 2;
    private int account_id;
    private InventoryTableController inventoryTableController;
    private JPanel cards;
    private String username = System.getenv("MYSQL_USERNAME");
    private String password = System.getenv("MYSQL_PASSWORD");
    private SQLQuery sQLQuery = new SQLQuery(Constants.DATABASE_NAME, username, password);
    public static final String QUERY_STATEMENT = "SELECT account_id FROM account WHERE username = '%s' AND password = '%s'";
    private CachedRowSet cachedrowset = null;
    private ResultSetMetaData metadata = null;

    public GUIHelper(int account_id) {
        this.account_id = account_id;
        inventoryTableController = new InventoryTableController(this);
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public JFrame initWindow(String title, int width, int height) {
        JFrame window = new JFrame();
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
        return GUIHelper.this.initWindow(title, 600, 600);
    }

    public JFrame initWindow() {
        return GUIHelper.this.initWindow("", 600, 600);
    }

    public JFrame initComponents() {
        JFrame window = initWindow("Pantry Boss", 600, 600);
        cards = createCardsPanel();
        window.setContentPane(cards);
        return window;
    }

    public JPanel crateWelcomePanel() {
        JPanel card = new JPanel(new FlowLayout());
        // TODO: complete welcome panel
        JLabel uLabel = new JLabel("Username", SwingConstants.CENTER);
        JTextField usernameTextField = new JTextField(10);
        usernameTextField.setToolTipText("Enter Username");
        JLabel pLabel = new JLabel("Password", SwingConstants.CENTER);
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setToolTipText("Enter Password");
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameTextField.getText();
            char[] password = passwordField.getPassword();
            String queryStr = String.format(QUERY_STATEMENT, username, String.valueOf(password));
            try {
                cachedrowset = sQLQuery.executeQuery(queryStr);
                if (cachedrowset.size() != 1) {
                    account_id = -1;
                    usernameTextField.setText("");
                    passwordField.setText("");
                } else {
                    if (cachedrowset.next()) {
                        account_id = cachedrowset.getInt("account_id");
                        System.out.println("Found user " + account_id);
                        CardLayout cardLayout = (CardLayout) cards.getLayout();
                        cardLayout.show(cards, "Restock");
                    }
                }
            } catch (SQLException ex) {
                account_id = -1;
                ex.printStackTrace();
            }
        });
        card.add(uLabel);
        card.add(usernameTextField);
        card.add(pLabel);
        card.add(passwordField);
        card.add(loginButton);
        return card;
    }

    public JPanel createRecipePanel() {
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(ROW_COUNT, COL_COUNT, 20, 20));
        // TODO: get recipe from the DB
        ArrayList<JButton> buttons = new ArrayList<>();
        for (int i = 0; i < ROW_COUNT * COL_COUNT; i++) {
            JButton button = new JButton(String.valueOf(i));
            buttons.add(button);
            card.add(button);
        }
        return card;
    }

    public JPanel createInventoryPanel() {
        JPanel card = new JPanel(new BorderLayout());
        JTable jTable = new JTable(inventoryTableController.getInventoryTableModel());
//        jTable.getCellEditor().addCellEditorListener(inventoryTableController);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        card.add(jScrollPane, BorderLayout.CENTER);
        return card;
    }

    public void updateTable(JTable jTable) {
        jTable.setModel(inventoryTableController.getInventoryTableModel());
    }

    public JPanel createCardsPanel() {
        JPanel cards = new JPanel(new CardLayout());
        cards.add(crateWelcomePanel(), "Login");
        cards.add(createInventoryPanel(), "Restock");
        cards.add(createRecipePanel(), "Cook");
        cards.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.ORANGE, Color.lightGray));
        if (account_id == -1) {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, "Login");
        }
        return cards;
    }

    public JMenuBar createMenuBar() {
        final String[] menuItemManage = {"Cook", "Restock"};
        final String[] menuItemLogin = {"Logout"};
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMmenu = new JMenu("Manage");
        for (String text : menuItemManage) {
            JMenuItem jMenuItem = new JMenuItem(text);
            jMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) cards.getLayout();
                    cardLayout.show(cards, text);
                }
            });
            jMmenu.add(jMenuItem);
        }
        jMenuBar.add(jMmenu);
        jMmenu = new JMenu("Logout");
        for (String text : menuItemLogin) {
            JMenuItem jMenuItem = new JMenuItem(text);
            jMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) cards.getLayout();
                    cardLayout.show(cards, text);
                }
            });
            jMmenu.add(jMenuItem);
        }
        jMenuBar.add(jMmenu);
        return jMenuBar;
    }
}
