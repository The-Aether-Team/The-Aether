package com.gildedgames.aether.api;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.mixin.mixins.common.accessor.StoredUserListAccessor;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;

import java.io.File;

public class SunAltarWhitelist {
    public static SunAltarWhitelist INSTANCE;

    public static final File SUN_ALTAR_WHITELIST_FILE = new File(Aether.DIRECTORY.toString(), "sun_altar_whitelist.json");
    private final UserWhiteList sunAltarWhitelist = new UserWhiteList(SUN_ALTAR_WHITELIST_FILE);

    public SunAltarWhitelist() {
        this.load();
        this.save();
    }

    public static void initialize() {
        INSTANCE = new SunAltarWhitelist();
    }

    public UserWhiteList getSunAltarWhiteList() {
        return this.sunAltarWhitelist;
    }

    public String[] getSunAltarWhiteListNames() {
        return this.sunAltarWhitelist.getUserList();
    }

    public boolean isWhiteListed(GameProfile profile) {
        StoredUserListAccessor storedUserListAccessor = (StoredUserListAccessor) this.sunAltarWhitelist;
        return storedUserListAccessor.callContains(profile);
    }

    public void add(UserWhiteListEntry element) {
        this.getSunAltarWhiteList().add(element);
        this.save();
    }

    public void remove(UserWhiteListEntry element) {
        this.getSunAltarWhiteList().remove(element);
        this.save();
    }

    public void reload() {
        this.load();
    }

    private void load() {
        try {
            this.getSunAltarWhiteList().load();
        } catch (Exception exception) {
            Aether.LOGGER.warn("Failed to load Sun Altar whitelist: ", exception);
        }
    }

    private void save() {
        try {
            this.getSunAltarWhiteList().save();
        } catch (Exception exception) {
            Aether.LOGGER.warn("Failed to save Sun Altar whitelist: ", exception);
        }
    }
}
