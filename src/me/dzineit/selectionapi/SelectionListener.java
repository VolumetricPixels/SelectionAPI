/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;


import org.spout.api.chat.style.ChatStyle;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.player.PlayerInteractEvent;
import org.spout.api.event.player.PlayerInteractEvent.Action;
import org.spout.api.geo.discrete.Point;

public class SelectionListener implements Listener {
    
    private short selectorId;
    
    SelectionListener(short selectorId) {
        this.selectorId = selectorId;
    }
    
    @EventHandler(order = Order.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.isCancelled() || e.isAir() || e.getHeldItem().getMaterial().getId() != selectorId || !e.getPlayer().hasPermission("selections.select")) {
            return;
        }
        
        Action a = e.getAction();
        Selection s = SelectionPlayerManager.getSelectionPlayer(e.getPlayer().getName()).getSelection();
        Point p = e.getInteractedPoint();
        
        switch (a) {            
            case LEFT_CLICK:
                s.setPos1(new SelectionPoint(p.getBlockX(), p.getBlockY(), p.getBlockZ()));
                e.getPlayer().sendMessage(ChatStyle.GRAY, "Point 1: ", p.getWorld().getName(), ", ", p.getBlockX(), ", " , p.getBlockY(), ", ", p.getBlockZ());
            case RIGHT_CLICK:
                s.setPos2(new SelectionPoint(p.getBlockX(), p.getBlockY(), p.getBlockZ()));
                e.getPlayer().sendMessage(ChatStyle.GRAY, "Point 2: ", p.getWorld().getName(), ", ", p.getBlockX(), ", " , p.getBlockY(), ", ", p.getBlockZ());
            default:
                return;
        }
    }
    
}
