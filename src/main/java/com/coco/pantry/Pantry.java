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

    public Pantry() {
        GUIHelper gUIHelper = new GUIHelper();
        window = gUIHelper.initComponents();
    }

    public static void main(String[] args) {
        Pantry pantry = new Pantry();
    }
}
