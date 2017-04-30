package me.nullchips.bukkitplace.listeners;

import me.nullchips.bukkitplace.utils.CooldownManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class PlayerQuit implements Listener {

    CooldownManager cm = CooldownManager.getInstance();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if(cm.getToBeKicked().contains(e.getPlayer())) {
            cm.getToBeKicked().remove(e.getPlayer().getUniqueId());

            cm.addOfflineCooldownPlayer(e.getPlayer().getUniqueId());
        }
    }

}
