package me.nullchips.bukkitplace;

import me.nullchips.bukkitplace.commands.CreatePlaceWorld;
import me.nullchips.bukkitplace.commands.Hub;
import me.nullchips.bukkitplace.commands.Join;
import me.nullchips.bukkitplace.commands.SetHubSpawn;
import me.nullchips.bukkitplace.listeners.*;
import me.nullchips.bukkitplace.threads.SetTimeRunnable;
import me.nullchips.bukkitplace.utils.PlaceWorldGenerator;
import me.nullchips.bukkitplace.utils.PlayerSettings;
import me.nullchips.bukkitplace.utils.SettingsManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static ArrayList<PlayerSettings> playerSettings;
    private static ArrayList<UUID> settingsCooldown;
    private static World placeWorld;

    @Override
    public void onEnable() {
        instance = this;

        sm = SettingsManager.getInstance();

        playerColours = new ConcurrentHashMap<>();
        playerSettings = new ArrayList<>();
        settingsCooldown = new ArrayList<>();

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
        registerEvents(this, new CreatureSpawn());
        registerEvents(this, new PlayerInteract());
        registerEvents(this, new BlockPlace());
        registerEvents(this, new BlockBreak());
        //TODO Chat formatting, players who have chat turned off.

        //Register Commands.
        getCommand("createplaceworld").setExecutor(new CreatePlaceWorld());
        getCommand("join").setExecutor(new Join());
        getCommand("sethubspawn").setExecutor(new SetHubSpawn());
        getCommand("hub").setExecutor(new Hub());

        sm.setup(this);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SetTimeRunnable(), 10L, 10L);
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

    public static ArrayList<PlayerSettings> getPlayerSettings() {
        return playerSettings;
    }

    public static ArrayList<UUID> getSettingsCooldown() {
        return settingsCooldown;
    }

    public static PlayerSettings getPlayerSettings(UUID uuid) {
        for (PlayerSettings ps : playerSettings) {
            if (ps.getPlayerUUID().equals(uuid)) {
                return ps;
            }
        }
        return null;
    }

    public static PlayerSettings getPlayerSettings(Player p) {
        for (PlayerSettings ps : playerSettings) {
            if (ps.getPlayerUUID().equals(p.getUniqueId())) {
                return ps;
            }
        }
        return null;
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
        ItemStack brush = new ItemStack(Material.IRON_SWORD);
        ItemMeta brushMeta = brush.getItemMeta();
        brushMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Pixel Brush");
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

        ItemStack settings = new ItemStack(Material.COMPASS);
        ItemMeta settingsMeta = settings.getItemMeta();
        settingsMeta.setDisplayName(ChatColor.RED + "Settings");
        settings.setItemMeta(settingsMeta);

        ItemStack colourHelmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack colourChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack colourLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack colourBoots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta colourHelmetMeta = (LeatherArmorMeta) colourHelmet.getItemMeta();
        LeatherArmorMeta colourChestplateMeta = (LeatherArmorMeta) colourChestplate.getItemMeta();
        LeatherArmorMeta colourLeggingsMeta = (LeatherArmorMeta) colourLeggings.getItemMeta();
        LeatherArmorMeta colourBootsMeta = (LeatherArmorMeta) colourBoots.getItemMeta();

        colourHelmetMeta.setColor(playerColours.get(p.getUniqueId()).getArmourColour());
        colourChestplateMeta.setColor(playerColours.get(p.getUniqueId()).getArmourColour());
        colourLeggingsMeta.setColor(playerColours.get(p.getUniqueId()).getArmourColour());
        colourBootsMeta.setColor(playerColours.get(p.getUniqueId()).getArmourColour());

        colourHelmet.setItemMeta(colourHelmetMeta);
        colourChestplate.setItemMeta(colourChestplateMeta);
        colourLeggings.setItemMeta(colourLeggingsMeta);
        colourBoots.setItemMeta(colourBootsMeta);

        p.getInventory().setItem(0, brush);
        p.getInventory().setItem(4, currentColour);
        p.getInventory().setItem(8, colourSelector);
        p.getInventory().setItem(7, settings);

        p.getInventory().setHelmet(colourHelmet);
        p.getInventory().setChestplate(colourChestplate);
        p.getInventory().setLeggings(colourLeggings);
        p.getInventory().setBoots(colourBoots);
    }

    public static void clearInventory(Player p) {
        p.getInventory().clear();
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    public static void addToSettingsCooldown(Player p) {
        final UUID uuid = p.getUniqueId();
        settingsCooldown.add(uuid);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BukkitPlace.getInstance(), new Runnable() {
            @Override
            public void run() {
                settingsCooldown.remove(uuid);
            }
        }, 50L);
    }

}
