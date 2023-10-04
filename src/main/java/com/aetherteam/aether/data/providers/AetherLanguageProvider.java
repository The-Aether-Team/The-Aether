package com.aetherteam.aether.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenLanguageProvider;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class AetherLanguageProvider extends NitrogenLanguageProvider {

    private final Map<String, String> PRO_TIPS = new HashMap<>();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PackOutput output;

    public AetherLanguageProvider(PackOutput output, String id) {
        super(output, id);
        this.output = output;
    }

    public void addMoaSkinsText(String key, String name) {
        this.addGuiText("moa_skins." + key, name);
    }

    public void addCustomizationText(String key, String name) {
        this.addGuiText("customization." + key, name);
    }

    public void addLoreBookText(String key, String name) {
        this.addGuiText("book_of_lore." + key, name);
    }

    public void addLore(Supplier<? extends ItemLike> key, String name) {
        this.add("lore." + key.get().asItem().getDescriptionId(), name);
    }

    public void addLoreUnique(String key, String name) {
        this.add("lore." + key, name);
    }

    public void addProTip(String key, String name) {
        String fullKey = "aether.pro_tips.line." + this.id + "." + key;
        this.add(fullKey, name);
        PRO_TIPS.put(fullKey, key);
    }

    public void addMenuTitle(String key, String name) {
        this.add(this.id + ".menu_title." + key, name);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        CompletableFuture<?> languageGen = super.run(cache);
        ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();
        futuresBuilder.add(languageGen);

        for (Map.Entry<String, String> entry : PRO_TIPS.entrySet()) {
            JsonObject object = new JsonObject();
            object.add("title", Component.Serializer.toJsonTree(Component.translatable("gui.aether.pro_tip").withStyle(ChatFormatting.BOLD, ChatFormatting.UNDERLINE, ChatFormatting.YELLOW)));
            object.add("tip", Component.Serializer.toJsonTree(Component.translatable(entry.getKey())));
            futuresBuilder.add(DataProvider.saveStable(cache, GSON.toJsonTree(object), this.output.getOutputFolder().resolve("assets/aether/tips/" + entry.getValue() + ".json")));
        }
        return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
    }
}
