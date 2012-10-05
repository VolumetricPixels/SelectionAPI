/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi.example;

import java.util.ArrayList;
import java.util.List;

import org.spout.api.entity.Player;
import org.spout.api.geo.discrete.Point;
import org.spout.api.material.BlockMaterial;

import me.dzineit.selectionapi.Selection;
import me.dzineit.selectionapi.SelectionComponent;

/**
 * An example to show using the SelectionAPI to make an area block setter.
 * Probably not a good idea to use this as there is no undo function.
 */
public class SelectionSetter {
    private SelectionComponent player;
    private List<SetterEdit> edits = new ArrayList<SetterEdit>();

    public SelectionSetter(Player player) {
        this.player = player.get(SelectionComponent.class);
    }

    /**
     * Set given player's Selection to given block type
     * 
     * @param player
     *            The player to use the Selection of
     * @param blockId
     *            The block ID to set the Selection to
     */
    public void setAllBlocks(short blockId) {
        Selection s = player.getSelection();
        Point min = s.getMinPoint();
        Point max = s.getMaxPoint();

        float minX = min.getX();
        float maxX = max.getX();
        float minY = min.getY();
        float maxY = max.getY();
        float minZ = min.getZ();
        float maxZ = max.getZ();

        /*
         * Loop through all positions in the user's selection and set them all
         * to the given block ID for the method.
         */
        for (float x = minX; x <= maxX; ++x) {
            for (float y = minY; y <= maxY; ++y) {
                for (float z = minZ; z <= maxZ; ++z) {
                    s.getWorld().setBlockMaterial((int) x, (int) y, (int) z, BlockMaterial.get(blockId), (short) 0, s.getWorld());
                }
            }
        }
    }

    public void undo() {
        undo(1);
    }

    public void undo(int count) {
        for (int i = ((edits.size() - 1) - count); i < edits.size(); i++) {
            if (edits.get(i) != null) {
                edits.get(i).undo();
            }
        }
    }

    public void redo() {
        redo(1);
    }

    public void redo(int count) {
        for (SetterEdit edit : edits) {
            if (edit.undone == false) {
                continue;
            }
            edit.redo();
            count--;
            if (count == 0) {
                break;
            }
        }
    }

    private class SetterEdit {
        private boolean undone = false;
        private List<BlockData> before;
        private List<BlockData> after;

        private SetterEdit(List<BlockData> before, List<BlockData> after) {
            this.before = before;
            this.after = after;
        }

        public void undo() {
            if (undone == false) {
                doUndo(false);
            }
        }

        public void redo() {
            if (undone) {
                doUndo(true);
            }
        }

        private void doUndo(boolean redo) {
            if (undone && !redo || !undone && redo) {
                return;
            }
            for (int i = 0; i < after.size(); i++) {
                Point p = after.get(i).getPosition();
                p.getWorld().setBlockMaterial(p.getBlockX(), p.getBlockY(), p.getBlockZ(), before.get(i).getMaterial(), (short) 0, p.getWorld());
                BlockData h = after.get(i);
                after.set(i, before.get(i));
                before.set(i, h);
            }

            List<BlockData> afterbck = after;
            after = before;
            before = afterbck;

            undone = !redo;
        }
    }

    private static class BlockData {
        private BlockMaterial bm;
        private Point position;

        private BlockData(BlockMaterial bm, Point pos) {
            this.bm = bm;
            this.position = pos;
        }

        public BlockMaterial getMaterial() {
            return bm;
        }

        public Point getPosition() {
            return position;
        }
    }
}
