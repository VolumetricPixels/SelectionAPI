/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;


import org.spout.api.Spout;
import org.spout.api.entity.Player;

public class SelectionPlayer {
    
    private String name;
    private Selection sel;
    
    SelectionPlayer(String name) {
        this.name = name;
        this.sel = new Selection(name, getPlayer().getWorld());
    }
    
    public Selection getSelection() {
        if (!getPlayer().hasPermission("selections.select")) {
            return null;
        }
        return sel;
    }
    
    public boolean hasValidSelection() {
        return sel.getPos1() != null && sel.getPos2() != null && getPlayer().hasPermission("selections.select");
    }
    
    public String getName() {
        return name;
    }
    
    public Player getPlayer() {
        return Spout.getEngine().getPlayer(name, true);
    }
    
}
