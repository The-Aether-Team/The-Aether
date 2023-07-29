package com.aetherteam.aether.mixin;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class AetherMixinConfig {
    private static final String MIXIN_PACKAGE_ROOT = "com.aetherteam.aether.core.mixin.";
    private static final Path DIRECTORY = FMLPaths.CONFIGDIR.get().resolve("aether");
    private static final File MIXIN_CONFIG = new File(DIRECTORY.toString(), "mixin_config.json");

    private static final ImmutableList<Entry> configEntries = ImmutableList.of(
            new Entry(MIXIN_PACKAGE_ROOT + "client.AdvancementToastMixin", true),
            new Entry(MIXIN_PACKAGE_ROOT + "client.TippableArrowRendererMixin", true)
    );

    public static void save() {
        if (!DIRECTORY.toFile().exists()) {
            DIRECTORY.toFile().mkdirs();
        }
        if (!MIXIN_CONFIG.exists()) {
            try (FileWriter fileWriter = new FileWriter(MIXIN_CONFIG)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("comment", "Disabling these is not advised unless necessary");
                for (Entry entry : configEntries) {
                    jsonObject.addProperty(entry.path(), entry.defaultValue());
                }
                gson.toJson(jsonObject, fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Entry load(String mixinClassName) {
        if (MIXIN_CONFIG.exists()) {
            try (FileReader fileReader = new FileReader(MIXIN_CONFIG)) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
                for (Map.Entry<String, JsonElement> property : jsonObject.entrySet()) {
                    if (property.getKey().equals(mixinClassName)) {
                        return new Entry(property.getKey(), property.getValue().getAsBoolean());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public record Entry(String path, boolean defaultValue) { }
}
