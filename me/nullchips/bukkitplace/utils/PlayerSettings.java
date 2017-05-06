package me.nullchips.bukkitplace.utils;

import me.nullchips.bukkitplace.BukkitPlace;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Copyright (c) NullChips 2017. All rights reserved.
 * All code contained within this document is the
 * sole property of NullChips. Distribution, reproduction,
 * taking snippets or claiming any contents as your own will
 * break the terms of the license, and void any agreements with
 * you, the third party.
 * Thanks.
 */
public class PlayerSettings {

    private final UUID playerUUID;
    private boolean showPlayers;
    private boolean showChat;

    public PlayerSettings(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.showPlayers = true;
        this.showChat = true;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public boolean isShowingPlayers() {
        return showPlayers;
    }

    public void setShowPlayers(boolean showPlayers) {
        this.showPlayers = showPlayers;
    }

    public boolean isShowingChat() {
        return showChat;
    }

    public void setShowChat(boolean showChat) {
        this.showChat = showChat;
    }

    public Player getPlayer() {
        return BukkitPlace.getPlayerFromUUID(playerUUID);
    }

}
