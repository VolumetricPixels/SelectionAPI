/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

import java.io.File;

import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.Command;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandExecutor;
import org.spout.api.command.CommandSource;
import org.spout.api.entity.Player;
import org.spout.api.event.Cause;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.block.BlockChangeEvent;
import org.spout.api.event.player.PlayerInteractEvent;
import org.spout.api.event.player.PlayerJoinEvent;
import org.spout.api.exception.CommandException;
import org.spout.api.geo.discrete.Point;
import org.spout.api.plugin.CommonPlugin;

/**
 * SelectionAPI is a small, lightweight API for player region selections
 */
public class SelectionAPI extends CommonPlugin implements Listener, CommandExecutor {
	@Override
	public void onEnable() {
		// Spout registrations
		getEngine().getEventManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void processCommand(CommandSource source, Command cmd, CommandContext context) throws CommandException {
		int a = -1;
		boolean b = false;
		if (!(source instanceof Player)) {
			throw new CommandException("Only players can make selections!");
		}
		if (cmd.getPreferredName().equalsIgnoreCase("pos1")) {
			a = 1;
			if (context.length() > 0) {
				String arg1 = context.getString(0);
				if (arg1.equalsIgnoreCase("here")) {
					b = true;
				} else {
					throw new CommandException("Unrecognised subcommand!");
				}
			} else {
				source.sendMessage(ChatStyle.GRAY, "Click a block to set position 1...");
			}
		} else if (cmd.getPreferredName().equalsIgnoreCase("pos2")) {
			a = 2;
			if (context.length() > 0) {
				String arg1 = context.getString(0);
				if (arg1.equalsIgnoreCase("here")) {
					b = true;
				} else {
					throw new CommandException("Unrecognised subcommand!");
				}
			} else {
				source.sendMessage(ChatStyle.GRAY, "Click a block to set position 2...");
			}
		}
		SelectionPlayer p = ((Player) source).get(SelectionPlayer.class);
		if (b) {
			p.getSelection().setPos(a, p.getOwner().getScene().getPosition());
		} else {
			p.setSelecting(a);
		}
	}

	@EventHandler(order = Order.LATEST_IGNORE_CANCELLED)
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Point p = e.getInteractedPoint();
		// Check permissions
		if (!player.hasPermission("selectionapi.select")) {
			return;
		}
		SelectionPlayer sp = player.get(SelectionPlayer.class);
		if (sp.getSelecting() > 0 && sp.getSelecting() < 3) {
			Selection selection = sp.getSelection();
			selection.setPos(sp.getSelecting(), p);
			e.setCancelled(true);
			player.sendMessage("Set position " + sp.getSelecting() + " to " + p.getBlockX() + ", " + p.getBlockY() + ", " + p.getBlockZ());
			sp.setSelecting(0);
		}
	}

	@EventHandler(order = Order.LATEST_IGNORE_CANCELLED)
	public void onBlockChange(BlockChangeEvent event) {
		Cause<?> cause = event.getCause();
		Object source = cause.getSource();
		if (!(source instanceof Player)) {
			return;
		}
		Player player = (Player) source;
		Point p = event.getBlock().getPosition();
		// Check permissions
		if (!player.hasPermission("selectionapi.select")) {
			return;
		}
		SelectionPlayer sp = player.get(SelectionPlayer.class);
		if (sp.getSelecting() > 0 && sp.getSelecting() < 3) {
			Selection selection = sp.getSelection();
			selection.setPos(sp.getSelecting(), p);
			event.setCancelled(true);
			player.sendMessage("Set position " + sp.getSelecting() + " to " + p.getBlockX() + ", " + p.getBlockY() + ", " + p.getBlockZ());
			sp.setSelecting(0);
		}
	}

	@EventHandler(order = Order.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
		// Add SelectionPlayer component for selections
		e.getPlayer().add(SelectionPlayer.class);
	}
}
