/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

import java.io.File;
import java.util.logging.Logger;

import org.spout.api.Source;
import org.spout.api.Spout;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.block.BlockChangeEvent;
import org.spout.api.event.player.PlayerInteractEvent;
import org.spout.api.event.player.PlayerInteractEvent.Action;
import org.spout.api.event.player.PlayerJoinEvent;
import org.spout.api.geo.discrete.Point;
import org.spout.api.material.BlockMaterial;
import org.spout.api.material.block.BlockSnapshot;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.plugin.PluginDescriptionFile;
import org.spout.api.util.config.yaml.YamlConfiguration;

/**
 * SelectionAPI is a small, lightweight API for player region selections
 */
public class SelectionAPI extends CommonPlugin implements Listener {
    private short selectorId; // The ID of the item to be used for selections

    private Logger log;
    private PluginDescriptionFile pdf;
    private YamlConfiguration yConf;

    @Override
    public void onEnable() {
        // Variables
        log = getLogger();
        pdf = getDescription();

        // Config
        yConf = new YamlConfiguration(new File(getDataFolder(), "config.yml"));
        yConf.setWritesDefaults(true);
        selectorId = yConf.getNode("Selector-Tool-Id").getShort((short) 256);

        // Spout registrations
        Spout.getEventManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler(order = Order.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Point p = e.getInteractedPoint();
        // Check for anything that means we can't run selection management or we don't need to
        if (p == null || e.isCancelled() || e.isAir() || e.getHeldItem().getMaterial().getId() != selectorId || !player.hasPermission("selections.select")) {
            return;
        }

        Action a = e.getAction();
        Selection s = player.getExact(SelectionPlayer.class).getSelection();

        switch (a) {
            case LEFT_CLICK:
                // Set point 1
                s.setPos1(new Point(p.getWorld(), p.getBlockX(), p.getBlockY(), p.getBlockZ()));
                player.sendMessage(ChatStyle.GRAY, "Point 1: ", p.getWorld().getName(), ", ", p.getBlockX(), ", ", p.getBlockY(), ", ",
                        p.getBlockZ());
                break;
            case RIGHT_CLICK:
                // Set point 2
                s.setPos2(new Point(p.getWorld(), p.getBlockX(), p.getBlockY(), p.getBlockZ()));
                player.sendMessage(ChatStyle.GRAY, "Point 2: ", p.getWorld().getName(), ", ", p.getBlockX(), ", ", p.getBlockY(), ", ",
                        p.getBlockZ());
                break;
            default:
                return;
        }
    }

    //@EventHandler(order = Order.LATEST_IGNORE_CANCELLED)
    public void onBlockChange(BlockChangeEvent event) {
        BlockSnapshot snap = event.getSnapshot();
        if (snap.getMaterial() == BlockMaterial.AIR) {
            // It's a break!
            Source source = event.getSource();
            if (!(source instanceof Player)) {
                return; // Booooo!
            }
            // It's a player, let's continue!
            Point point = snap.getBlock(this).getPosition();
            Player player = (Player) source;
            SelectionPlayer c = player.getExact(SelectionPlayer.class);
            // TODO: Apparently there is no way to get the held item of a player without Vanilla depends. Looking into this
            // For now, this method isn't registered because I commented out the EventHandler line
        }
    }

    @EventHandler(order = Order.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        // Add component
        if (!e.getPlayer().hasExact(SelectionPlayer.class)) {
            e.getPlayer().add(SelectionPlayer.class);
        }
    }
}
