/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

public class SelectionPoint {

    private int x;
    private int y;
    private int z;
    
    public SelectionPoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public SelectionPoint() {
        this(0, 0, 0);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getZ() {
        return z;
    }
    
    public boolean isInside(SelectionPoint min, SelectionPoint max) {
        return x >= min.x && x <= max.x && y >= min.y && y <= max.y && z >= min.z && z <= max.z;
    }

    @Override
    public String toString() {
        return "SelectionPoint [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
    
}
