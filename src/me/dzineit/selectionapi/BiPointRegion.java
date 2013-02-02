/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class BiPointRegion {
    private World world;
    private Point pos1;
    private Point pos2;

    public BiPointRegion(World w) {
        world = w;
    }

    public World getWorld() {
        return world;
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
        world = w;
    }

    public Point getMinPoint() {
        return new Point(getWorld(), min(pos1.getX(), pos2.getX()), min(pos1.getY(), pos2.getY()), min(pos1.getZ(), pos2.getZ()));
    }

    public Point getMaxPoint() {
        return new Point(getWorld(), max(pos1.getX(), pos2.getX()), max(pos1.getY(), pos2.getY()), max(pos1.getZ(), pos2.getZ()));
    }

    public boolean isValid() {
        return getPos1() != null && getPos2() != null && pos1.getWorld() == pos2.getWorld();
    }
}
