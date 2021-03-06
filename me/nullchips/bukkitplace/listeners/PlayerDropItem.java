package me.nullchips.bukkitplace.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class PlayerDropItem implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

}
