/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Coco
 */
public class GUIHelper {

    public static final int ROW_COUNT = 3;
    public static final int COL_COUNT = 3;
    private int account_id;
    private InventoryTableController inventoryTableController;
    private JPanel cards;

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
        JPanel card = new JPanel(new BorderLayout());
        // TODO: complete welcome panel
        JTextField usernameTextField = new JTextField("Username");
//        JLabel jLabel = new JLabel("Welcome", SwingConstants.CENTER);
        JButton jButton = new JButton("Login");
        card.add(usernameTextField, BorderLayout.CENTER);
        card.add(jButton, BorderLayout.SOUTH);
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
        cards.add(createInventoryPanel(), "Restock");
        cards.add(createRecipePanel(), "Cook");
        cards.add(crateWelcomePanel(), "Logout");
        cards.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.ORANGE, Color.lightGray));
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
