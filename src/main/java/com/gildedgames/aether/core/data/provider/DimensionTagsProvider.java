package com.gildedgames.aether.core.data.provider;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public abstract class DimensionTagsProvider extends TagsProvider<LevelStem> {
    protected DimensionTagsProvider(DataGenerator generator, String modID, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental(), null), modID, existingFileHelper);
    }
}
