package me.nullchips.bukkitplace.listeners;

import me.nullchips.bukkitplace.BukkitPlace;
import me.nullchips.bukkitplace.DrawingColour;
import me.nullchips.bukkitplace.events.PlayerPlacePixelEvent;
import me.nullchips.bukkitplace.utils.CooldownManager;
import me.nullchips.bukkitplace.utils.PlayerSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Set;

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

    CooldownManager cm = CooldownManager.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = e.getPlayer().getItemInHand();

            if (ChatColor.stripColor(handItem.getItemMeta().getDisplayName()).equalsIgnoreCase("pixel brush") &&
                    !cm.getToBeKicked().contains(e.getPlayer().getUniqueId()) &&
                    !cm.getCooldownPlayers().contains(e.getPlayer().getUniqueId())) {

                Block target = e.getPlayer().getTargetBlock((Set<Material>) null, 10);

                if (target == null || target.getType() != Material.WOOL) {
                    e.getPlayer().sendMessage(ChatColor.RED + "You are not currently looking at a wool block!");
                }

                DrawingColour dc = BukkitPlace.getPlayerColours().get(e.getPlayer().getUniqueId());

                Bukkit.getServer().getPluginManager().callEvent(new PlayerPlacePixelEvent(e.getPlayer()));
                target.setData(dc.getDyeData());
                cm.addToCooldown(e.getPlayer());

                return;
            }

            if (ChatColor.stripColor(handItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Select colour")) {
                Inventory colourInv = Bukkit.createInventory(null, 18, "Select a colour:");
                int i = 0;

                for (DrawingColour dc : DrawingColour.values()) {
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

            if (ChatColor.stripColor(handItem.getItemMeta().getDisplayName()).equalsIgnoreCase("settings")) {
                PlayerSettings ps = BukkitPlace.getPlayerSettings(e.getPlayer());

                if (ps == null) {
                    e.getPlayer().sendMessage(ChatColor.RED + "An error has occured. Please relog.");
                    return;
                }

                Inventory settingsInv = Bukkit.createInventory(null, 9, "Settings:");

                ItemStack playerOption = new ItemStack(Material.SKULL_ITEM);
                ItemMeta playerOptionMeta = playerOption.getItemMeta();

                if(ps.isShowingPlayers()) {
                    playerOptionMeta.setDisplayName(ChatColor.GOLD + "Hide Players");
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "Showing players currently:");
                    lore.add(ChatColor.GREEN + "ENABLED.");
                    playerOptionMeta.setLore(lore);
                    playerOption.setItemMeta(playerOptionMeta);
                } else {
                    playerOptionMeta.setDisplayName(ChatColor.GOLD + "Show Players");
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "Showing players currently:");
                    lore.add(ChatColor.RED + "DISABLED.");
                    playerOptionMeta.setLore(lore);
                    playerOption.setItemMeta(playerOptionMeta);
                }

                ItemStack chatOption = new ItemStack(Material.BOOK);
                ItemMeta chatOptionMeta = chatOption.getItemMeta();

                if(ps.isShowingChat()) {
                    chatOptionMeta.setDisplayName(ChatColor.GOLD + "Hide Chat");
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "Showing chat is currently:");
                    lore.add(ChatColor.GREEN + "ENABLED.");
                    chatOptionMeta.setLore(lore);
                    chatOption.setItemMeta(chatOptionMeta);
                } else {
                    chatOptionMeta.setDisplayName(ChatColor.GOLD + "Show Chat");
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "Showing chat is currently:");
                    lore.add(ChatColor.RED + "DISABLED.");
                    chatOptionMeta.setLore(lore);
                    chatOption.setItemMeta(chatOptionMeta);
                }

                ItemStack closeItem = new ItemStack(Material.BARRIER);
                ItemMeta closeItemMeta = closeItem.getItemMeta();

                closeItemMeta.setDisplayName(ChatColor.RED + "Close");
                closeItem.setItemMeta(closeItemMeta);

                settingsInv.setItem(3, playerOption);
                settingsInv.setItem(5, chatOption);
                settingsInv.setItem(8, closeItem);

                e.getPlayer().openInventory(settingsInv);

            }

        }
    }

}
