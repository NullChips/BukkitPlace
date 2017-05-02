package me.nullchips.bukkitplace;

import me.nullchips.bukkitplace.commands.CreatePlaceWorld;
import me.nullchips.bukkitplace.commands.Join;
import me.nullchips.bukkitplace.listeners.*;
import me.nullchips.bukkitplace.utils.PlaceWorldGenerator;
import me.nullchips.bukkitplace.utils.SettingsManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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

    private SettingsManager sm;

    private static ConcurrentHashMap<UUID, DrawingColour> playerColours;
    private static World placeWorld;

    @Override
    public void onEnable() {
        instance = this;

        sm = SettingsManager.getInstance();

        playerColours = new ConcurrentHashMap<>();

        File worldFiles = new File("BukkitPlaceWorld/level.dat");
        if (worldFiles.exists()) {
            Bukkit.getServer().getLogger().info(ChatColor.GREEN + "[BukkitPlace] BukkitPlace canvas world found sucessfully.");
            placeWorld = Bukkit.getServer().createWorld(new WorldCreator("BukkitPlaceWorld"));
        } else {
            Bukkit.getServer().getLogger().info(ChatColor.RED + "[BukkitPlace] Could not find BukkitPlace canvas world. Use /createplaceworld to create the canvas.");
        }

        PlaceWorldGenerator.setInstance(this);

        //Register Events.
        registerEvents(this, new EntityDamage());
        registerEvents(this, new InventoryClick());
        registerEvents(this, new PlayerDropItem());
        registerEvents(this, new PlayerJoin());
        registerEvents(this, new PlayerQuit());

        //TODO Register Commands.
        getCommand("createplaceworld").setExecutor(new CreatePlaceWorld());
        getCommand("join").setExecutor(new Join());

        //TODO Find out why NPE is happening when trying to join BukkitPlaceWorld.

        sm.setup(this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static ConcurrentHashMap<UUID, DrawingColour> getPlayerColours() {
        return playerColours;
    }

    public static World getPlaceWorld() {
        return placeWorld;
    }

    public static Player getPlayerFromUUID(UUID uuid) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getUniqueId().equals(uuid)) {
                return p;
            }
        }
        return null;
    }

    public static Player getPlayerFromUUID(String uuid) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getUniqueId().toString().equals(uuid)) {
                return p;
            }
        }
        return null;
    }

    private static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public static void giveStartingItems(Player p) {
        playerColours.put(p.getUniqueId(), DrawingColour.BLACK);

        ItemStack brush = new ItemStack(Material.IRON_SWORD);
        ItemMeta brushMeta = brush.getItemMeta();
        brushMeta.setDisplayName(ChatColor.GREEN + "Pixel Brush");
        ArrayList<String> brushLore = new ArrayList<>();
        brushLore.add(ChatColor.RED + "Right click to place a pixel!");
        brushLore.add("Current colour: " + playerColours.get(p.getUniqueId()).getChatColor() + playerColours.get(p.getUniqueId()).getDisplayName());
        brush.setItemMeta(brushMeta);

        ItemStack colourSelector = new ItemStack(Material.BOOK);
        ItemMeta colourSelectorMeta = colourSelector.getItemMeta();
        colourSelectorMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Select Colour");
        ArrayList<String> colourSelectorLore = new ArrayList<>();
        colourSelectorLore.add(ChatColor.RED + "Right click to select a colour.");
        colourSelectorLore.add("Current colour: " + playerColours.get(p.getUniqueId()).getChatColor() + playerColours.get(p.getUniqueId()).getDisplayName());
        colourSelector.setItemMeta(colourSelectorMeta);

        ItemStack currentColour = new ItemStack(Material.WOOL, 1, playerColours.get(p.getUniqueId()).getDyeData());
        ItemMeta currentColourMeta = currentColour.getItemMeta();
        currentColourMeta.setDisplayName(playerColours.get(p.getUniqueId()).getChatColor() + "Current Color");
        currentColour.setItemMeta(currentColourMeta);

        ItemStack colourHelmet = new ItemStack(Material.LEATHER_HELMET, 1, playerColours.get(p.getUniqueId()).getDyeData());
        ItemStack colourChestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1, playerColours.get(p.getUniqueId()).getDyeData());
        ItemStack colourLeggings = new ItemStack(Material.LEATHER_LEGGINGS, 1, playerColours.get(p.getUniqueId()).getDyeData());
        ItemStack colourBoots = new ItemStack(Material.LEATHER_BOOTS, 1, playerColours.get(p.getUniqueId()).getDyeData());

        p.getInventory().setItem(0, brush);
        p.getInventory().setItem(4, currentColour);
        p.getInventory().setItem(8, colourSelector);

        p.getInventory().setHelmet(colourHelmet);
        p.getInventory().setChestplate(colourChestplate);
        p.getInventory().setLeggings(colourLeggings);
        p.getInventory().setBoots(colourBoots);

    }

}
