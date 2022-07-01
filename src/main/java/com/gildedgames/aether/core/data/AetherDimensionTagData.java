package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AetherDimensionTagData extends TagsProvider<DimensionType> {
    public AetherDimensionTagData(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, BuiltinRegistries.DIMENSION_TYPE, Aether.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(AetherTags.Dimensions.HOSTILE_PARADISE).add(AetherDimensions.AETHER_DIMENSION_TYPE);

        tag(AetherTags.Dimensions.ULTRACOLD).addTag(AetherTags.Dimensions.HOSTILE_PARADISE);
        tag(AetherTags.Dimensions.NO_WHEAT_SEEDS).addTag(AetherTags.Dimensions.HOSTILE_PARADISE);
        tag(AetherTags.Dimensions.FALL_TO_OVERWORLD).addTag(AetherTags.Dimensions.HOSTILE_PARADISE);
        tag(AetherTags.Dimensions.DISPLAY_TRAVEL_TEXT).addTag(AetherTags.Dimensions.HOSTILE_PARADISE).add(BuiltinDimensionTypes.OVERWORLD);
        tag(AetherTags.Dimensions.AETHER_MUSIC).addTag(AetherTags.Dimensions.HOSTILE_PARADISE);
    }

    @Override
    @Nonnull
    public String getName() {
        return "Aether Dimension Tags";
    }
}
