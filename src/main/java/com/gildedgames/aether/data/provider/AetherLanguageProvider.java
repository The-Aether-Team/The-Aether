package com.gildedgames.aether.data.provider;

import com.gildedgames.aether.Aether;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public abstract class AetherLanguageProvider extends LanguageProvider
{
    public AetherLanguageProvider(DataGenerator gen) {
        super(gen, Aether.MODID, "en_us");
    }

    public void addDiscDesc(Supplier<? extends Item> key, String name) {
        add(key.get().getDescriptionId() + ".desc", name);
    }

    public void addSubtitle(String category, String key, String name) {
        add("subtitles." + category + "." + key, name);
    }

    public void addDeath(String key, String name) {
        add("death.attack." + key, name);
    }

    public void addContainerType(Supplier<? extends MenuType<?>> key, String name) {
        add("menu." + ForgeRegistries.CONTAINERS.getKey(key.get()).toString().replace(":", "."), name);
    }

    public void addContainerType(String key, String name) {
        add("menu.aether." + key, name);
    }

    public void addItemGroup(CreativeModeTab group, String name) {
        add(group.getDisplayName().getString(), name);
    }

    public void addAdvancement(String key, String name) {
        add("advancement.aether." + key, name);
    }

    public void addAdvancementDesc(String key, String name) {
        add("advancement.aether." + key + ".desc", name);
    }

    public void addGuiText(String key, String name) {
        add("gui.aether." + key, name);
    }

    public void addCustomizationText(String key, String name) {
        addGuiText("customization." + key, name);
    }

    public void addMenuText(String key, String name) {
        addGuiText("menu." + key, name);
    }

    public void addLoreBookText(String key, String name) {
        addGuiText("book_of_lore." + key, name);
    }

    public void addMessage(String key, String name) {
        add("aether." + key, name);
    }

    public void addCommand(String key, String name) {
        add("commands.aether." + key, name);
    }

    public void addKeyInfo(String key, String name) {
        add("key.aether." + key, name);
    }

    public void addCuriosIdentifier(String key, String name) {
        add("curios.identifier." + key, name);
    }

    public void addCuriosModifier(String key, String name) {
        add("curios.modifiers." + key, name);
    }

    public void addLore(Supplier<? extends ItemLike> key, String name) {
        add("lore." + key.get().asItem().getDescriptionId(), name);
    }

    public void addProTip(String key, String name) {
        add("aether.pro_tips.line.aether." + key, name);
    }

    public void addCommonConfig(String prefix, String key, String name) {
        add("config.aether.common." + prefix + "." + key, name);
    }

    public void addClientConfig(String prefix, String key, String name) {
        add("config.aether.client." + prefix + "." + key, name);
    }
}
