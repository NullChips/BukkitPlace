package me.nullchips.bukkitplace.threads;

import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class SetTimeRunnable implements Runnable {

    @Override
    public void run() {
        for(World w : Bukkit.getServer().getWorlds()) {
            w.setTime(6000);
        }
    }
}
