/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

public abstract class Selection {
    
    private String ownerName;
    private SelectionPoint pos1;
    private SelectionPoint pos2;

    public Selection(String owner) {
        this.ownerName = owner;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public SelectionPoint getPos1() {
        return pos1;
    }
    
    public SelectionPoint getPos2() {
        return pos2;
    }
    
    public void setPos1(SelectionPoint pos1) {
        this.pos1 = pos1;
    }
    
    public void setPos2(SelectionPoint pos2) {
        this.pos2 = pos2;
    }
    
    public SelectionPoint getMinPoint() {
        return new SelectionPoint(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
    }
    
    public SelectionPoint getMaxPoint() {
        return new SelectionPoint(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
    }
    
}
