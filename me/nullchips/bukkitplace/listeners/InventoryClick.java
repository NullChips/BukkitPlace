package me.nullchips.bukkitplace.listeners;

import me.nullchips.bukkitplace.BukkitPlace;
import me.nullchips.bukkitplace.DrawingColour;
import me.nullchips.bukkitplace.utils.ChatUtils;
import me.nullchips.bukkitplace.utils.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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

    ChatUtils cu = ChatUtils.getInstance();

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

        if (e.getInventory().getName().equalsIgnoreCase("settings:")) {
            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
                ItemStack is = e.getCurrentItem();
                PlayerSettings ps = BukkitPlace.getPlayerSettings((Player) e.getWhoClicked());

                if (ps == null) {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "An error has occured. Please relog.");
                    return;
                }

                if (ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("hide players") ||
                        ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("show players")) {

                    if (BukkitPlace.getSettingsCooldown().contains(e.getWhoClicked().getUniqueId())) {
                        e.getWhoClicked().sendMessage(ChatColor.RED + "Please wait to use this again.");
                        return;
                    }

                    if (ps.isShowingPlayers()) {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            ((Player) e.getWhoClicked()).hidePlayer(p);
                        }

                        cu.message((Player) e.getWhoClicked(), ChatColor.GOLD + "You have hidden all players.");
                        ps.setShowPlayers(false);
                        e.getWhoClicked().closeInventory();
                        BukkitPlace.addToSettingsCooldown((Player) e.getWhoClicked());
                        return;

                    } else {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            ((Player) e.getWhoClicked()).showPlayer(p);
                        }

                        cu.message((Player) e.getWhoClicked(), ChatColor.GOLD + "You have shown all players.");
                        ps.setShowPlayers(true);
                        e.getWhoClicked().closeInventory();
                        BukkitPlace.addToSettingsCooldown((Player) e.getWhoClicked());
                        return;
                    }
                }

                if (ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("hide chat") ||
                        ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("show chat")) {
                    if (ps.isShowingChat()) {
                        cu.message((Player) e.getWhoClicked(), ChatColor.GOLD + "You have hidden chat.");
                        ps.setShowChat(false);
                        e.getWhoClicked().closeInventory();
                        BukkitPlace.addToSettingsCooldown((Player) e.getWhoClicked());
                        return;
                    } else {
                        cu.message((Player) e.getWhoClicked(), ChatColor.GOLD + "You have shown chat.");
                        ps.setShowChat(true);
                        e.getWhoClicked().closeInventory();
                        BukkitPlace.addToSettingsCooldown((Player) e.getWhoClicked());
                        return;
                    }
                }

                if(ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("close")) {
                    e.getWhoClicked().closeInventory();
                }
            }
        }
    }
}
