/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

import org.spout.api.component.components.EntityComponent;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;

public class SelectionPlayer extends EntityComponent {
    private Selection selection;

    @Override
    public void onAttached() {
        Entity player = getOwner();

        if (player instanceof Player) {
            selection = new Selection(((Player) player).getName(), player.getWorld());
        } else {
            selection = new Selection(null, null);
            throw new IllegalStateException("Cannot assign SelectionPlayer component to non-player Entity!");
        }
    }

    public Selection getSelection() {
        return selection;
    }
}
