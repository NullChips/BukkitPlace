package me.nullchips.bukkitplace.commands;

import me.nullchips.bukkitplace.BukkitPlace;
import me.nullchips.bukkitplace.utils.ChatUtils;
import me.nullchips.bukkitplace.utils.SettingsManager;
import org.bukkit.ChatColor;
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
public class Hub implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    SettingsManager sm = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = cu.commandCheck(sender);

        if (p != null) {
            if (sm.getHubLocation() == null) {
                p.sendMessage(ChatColor.RED + "The hub spawn has not yet been set! Use /sethubspawn to set the hub spawn.");
                return true;
            }

            p.teleport(sm.getHubLocation());
            BukkitPlace.clearInventory(p);
        }

        return true;
    }

}
