/*
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 */
package com.coco.pantry;

/**
 *
 * @author Coco
 */
public class PreparedParameter {

    public Object value;
    public int type;

    public PreparedParameter(Object value, int type) {
        this.value = value;
        this.type = type;
    }
}
