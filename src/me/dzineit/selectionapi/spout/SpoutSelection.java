/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi.spout;

import me.dzineit.selectionapi.Selection;

import org.spout.api.Spout;
import org.spout.api.entity.Player;
import org.spout.api.geo.World;

public class SpoutSelection extends Selection {
    
    private World world;
    
    SpoutSelection(String owner, World w) {
        super(owner);
        this.world = w;
    }
    
    public Player getOwner() {
        return Spout.getEngine().getPlayer(getOwnerName(), true);
    }
    
    public void setWorld(World w) {
        this.world = w;
    }
    
    public World getWorld() {
        return world;
    }
    
}
