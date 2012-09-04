/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

import org.spout.api.Server;
import org.spout.api.Spout;
import org.spout.api.entity.Player;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;

public class SelectionPlayer {
    
    private String name;
    private Selection sel;
    
    protected SelectionPlayer(String name) {
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
    
    public boolean isValidPlayer() {
        return getPlayer() != null;
    }
    
    public String getName() {
        return name;
    }
    
    public Player getPlayer() {
        return ((Server) Spout.getEngine()).getPlayer(name, true);
    }
    
    public class Selection {
        
        private World world;
        private String ownerName;
        private Point pos1;
        private Point pos2;
        
        private Selection(String owner, World w) {
            this.world = w;
        }
        
        public Player getOwner() {
            return ((Server) Spout.getEngine()).getPlayer(getOwnerName(), true);
        }
        
        public World getWorld() {
            return world;
        }
        
        public String getOwnerName() {
            return ownerName;
        }
        
        public Point getPos1() {
            return pos1;
        }
        
        public Point getPos2() {
            return pos2;
        }
        
        public void setPos1(Point pos1) {
            this.pos1 = pos1;
            if (pos2.getWorld() != pos1.getWorld()) {
                pos2 = null;
            }
        }
        
        public void setPos2(Point pos2) {
            this.pos2 = pos2;
            if (pos2.getWorld() != pos1.getWorld()) {
                pos1 = null;
            }
        }
        
        public void setWorld(World w) {
            this.world = w;
        }
        
        public Point getMinPoint() {
            return new Point(getWorld(), Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
        }
        
        public Point getMaxPoint() {
            return new Point(getWorld(), Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
        }
        
    }
    
}
