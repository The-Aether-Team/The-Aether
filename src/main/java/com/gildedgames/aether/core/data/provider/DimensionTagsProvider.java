package com.gildedgames.aether.core.data.provider;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public abstract class DimensionTagsProvider extends TagsProvider<DimensionType> {
    protected DimensionTagsProvider(DataGenerator generator, String modID, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, BuiltinRegistries.DIMENSION_TYPE, modID, existingFileHelper);
    }
}
