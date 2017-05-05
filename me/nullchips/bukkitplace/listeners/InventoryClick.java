package me.nullchips.bukkitplace.listeners;

import me.nullchips.bukkitplace.BukkitPlace;
import me.nullchips.bukkitplace.DrawingColour;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getInventory().getName().equalsIgnoreCase("Select a colour:")) {
            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
                for (DrawingColour dc : DrawingColour.values()) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(dc.getChatColor() + dc.getDisplayName())) {
                        BukkitPlace.getPlayerColours().put(e.getWhoClicked().getUniqueId(), dc);
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().sendMessage(ChatColor.GOLD + "You have selected the colour: " + dc.getChatColor() + dc.getDisplayName());
                        BukkitPlace.clearInventory((Player) e.getWhoClicked());
                        BukkitPlace.giveStartingItems((Player) e.getWhoClicked());
                    }
                }
            }
        }
    }
}
