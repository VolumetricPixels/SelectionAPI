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

public class Selection extends BiPointRegion {
    private String ownerName;

    public Selection(String owner, World w) {
        super(w);
        this.ownerName = owner;
    }

    public Player getOwner() {
        return Spout.getEngine().getPlayer(getOwnerName(), true);
    }

    public String getOwnerName() {
        return ownerName;
    }

}
