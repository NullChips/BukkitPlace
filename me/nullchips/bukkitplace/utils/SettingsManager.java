package me.nullchips.bukkitplace.utils;

import me.nullchips.bukkitplace.BukkitPlace;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class SettingsManager {
    private static SettingsManager ourInstance = new SettingsManager();

    public static SettingsManager getInstance() {
        return ourInstance;
    }

    private SettingsManager() {

    }

    private Plugin p;
    private FileConfiguration config;
    private File cfile;

    public void setup(Plugin p) {
        config = p.getConfig();
        config.options().copyDefaults(true);
        cfile = new File(p.getDataFolder(), "config.yml");
        BukkitPlace.getInstance().saveDefaultConfig();
        loadSettings();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe("Could not save config.yml!");
        }
    }

    private boolean containsInt(String path) {
        if (config.contains(path) && config.get(path) instanceof Integer) {
            return true;
        }
        return false;
    }

    private boolean containsString(String path) {
        if (config.contains(path) && config.get(path) instanceof String) {
            return true;
        }
        return false;
    }

    private boolean containsDouble(String path) {
        if (config.contains(path) && config.get(path) instanceof Double) {
            return true;
        }
        return false;
    }

    private boolean containsBoolean(String path) {
        if (config.contains(path) && config.get(path) instanceof Boolean) {
            return true;
        }
        return false;
    }

    private boolean containsLocation(String path) {
        if (containsDouble(path + ".x") && containsDouble(path + ".y") && containsDouble(path + ".z") &&
                containsString(path + ".world") && containsDouble(path + ".yaw") && containsDouble(path + ".pitch")) {
            return true;
        }
        return false;
    }

    private Location getLocation(String path, World world) {
        FileConfiguration config = getConfig();

        double x, y, z, yaw, pitch;
        float yawF, pitchF;

        x = config.getDouble(path + ".x");
        y = config.getDouble(path + ".y");
        z = config.getDouble(path + ".z");
        yaw = config.getDouble(path + ".yaw");
        pitch = config.getDouble(path + ".pitch");

        yawF = (float) yaw;
        pitchF = (float) pitch;

        Location l = new Location(world, x, y, z, yawF, pitchF);

        return l;
    }

    //Settings
    private long cooldownTime;
    private int canvasRadius;
    private Location hubLocation;

    public void loadSettings() {

        final Logger logger = Bukkit.getServer().getLogger();

        //Cooldown time.
        if (containsInt("cooldown-time")) {
            cooldownTime = (long) config.getInt("cooldown-time") * 20;
        } else {
            logger.info("The cooldown time could not be found in config.yml. Setting to default cooldown time of 5 minutes.");
            cooldownTime = 6000;
        }

        //Canvas radius.
        if (containsInt("canvas-radius")) {
            canvasRadius = config.getInt("canvas-radius");
        } else {
            logger.info("The canvas radius cannot be found in config.yml. Setting to default radius of 250.");
            canvasRadius = 250;
        }

        //Hub location.
        if (containsLocation("hub-location")) {
            String hubWorldString = config.getString("hub-location.world");

            World hubWorld;
            File worldFiles = new File(hubWorldString + "/level.dat");
            if (worldFiles.exists()) {
                hubWorld = Bukkit.getServer().getWorld(hubWorldString);
                hubLocation = getLocation("hub-location", hubWorld);
            } else {
                Bukkit.getServer().getLogger().info(ChatColor.RED + "The hub world location could not be found in the config.yml!");
                hubLocation = null;
            }
        } else {
            Bukkit.getServer().getLogger().info(ChatColor.RED + "The hub world location could not be found in the config.yml!");
            hubLocation = null;
        }
    }

    public long getCooldownTime() {
        return cooldownTime;
    }

    public int getCanvasRadius() {
        return canvasRadius;
    }

    public Location getHubLocation() {
        return hubLocation;
    }

    public void setHubLocation(Location hubLocation) {
        this.hubLocation = hubLocation;
    }

}
