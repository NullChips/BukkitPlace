package me.nullchips.bukkitplace.listeners;

import me.nullchips.bukkitplace.DrawingColour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = e.getPlayer().getItemInHand();

            if(ChatColor.stripColor(handItem.getItemMeta().getDisplayName()).equalsIgnoreCase("pixel brush")) {
                e.getPlayer().sendMessage("[DEBUG] You have used the pixel brush.");
                //TODO Draw pixel.
                return;
            }

            if(ChatColor.stripColor(handItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Select colour")) {
                Inventory colourInv = Bukkit.createInventory(null, 18, "Select a colour:");
                int i = 0;

                for(DrawingColour dc: DrawingColour.values()) {
                    ItemStack newWool = new ItemStack(Material.WOOL, 1, dc.getDyeData());
                    ItemMeta newWoolMeta = newWool.getItemMeta();
                    newWoolMeta.setDisplayName(dc.getChatColor() + dc.getDisplayName());
                    newWool.setItemMeta(newWoolMeta);
                    colourInv.setItem(i, newWool);
                    i++;
                }

                e.getPlayer().openInventory(colourInv);

                return;
            }

        }

        //TODO Settings menu, pixel brush and stuff.
    }

}
