package me.nullchips.bukkitplace.utils;

import me.nullchips.bukkitplace.BukkitPlace;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class PlaceWorldGenerator extends ChunkGenerator {

    private static BukkitPlace plugin;

    public static void setInstance(BukkitPlace instance) {
        plugin = instance;
    }

    @Override
    public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
        byte[][] result = new byte[world.getMaxHeight() / 16][];

        int x, y, z;

        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {
                for (y = 0; y <= 2; y++) {
                    setBlock(result, x, y, z, (byte) Material.AIR.getId());
                }
            }
        }

        return result;
    }

    private void setBlock(byte[][] result, int x, int y, int z, byte blockId) {
        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }

        result[y >> 4][((y & 0xf)) | (z << 4) | x] = blockId;

    }


    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 3, 0);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return new ArrayList<BlockPopulator>();
    }
}
