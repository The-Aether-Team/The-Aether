package com.gildedgames.aether.util;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.capability.rankings.AetherRankings;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class AetherCustomizations {
    public static AetherCustomizations INSTANCE;

    private static final File CUSTOMIZATIONS_FILE = new File(Aether.DIRECTORY.toString(), "aether_customizations.txt");

    private final LinkedHashMap<String, Object> customizations = Maps.newLinkedHashMap();

    public AetherCustomizations() {
        if (CUSTOMIZATIONS_FILE.exists()) {
            this.load();
        } else {
            this.customizations.put("sleeveGloves", false);
            this.customizations.put("haloEnabled", true);
            this.customizations.put("haloColor", null);
            this.customizations.put("developerGlowEnabled", false);
            this.customizations.put("developerGlowColor", null);
            this.save();
        }
    }

    public static void initialize() {
        INSTANCE = new AetherCustomizations();
    }

    public void load() {
        try {
            Scanner reader = new Scanner(CUSTOMIZATIONS_FILE);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] split = line.split(":");
                if (split.length > 1) {
                    String key = split[0];
                    String value = split[1];
                    if (Boolean.parseBoolean(value)) {
                        this.set(key, Boolean.valueOf(value));
                    } else {
                        this.set(key, value);
                    }
                }
            }
        } catch (IOException exception) {
            Aether.LOGGER.warn("Failed to save Aether skin customizations: ", exception);
        }
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(CUSTOMIZATIONS_FILE);
            for (Map.Entry<String, Object> entry : this.customizations.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            Aether.LOGGER.warn("Failed to save Aether skin customizations: ", exception);
        }
    }

    public void sync() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            AetherRankings.get(player).ifPresent(aetherRankings -> {
                aetherRankings.setAreSleeveGloves(this.areSleeveGloves());
                aetherRankings.setIsHaloEnabled(this.isHaloEnabled());
                aetherRankings.setHaloColor(this.getHaloHex());
                aetherRankings.setIsDeveloperGlowEnabled(this.isDeveloperGlowEnabled());
                aetherRankings.setDeveloperGlowColor(this.getDeveloperGlowHex());
            });
        }
    }

    public boolean areSleeveGloves() {
        Object value = this.get("sleeveGloves");
        if (value instanceof Boolean bool) {
            return bool;
        } else {
            return false;
        }
    }

    public void setAreSleeveGloves(boolean value) {
        this.set("sleeveGloves", value);
    }

    public boolean isHaloEnabled() {
        Object value = this.get("haloEnabled");
        if (value instanceof Boolean bool) {
            return bool;
        } else {
            return false;
        }
    }

    public void setIsHaloEnabled(boolean value) {
        this.set("haloEnabled", value);
    }

    public String getHaloHex() {
        Object value = this.get("haloColor");
        if (value instanceof String string) {
            return string;
        } else {
            return null;
        }
    }

    public void setHaloColor(String value) {
        this.set("haloColor", value);
    }

    public boolean isDeveloperGlowEnabled() {
        Object value = this.get("developerGlowEnabled");
        if (value instanceof Boolean bool) {
            return bool;
        } else {
            return false;
        }
    }

    public void setIsDeveloperGlowEnabled(boolean value) {
        this.set("developerGlowEnabled", value);
    }

    public String getDeveloperGlowHex() {
        Object value = this.get("developerGlowColor");
        if (value instanceof String string) {
            return string;
        } else {
            return null;
        }
    }

    public void setDeveloperGlowColor(String value) {
        this.set("developerGlowColor", value);
    }

    public Object get(String string) {
        return this.customizations.get(string);
    }

    public void set(String string, Object object) {
        if (this.customizations.containsKey(string)) {
            this.customizations.replace(string, object);
        } else {
            this.customizations.put(string, object);
        }
    }
}