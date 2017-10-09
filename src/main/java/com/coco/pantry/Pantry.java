/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

import javax.swing.JFrame;

/**
 *
 * @author Coco
 */
public class Pantry {

    private JFrame window;
    private int account_id;

    public Pantry(int account_id) {
        this.account_id = account_id;
        GUIHelper gUIHelper = new GUIHelper(account_id);
        window = gUIHelper.initComponents();
    }

    public static void main(String[] args) {
        Pantry pantry = new Pantry(1);
        //TODO: remove hard coded account_id
    }
}
