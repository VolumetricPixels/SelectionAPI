/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi.example;

import org.spout.api.material.BlockMaterial;

import me.dzineit.selectionapi.Selection;
import me.dzineit.selectionapi.SelectionPlayerManager;
import me.dzineit.selectionapi.SelectionPoint;

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
        Selection s = SelectionPlayerManager.getSelectionPlayer(player).getSelection();
        SelectionPoint min = s.getMinPoint();
        SelectionPoint max = s.getMaxPoint();
        
        int minX = min.getX();
        int maxX = max.getX();
        int minY = min.getY();
        int maxY = max.getY();
        int minZ = min.getZ();
        int maxZ = max.getZ();
        
        /*
         * Loop through all positions in the user's selection and set
         * them all to the given block ID for the method.
         */
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    s.getWorld().setBlockMaterial(x, y, z, BlockMaterial.get(blockId), (short) 0, s.getWorld());
                }
            }
        }
    }
    
}
