package me.nullchips.bukkitplace.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class ChatUtils {

    private ChatUtils() {

    }

    private static ChatUtils instance;

    public static ChatUtils getInstance() {
        if (instance == null) {
            instance = new ChatUtils();
        }
        return instance;
    }

    private String starter;

    public String getStarter() {
        return starter;
    }

    public void broadcastMessage(String message) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(starter + " " + ChatColor.RESET + ChatColor.WHITE + message);
        }
    }

    public void message(Player p, String message) {
        p.sendMessage(starter + " " + ChatColor.RESET + ChatColor.WHITE + message);
    }

    public void noPermission(Player p) {
        p.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
    }

    public Player commandCheck(CommandSender sender) {
        if (sender instanceof Player) {
            return (Player) sender;
        }

        sender.sendMessage(ChatColor.RED + "The console cannot use this command.");

        return null;
    }

    public Player commandCheck(CommandSender sender, String permission) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (!p.hasPermission(permission)) {
                noPermission(p);
                return null;
            }

            return p;
        }

        sender.sendMessage(ChatColor.RED + "The console cannot use this command.");

        return null;
    }

}
