package me.nullchips.bukkitplace.commands;

import me.nullchips.bukkitplace.BukkitPlace;
import me.nullchips.bukkitplace.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
public class Join implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = cu.commandCheck(sender);

        if (p != null) {
            if(BukkitPlace.getPlaceWorld() == null) {
                p.sendMessage(ChatColor.RED + "The canvas has not yet been loaded!");
                return true;
            }
            p.teleport(new Location(Bukkit.getServer().getWorld("BukkitPlaceWorld"), 0, 2, 0));
        }

        return true;
    }
}
