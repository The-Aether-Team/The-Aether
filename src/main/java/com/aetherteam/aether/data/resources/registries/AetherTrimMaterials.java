package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class AetherTrimMaterials {
    public static final ResourceKey<TrimMaterial> ZANITE = createKey("zanite");
    public static final ResourceKey<TrimMaterial> GRAVITITE = createKey("gravitite");
    public static final ResourceKey<TrimMaterial> GOLDEN_AMBER = createKey("golden_amber");

    private static ResourceKey<TrimMaterial> createKey(String name) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(Aether.MODID, name));
    }

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        register(context, ZANITE, AetherItems.ZANITE_GEMSTONE.get(), Style.EMPTY.withColor(8009440), 1.0F);
        register(context, GRAVITITE, AetherBlocks.ENCHANTED_GRAVITITE.get().asItem(), Style.EMPTY.withColor(13391043), 1.0F);
        register(context, GOLDEN_AMBER, AetherItems.GOLDEN_AMBER.get().asItem(), Style.EMPTY.withColor(16299311), 0.6F);
    }

    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> materialKey, Item ingredient, Style style, float itemModelIndex) {
        register(context, materialKey, ingredient, style, itemModelIndex, Map.of());
    }

    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> materialKey, Item ingredient, Style style, float itemModelIndex, Map<Holder<ArmorMaterial>, String> overrideArmorMaterials) {
        TrimMaterial trimMaterial = TrimMaterial.create(materialKey.location().getPath(), ingredient, itemModelIndex, Component.translatable(Util.makeDescriptionId("trim_material", materialKey.location())).withStyle(style), overrideArmorMaterials);
        context.register(materialKey, trimMaterial);
    }
}
