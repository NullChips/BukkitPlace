package me.nullchips.bukkitplace.listeners;

import me.nullchips.bukkitplace.BukkitPlace;
import me.nullchips.bukkitplace.DrawingColour;
import me.nullchips.bukkitplace.utils.CooldownManager;
import me.nullchips.bukkitplace.utils.PlayerSettings;
import me.nullchips.bukkitplace.utils.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class PlayerJoin implements Listener {

    CooldownManager cm = CooldownManager.getInstance();
    SettingsManager sm = SettingsManager.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(cm.getCooldownPlayers().contains(e.getPlayer().getUniqueId())) {
            e.getPlayer().kickPlayer(ChatColor.RED + "Your cooldown has not yet ended. Please try again later!");
            return;
        }

        if(sm.getHubLocation() == null) {
            e.getPlayer().sendMessage(ChatColor.RED + "The hub spawn has not yet been set! Use /sethubspawn to set the hub spawn.");
        } else {
            e.getPlayer().teleport(sm.getHubLocation());
        }

        BukkitPlace.getPlayerColours().put(e.getPlayer().getUniqueId(), DrawingColour.BLACK);
        BukkitPlace.getPlayerSettings().add(new PlayerSettings(e.getPlayer().getUniqueId()));
        BukkitPlace.clearInventory(e.getPlayer());
        e.setJoinMessage("");
    }

}
