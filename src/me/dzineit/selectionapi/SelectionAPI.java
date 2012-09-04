/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import me.dzineit.selectionapi.SelectionPlayer.Selection;

import org.spout.api.Spout;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.player.PlayerInteractEvent;
import org.spout.api.event.player.PlayerInteractEvent.Action;
import org.spout.api.geo.discrete.Point;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.plugin.PluginDescriptionFile;
import org.spout.api.util.config.yaml.YamlConfiguration;

public class SelectionAPI extends CommonPlugin implements Listener {
    
    private short selectorId;
    
    private Logger log;
    private PluginDescriptionFile pdf;
    private YamlConfiguration yConf;

    @Override
    public void onEnable() {
        this.log = getLogger();
        this.pdf = getDescription();
        this.yConf = new YamlConfiguration(new File(getDataFolder(), "config.yml"));
        
        yConf.setWritesDefaults(true);
        Spout.getEventManager().registerEvents(this, this);
        selectorId = yConf.getNode("Selector-Tool-Id").getShort((short) 256);
        
        log.info("[SelectorAPI] v" + pdf.getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        log.info("[SelectorAPI] v" + pdf.getVersion() + " disabled!");
    }
    
    @EventHandler(order = Order.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.isCancelled() || e.isAir() || e.getHeldItem().getMaterial().getId() != selectorId || !e.getPlayer().hasPermission("selections.select")) {
            return;
        }
        
        Action a = e.getAction();
        Selection s = getSelectionPlayer(e.getPlayer().getName()).getSelection();
        Point p = e.getInteractedPoint();
        
        switch (a) {
            case LEFT_CLICK:
                s.setPos1(new Point(p.getWorld(), p.getBlockX(), p.getBlockY(), p.getBlockZ()));
                e.getPlayer().sendMessage(ChatStyle.GRAY, "Point 1: ", p.getWorld().getName(), ", ", p.getBlockX(), ", " , p.getBlockY(), ", ", p.getBlockZ());
            case RIGHT_CLICK:
                s.setPos2(new Point(p.getWorld(), p.getBlockX(), p.getBlockY(), p.getBlockZ()));
                e.getPlayer().sendMessage(ChatStyle.GRAY, "Point 2: ", p.getWorld().getName(), ", ", p.getBlockX(), ", " , p.getBlockY(), ", ", p.getBlockZ());
            default:
                return;
        }
    }
    
    private static Map<String, SelectionPlayer> players = new HashMap<String, SelectionPlayer>();
    
    public static SelectionPlayer getSelectionPlayer(String name) {
        if (!players.containsKey(name)) {
            players.put(name, new SelectionPlayer(name));
        }
        return players.get(name);
    }
    
}
