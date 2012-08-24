/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;


import org.spout.api.Spout;
import org.spout.api.entity.Player;
import org.spout.api.geo.World;

public class Selection {
    
    private World world;
    private String ownerName;
    private SelectionPoint pos1;
    private SelectionPoint pos2;
    
    Selection(String owner, World w) {
        this.world = w;
    }
    
    public Player getOwner() {
        return Spout.getEngine().getPlayer(getOwnerName(), true);
    }
    
    public World getWorld() {
        return world;
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
    
    public void setWorld(World w) {
        this.world = w;
    }
    
    public SelectionPoint getMinPoint() {
        return new SelectionPoint(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
    }
    
    public SelectionPoint getMaxPoint() {
        return new SelectionPoint(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
    }
    
}
