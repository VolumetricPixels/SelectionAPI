/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi.example;

import org.spout.api.geo.discrete.Point;
import org.spout.api.material.BlockMaterial;

import me.dzineit.selectionapi.SelectionAPI;
import me.dzineit.selectionapi.SelectionPlayer.Selection;

/**
 * An example to show using the SelectionAPI to make an area block setter.
 * Probably not a good idea to use this as there is no undo function.
 */
public class SelectionSetter {
    
    /**
     * Set given player's Selection to given block type
     * @param player The player to use the Selection of
     * @param blockId The block ID to set the Selection to
     */
    public void setAllBlocks(String player, short blockId) {
        Selection s = SelectionAPI.getSelectionPlayer(player).getSelection();
        Point min = s.getMinPoint();
        Point max = s.getMaxPoint();
        
        float minX = min.getX();
        float maxX = max.getX();
        float minY = min.getY();
        float maxY = max.getY();
        float minZ = min.getZ();
        float maxZ = max.getZ();
        
        /*
         * Loop through all positions in the user's selection and set
         * them all to the given block ID for the method.
         */
        for (float x = minX; x <= maxX; ++x) {
            for (float y = minY; y <= maxY; ++y) {
                for (float z = minZ; z <= maxZ; ++z) {
                    s.getWorld().setBlockMaterial((int) x, (int) y, (int) z, BlockMaterial.get(blockId), (short) 0, s.getWorld());
                }
            }
        }
    }
    
}
