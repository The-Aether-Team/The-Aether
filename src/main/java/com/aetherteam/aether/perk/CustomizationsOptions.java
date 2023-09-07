package com.aetherteam.aether.perk;

import com.aetherteam.aether.Aether;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CustomizationsOptions {
    private static final File CUSTOMIZATIONS_FILE = new File(Aether.DIRECTORY.toString(), "aether_customizations.txt");
    private final LinkedHashMap<String, Object> customizations = new LinkedHashMap<>();

    public static final CustomizationsOptions INSTANCE = new CustomizationsOptions();

    public CustomizationsOptions() {
        if (CUSTOMIZATIONS_FILE.exists()) {
            this.load();
        } else {
            this.customizations.put("haloEnabled", true);
            this.customizations.put("haloColor", "");
            this.customizations.put("developerGlowEnabled", false);
            this.customizations.put("developerGlowColor", "");
            this.customizations.put("moaSkin", "");
            this.save();
        }
    }

    /**
     * Loads settings from {@link CustomizationsOptions#CUSTOMIZATIONS_FILE}.
     */
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
            Aether.LOGGER.warn("Failed to load Aether perk customizations: ", exception);
        }
    }

    /**
     * Saves settings to {@link CustomizationsOptions#CUSTOMIZATIONS_FILE}.
     */
    public void save() {
        try {
            FileWriter writer = new FileWriter(CUSTOMIZATIONS_FILE);
            for (Map.Entry<String, Object> entry : this.customizations.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            Aether.LOGGER.warn("Failed to save Aether perk customizations: ", exception);
        }
    }

    /**
     * @return A {@link Boolean} for the "haloEnabled" setting.
     */
    public boolean isHaloEnabled() {
        Object value = this.get("haloEnabled");
        if (value instanceof Boolean bool) {
            return bool;
        } else {
            return false;
        }
    }

    /**
     * Modifies the "haloEnabled" setting.
     * @param value The {@link Boolean} value.
     */
    public void setIsHaloEnabled(boolean value) {
        this.set("haloEnabled", value);
    }

    /**
     * @return A {@link String} for a hex code from the "haloColor" setting.
     */
    public String getHaloHex() {
        Object value = this.get("haloColor");
        if (value instanceof String string) {
            return string;
        } else {
            return "";
        }
    }

    /**
     * Modifies the "haloColor" setting.
     * @param value The {@link String} hex code value.
     */
    public void setHaloColor(String value) {
        this.set("haloColor", value);
    }

    /**
     * @return A {@link Boolean} for the "developerGlowEnabled" setting.
     */
    public boolean isDeveloperGlowEnabled() {
        Object value = this.get("developerGlowEnabled");
        if (value instanceof Boolean bool) {
            return bool;
        } else {
            return false;
        }
    }

    /**
     * Modifies the "developerGlowEnabled" setting.
     * @param value The {@link Boolean} value.
     */
    public void setIsDeveloperGlowEnabled(boolean value) {
        this.set("developerGlowEnabled", value);
    }

    /**
     * @return A {@link String} for a hex code from the "developerGlowColor" setting.
     */
    public String getDeveloperGlowHex() {
        Object value = this.get("developerGlowColor");
        if (value instanceof String string) {
            return string;
        } else {
            return "";
        }
    }

    /**
     * Modifies the "developerGlowColor" setting.
     * @param value The {@link String} hex code value.
     */
    public void setDeveloperGlowColor(String value) {
        this.set("developerGlowColor", value);
    }

    /**
     * @return A {@link String} name ID from the "moaSkin" setting.
     */
    public String getMoaSkin() {
        Object value = this.get("moaSkin");
        if (value instanceof String string) {
            return string;
        } else {
            return "";
        }
    }

    /**
     * Modifies the "moaSkin" setting.
     * @param value The {@link String} name ID value.
     */
    public void setMoaSkin(String value) {
        this.set("moaSkin", value);
    }

    /**
     * @param string A setting key {@link String}.
     * @return A setting value as an {@link Object}.
     */
    public Object get(String string) {
        return this.customizations.get(string);
    }

    /**
     * Modifies a setting.
     * @param string A setting key {@link String}.
     * @param object A setting value as an {@link Object}.
     */
    public void set(String string, Object object) {
        if (this.customizations.containsKey(string)) {
            this.customizations.replace(string, object);
        } else {
            this.customizations.put(string, object);
        }
    }
}
