package com.aetherteam.aether.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public abstract class AetherLanguageProvider extends LanguageProvider {
    public AetherLanguageProvider(PackOutput output, String id) {
        super(output, id, "en_us");
    }

    public void addDiscDesc(Supplier<? extends Item> key, String name) {
        this.add(key.get().getDescriptionId() + ".desc", name);
    }

    public void addBiome(ResourceKey<Biome> biome, String name) {
        this.add("biome.aether." + biome.location().getPath(), name);
    }

    public void addContainerType(Supplier<? extends MenuType<?>> key, String name) {
        ResourceLocation location = ForgeRegistries.MENU_TYPES.getKey(key.get());
        if (location != null) {
            this.add("menu." + location.toString().replace(":", "."), name);
        }
    }

    public void addContainerType(String key, String name) {
        this.add("menu.aether." + key, name);
    }

    public void addCreativeTab(CreativeModeTab tab, String name) {
        this.add(tab.getDisplayName().getString(), name);
    }

    public void addAdvancement(String key, String name) {
        this.add("advancement.aether." + key, name);
    }

    public void addAdvancementDesc(String key, String name) {
        this.add("advancement.aether." + key + ".desc", name);
    }

    public void addSubtitle(String category, String key, String name) {
        this.add("subtitles.aether." + category + "." + key, name);
    }

    public void addDeath(String key, String name) {
        this.add("death.attack.aether." + key, name);
    }

    public void addMenuText(String key, String name) {
        this.addGuiText("menu." + key, name);
    }

    public void addGuiText(String key, String name) {
        this.add("gui.aether." + key, name);
    }

    public void addCustomizationText(String key, String name) {
        this.addGuiText("customization." + key, name);
    }

    public void addLoreBookText(String key, String name) {
        this.addGuiText("book_of_lore." + key, name);
    }

    public void addMessage(String key, String name) {
        this.add("aether." + key, name);
    }

    public void addCommand(String key, String name) {
        this.add("commands.aether." + key, name);
    }

    public void addKeyInfo(String key, String name) {
        this.add("key.aether." + key, name);
    }

    public void addCuriosIdentifier(String key, String name) {
        this.add("curios.identifier." + key, name);
    }

    public void addCuriosModifier(String key, String name) {
        this.add("curios.modifiers." + key, name);
    }

    public void addLore(Supplier<? extends ItemLike> key, String name) {
        this.add("lore." + key.get().asItem().getDescriptionId(), name);
    }

    public void addLoreUnique(String key, String name) {
        this.add("lore." + key, name);
    }

    public void addProTip(String key, String name) {
        this.add("aether.pro_tips.line.aether." + key, name);
    }

    public void addCommonConfig(String prefix, String key, String name) {
        this.add("config.aether.common." + prefix + "." + key, name);
    }

    public void addClientConfig(String prefix, String key, String name) {
        this.add("config.aether.client." + prefix + "." + key, name);
    }

    public void addPackTitle(String packName, String description) {
        this.add("pack.aether." + packName + ".title", description);
    }

    public void addPackDescription(String packName, String description) {
        this.add("pack.aether." + packName + ".description", description);
    }
}
