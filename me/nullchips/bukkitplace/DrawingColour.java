package me.nullchips.bukkitplace;

import org.bukkit.ChatColor;
import org.bukkit.Color;
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
    /*
        Order of dye colours are reversed for some reason.

        RGB values were obtained from: http://minecraft.gamepedia.com/Wool
     */

    WHITE("White", ChatColor.WHITE, DyeColor.BLACK.getDyeData(), Color.WHITE),
    ORANGE("Orange", ChatColor.GOLD, DyeColor.RED.getDyeData(), Color.ORANGE),
    MAGENTA("Magenta", ChatColor.LIGHT_PURPLE, DyeColor.GREEN.getDyeData(), Color.fromRGB(189, 68, 179)),
    LIGHT_BLUE("Light Blue", ChatColor.AQUA, DyeColor.BROWN.getDyeData(), Color.fromRGB(58, 175, 217)),
    YELLOW("Yellow", ChatColor.YELLOW, DyeColor.BLUE.getDyeData(), Color.YELLOW),
    LIGHT_GREEN("Light Green", ChatColor.GREEN, DyeColor.PURPLE.getDyeData(), Color.LIME),
    PINK("Pink", ChatColor.LIGHT_PURPLE, DyeColor.CYAN.getDyeData(), Color.fromRGB(237, 141, 172)),
    GREY("Grey", ChatColor.DARK_GRAY, DyeColor.SILVER.getDyeData(), Color.GRAY),
    LIGHT_GREY("Light Grey", ChatColor.GRAY, DyeColor.GRAY.getDyeData(), Color.fromRGB(142, 142, 134)),
    CYAN("Cyan", ChatColor.DARK_AQUA, DyeColor.PINK.getDyeData(), Color.fromRGB(21, 137, 145)),
    PURPLE("Purple", ChatColor.DARK_PURPLE, DyeColor.LIME.getDyeData(), Color.PURPLE),
    BLUE("Blue", ChatColor.DARK_BLUE, DyeColor.YELLOW.getDyeData(), Color.BLUE),
    BROWN("Brown", ChatColor.GOLD, DyeColor.LIGHT_BLUE.getDyeData(), Color.MAROON),
    GREEN("Green", ChatColor.DARK_GREEN, DyeColor.MAGENTA.getDyeData(), Color.GREEN),
    RED("Red", ChatColor.RED, DyeColor.ORANGE.getDyeData(), Color.RED),
    BLACK("Black", ChatColor.DARK_GRAY, DyeColor.WHITE.getDyeData(), Color.BLACK);

    private String displayName;
    private ChatColor chatColor;
    private byte dyeData;
    private Color armourColour;

    DrawingColour(String displayName, ChatColor chatColor, byte dyeData, Color armourColor) {
        this.displayName = displayName;
        this.chatColor = chatColor;
        this.dyeData = dyeData;
        this.armourColour = armourColor;
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

    public Color getArmourColour() {
        return armourColour;
    }

}
