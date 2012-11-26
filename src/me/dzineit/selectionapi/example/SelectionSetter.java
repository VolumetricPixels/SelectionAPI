/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi.example;

import java.util.ArrayList;
import java.util.List;

import me.dzineit.selectionapi.Selection;
import me.dzineit.selectionapi.SelectionPlayer;

import org.spout.api.entity.Player;
import org.spout.api.geo.discrete.Point;
import org.spout.api.material.BlockMaterial;

/**
 * An example to show using the SelectionAPI to make an area block setter, with undos and redos
 */
public class SelectionSetter {
    private SelectionPlayer player;
    private List<SetterEdit> edits = new ArrayList<SetterEdit>();

    public SelectionSetter(Player player) {
        this.player = player.get(SelectionPlayer.class);
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
        for (int i = edits.size() - 1 - count; i < edits.size(); i++) {
            SetterEdit e = edits.get(i);
            if (e != null && !e.undone) {
                edits.get(i).undo();
            } else {
                i--;
            }
        }
    }

    public void redo() {
        redo(1);
    }

    public void redo(int count) {
        for (int i = count; i > 0; i--) {
            SetterEdit e = edits.get(i);
            if (e != null && e.undone) {
                e.redo();
            } else {
                i++;
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
            position = pos;
        }

        public BlockMaterial getMaterial() {
            return bm;
        }

        public Point getPosition() {
            return position;
        }
    }
}
