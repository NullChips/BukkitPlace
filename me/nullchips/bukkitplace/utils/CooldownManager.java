package me.nullchips.bukkitplace.utils;

import me.nullchips.bukkitplace.BukkitPlace;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
public class CooldownManager {

    private static CooldownManager ourInstance = new CooldownManager();

    public static CooldownManager getInstance() {
        return ourInstance;
    }

    private CooldownManager() {
        cooldownPlayers = new ArrayList<>();
        toBeKicked = new ArrayList<>();
    }

    private ArrayList<UUID> cooldownPlayers;
    private ArrayList<UUID> toBeKicked;

    public ArrayList<UUID> getCooldownPlayers() {
        return cooldownPlayers;
    }

    public ArrayList<UUID> getToBeKicked() {
        return toBeKicked;
    }

    SettingsManager sm = SettingsManager.getInstance();
    ChatUtils cu = ChatUtils.getInstance();

    public void addToCooldown(Player p) {
        if (!p.hasPermission("place.ignorecooldown")) {
            cu.message(p, "You have been added to the cooldown. You will be kicked in 10 seconds and will not be able to join back until your cooldown is over.");

            toBeKicked.add(p.getUniqueId());

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BukkitPlace.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (toBeKicked.contains(p.getUniqueId())) {
                        p.kickPlayer(ChatColor.RED + "You have placed a pixel! You will be able to join back once the cooldown is over.");
                        toBeKicked.remove(p.getUniqueId());

                        addOfflineCooldownPlayer(p.getUniqueId());
                    }
                }
            }, 200);
        }
    }

    public void addOfflineCooldownPlayer(UUID uuid) {
        cooldownPlayers.add(uuid);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BukkitPlace.getInstance(), new Runnable() {
            @Override
            public void run() {
                cooldownPlayers.remove(uuid);
            }
        }, sm.getCooldownTime());
        //TODO Change cooldown time via config.
    }

}
