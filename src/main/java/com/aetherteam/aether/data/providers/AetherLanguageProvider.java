package com.aetherteam.aether.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenLanguageProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public abstract class AetherLanguageProvider extends NitrogenLanguageProvider {
    public AetherLanguageProvider(PackOutput output, String id) {
        super(output, id);
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
        this.add("aether.pro_tips.line." + this.id + "." + key, name);
    }
}
