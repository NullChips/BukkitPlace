package me.nullchips.bukkitplace.utils;

import me.nullchips.bukkitplace.BukkitPlace;
import org.bukkit.Bukkit;
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
        if(config.contains(path) && config.get(path) instanceof Boolean) {
            return true;
        }
        return false;
    }

    //Settings
    private long cooldownTime;

    public void loadSettings() {

        final Logger logger = Bukkit.getServer().getLogger();

        if(containsInt("cooldown-time")) {
            cooldownTime = config.getInt("cooldown-time") *20;
        } else {
            logger.info("The default cooldown time could not be found. Setting to default cooldown time of 5 minutes.");
            cooldownTime = 6000;
        }
    }

    public long getCooldownTime() {
        return cooldownTime;
    }

}
