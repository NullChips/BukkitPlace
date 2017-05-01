package me.nullchips.bukkitplace;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public enum DrawingColour {

    WHITE("White", ChatColor.WHITE, DyeColor.WHITE.getDyeData()),
    ORANGE("Orange", ChatColor.GOLD, DyeColor.ORANGE.getDyeData()),
    MAGENTA("Magenta", ChatColor.LIGHT_PURPLE, DyeColor.MAGENTA.getDyeData()),
    LIGHT_BLUE("Light Blue", ChatColor.AQUA, DyeColor.LIGHT_BLUE.getDyeData()),
    YELLOW("Yellow", ChatColor.YELLOW, DyeColor.YELLOW.getDyeData()),
    LIGHT_GREEN("Light Green", ChatColor.GREEN, DyeColor.LIME.getDyeData()),
    PINK("Pink", ChatColor.LIGHT_PURPLE, DyeColor.PINK.getDyeData()),
    GREY("Grey", ChatColor.DARK_GRAY, DyeColor.GRAY.getDyeData()),
    LIGHT_GREY("Light Grey", ChatColor.GRAY, DyeColor.SILVER.getDyeData()),
    CYAN("Cyan", ChatColor.DARK_AQUA, DyeColor.CYAN.getDyeData()),
    PURPLE("Purple", ChatColor.DARK_PURPLE, DyeColor.PURPLE.getDyeData()),
    BLUE("Blue", ChatColor.DARK_BLUE, DyeColor.BLUE.getDyeData()),
    BROWN("Brown", ChatColor.GOLD, DyeColor.BROWN.getDyeData()),
    GREEN("Green", ChatColor.DARK_GREEN, DyeColor.GREEN.getDyeData()),
    RED("Red", ChatColor.RED, DyeColor.RED.getDyeData()),
    BLACK("Black", ChatColor.BLACK, DyeColor.BLACK.getDyeData());

    private String displayName;
    private ChatColor chatColor;
    private byte dyeData;

    DrawingColour(String displayName, ChatColor chatColor, byte dyeData) {
        this.displayName = displayName;
        this.chatColor = chatColor;
        this.dyeData = dyeData;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public byte getDyeData() {
        return dyeData;
    }

}
