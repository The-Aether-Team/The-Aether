package com.aetherteam.aether.command;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.mixin.mixins.common.accessor.StoredUserListAccessor;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;

import java.io.File;

/**
 * A whitelist handling who can use the Sun Altar on a server.
 */
public class SunAltarWhitelist {
    public static final File SUN_ALTAR_WHITELIST_FILE = new File(Aether.DIRECTORY.toString(), "sun_altar_whitelist.json");
    private final UserWhiteList sunAltarWhitelist = new UserWhiteList(SUN_ALTAR_WHITELIST_FILE);

    public static final SunAltarWhitelist INSTANCE = new SunAltarWhitelist();

    public SunAltarWhitelist() {
        this.load();
        this.save();
    }

    /**
     * @return The {@link UserWhiteList} for Sun Altar usage.
     */
    public UserWhiteList getSunAltarWhiteList() {
        return this.sunAltarWhitelist;
    }

    /**
     * @return A {@link String String[]} listing the player names in the whitelist.
     */
    public String[] getSunAltarWhiteListNames() {
        return this.sunAltarWhitelist.getUserList();
    }

    /**
     * Checks if a player is whitelisted.
     *
     * @param profile The player's {@link GameProfile}.
     * @return Whether the player was found in the whitelist data, as a {@link Boolean}.
     */
    public boolean isWhiteListed(GameProfile profile) {
        StoredUserListAccessor storedUserListAccessor = (StoredUserListAccessor) this.sunAltarWhitelist;
        return storedUserListAccessor.callContains(profile);
    }

    /**
     * Adds a player to the whitelist.
     *
     * @param element The {@link UserWhiteListEntry} for the player.
     */
    public void add(UserWhiteListEntry element) {
        this.getSunAltarWhiteList().add(element);
        this.save();
    }

    /**
     * Removes a player from the whitelist.
     *
     * @param element The {@link UserWhiteListEntry} for the player.
     */
    public void remove(UserWhiteListEntry element) {
        this.getSunAltarWhiteList().remove(element);
        this.save();
    }

    /**
     * Reloads the whitelist file.
     *
     * @see SunAltarWhitelist#load()
     */
    public void reload() {
        this.load();
    }

    /**
     * Loads the whitelist data from {@link SunAltarWhitelist#SUN_ALTAR_WHITELIST_FILE}.
     */
    private void load() {
        try {
            this.getSunAltarWhiteList().load();
        } catch (Exception exception) {
            Aether.LOGGER.warn("Failed to load Sun Altar whitelist: ", exception);
        }
    }

    /**
     * Saves the whitelist data to {@link SunAltarWhitelist#SUN_ALTAR_WHITELIST_FILE}.
     */
    private void save() {
        try {
            this.getSunAltarWhiteList().save();
        } catch (Exception exception) {
            Aether.LOGGER.warn("Failed to save Sun Altar whitelist: ", exception);
        }
    }
}
