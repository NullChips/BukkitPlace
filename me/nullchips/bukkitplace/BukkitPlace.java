package me.nullchips.bukkitplace;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class BukkitPlace extends JavaPlugin {

    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static Player getPlayerFromUUID(UUID uuid) {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(p.getUniqueId().equals(uuid)) {
                return p;
            }
        }
        return null;
    }

    public static Player getPlayerFromUUID(String uuid) {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(p.getUniqueId().toString().equals(uuid)) {
                return p;
            }
        }
        return null;
    }

}
