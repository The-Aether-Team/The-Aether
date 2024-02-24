package com.aetherteam.aether.integration.rei.categories;

import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Optional;

public interface BiomeTooltip {
    default void populateBiomeInformation(Optional<ResourceKey<Biome>> biomeKey, Optional<TagKey<Biome>> biomeTag, Tooltip tooltip) {
        if (Minecraft.getInstance().level != null && (biomeKey.isPresent() || biomeTag.isPresent())) {
            tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip").withStyle(ChatFormatting.GRAY));
            if (biomeKey.isPresent()) {
                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.biome").withStyle(ChatFormatting.DARK_GRAY));
                tooltip.add(Component.literal(biomeKey.get().location().toString()).withStyle(ChatFormatting.DARK_GRAY));
            } else {
                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.tag").withStyle(ChatFormatting.DARK_GRAY));
                tooltip.add(Component.literal("#" + biomeTag.get().location()).withStyle(ChatFormatting.DARK_GRAY));

                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.biomes").withStyle(ChatFormatting.DARK_GRAY));
                Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME).getTagOrEmpty(biomeTag.get()).forEach((biomeHolder) -> biomeHolder.unwrapKey().ifPresent((key) -> tooltip.add(Component.literal(key.location().toString()).withStyle(ChatFormatting.DARK_GRAY))));
            }
        }
    }
}
