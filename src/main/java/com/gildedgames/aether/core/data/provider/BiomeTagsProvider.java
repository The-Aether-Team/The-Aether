package com.gildedgames.aether.core.data.provider;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public abstract class BiomeTagsProvider extends TagsProvider<Biome> {
    protected BiomeTagsProvider(DataGenerator generator, String modID, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, new MappedRegistry<>(Registry.BIOME_REGISTRY, Lifecycle.experimental(), null), modID, existingFileHelper);
    }
}
