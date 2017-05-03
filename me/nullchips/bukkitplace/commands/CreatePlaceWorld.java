package me.nullchips.bukkitplace.commands;

import me.nullchips.bukkitplace.utils.ChatUtils;
import me.nullchips.bukkitplace.utils.PlaceWorldGenerator;
import me.nullchips.bukkitplace.utils.SettingsManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class CreatePlaceWorld implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();
    SettingsManager sm = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = cu.commandCheck(sender, "place.createworld");

        if (p != null) {
            p.sendMessage(ChatColor.GREEN + "Creating canvas world. This will override and clear any previous canvas worlds you had.");
            p.sendMessage(ChatColor.RED + "This can take some time, expect some lag.");

            for (Player target : Bukkit.getServer().getOnlinePlayers()) {
                if (target.getWorld().getName().equalsIgnoreCase("BukkitPlaceWorld")) {
                    target.sendMessage("The BukkitPlace canvas is being refreshed. You have been kicked to the lobby.");
                    target.teleport(sm.getHubLocation());
                }
            }

            Bukkit.getServer().unloadWorld("BukkitPlaceWorld", true);

            File worldFiles = new File("BukkitPlaceWorld/");

            if (worldFiles.exists()) {
                Bukkit.getLogger().info("BukkitPlaceWorld folder is being deleted.");
                worldFiles.delete();
            }

            World world = Bukkit.createWorld(new WorldCreator("BukkitPlaceWorld").environment(World.Environment.NORMAL).generator(new PlaceWorldGenerator()));

            int radius = sm.getCanvasRadius();

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    world.getBlockAt(x, 0, z).setType(Material.WOOL);
                }
            }

            p.sendMessage(ChatColor.GREEN + "The canvas generation has finished!");
        }

        return true;
    }
}
