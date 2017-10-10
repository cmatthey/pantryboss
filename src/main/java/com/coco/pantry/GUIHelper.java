/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Coco
 */
public class GUIHelper {

    public static final int ROW_COUNT = 3;
    public static final int COL_COUNT = 3;
    private int account_id;

    public GUIHelper(int account_id) {
        this.account_id = account_id;
    }

    public JFrame initWindow(String title, int width, int height) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(width, height);
        window.setTitle(title);
        window.setContentPane(cardsPanel());
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
        window.setContentPane(cardsPanel());
        return window;
    }

    public JPanel welcomePanel() {
        JPanel card = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel("Welcome", SwingConstants.CENTER);
        JButton jButton = new JButton("Login");
        card.add(jLabel, BorderLayout.CENTER);
        card.add(jButton, BorderLayout.SOUTH);
        return card;
    }

    public JPanel recipePanel() {
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

    public JPanel inventoryPanel() {
        InventoryTableModel inventoryTableModel = new InventoryTableModel(account_id);
//        InventoryTableController inventoryTableController = new InventoryTableController(this);
        JPanel card = new JPanel(new BorderLayout());
        // TODO: get data from model
        JTable jTable = new JTable(inventoryTableModel);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        card.add(jScrollPane, BorderLayout.CENTER);

        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel selectModel = (ListSelectionModel) e.getSource();
                // TODO: Question: How to update value here?
            }
        });
        return card;
    }

    public JPanel cardsPanel() {
        JPanel cards = new JPanel(new CardLayout());
        cards.add(inventoryPanel(), "Stock");
        cards.add(recipePanel(), "Cook");
        cards.add(welcomePanel(), "Account");

        cards.setBorder(BorderFactory.createLineBorder(Color.red, 10));
//        card1.setBorder(BorderFactory.createLineBorder(Color.green, 30));
//        card2.setBorder(BorderFactory.createLineBorder(Color.blue, 70));
//        CardLayout c1 = (CardLayout) cards.getLayout();
//        c1.show(cards, "Stock");

        return cards;
    }
}
