package me.nullchips.bukkitplace.commands;

import me.nullchips.bukkitplace.utils.ChatUtils;
import me.nullchips.bukkitplace.utils.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class SetHubSpawn implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    SettingsManager sm = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = cu.commandCheck(sender, "place.setspawn");

        if (p != null) {
            FileConfiguration config = sm.getConfig();

            config.set("hub-location.world", p.getLocation().getWorld().getName());
            config.set("hub-location.x", p.getLocation().getX());
            config.set("hub-location.y", p.getLocation().getY());
            config.set("hub-location.z", p.getLocation().getZ());
            config.set("hub-location.yaw", p.getLocation().getYaw());
            config.set("hub-location.pitch", p.getLocation().getPitch());

            sm.saveConfig();
            sm.setHubLocation(p.getLocation());

            p.sendMessage(ChatColor.GREEN + "The hub spawn has been set successfully!");

        }

        return true;
    }
}
