package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import com.gildedgames.aether.core.data.provider.DimensionTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class AetherDimensionTagData extends DimensionTagsProvider {
    public AetherDimensionTagData(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Aether.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(AetherTags.Dimensions.HOSTILE_PARADISE).add(AetherDimensions.AETHER_DIMENSION_TYPE);
        
        tag(AetherTags.Dimensions.ULTRACOLD).addTag(AetherTags.Dimensions.HOSTILE_PARADISE);
        tag(AetherTags.Dimensions.ETERNAL_DAY).addTag(AetherTags.Dimensions.HOSTILE_PARADISE);
        tag(AetherTags.Dimensions.NO_WHEAT_SEEDS).addTag(AetherTags.Dimensions.HOSTILE_PARADISE);
        tag(AetherTags.Dimensions.FALL_TO_OVERWORLD).addTag(AetherTags.Dimensions.HOSTILE_PARADISE);
        tag(AetherTags.Dimensions.AETHER_MUSIC).addTag(AetherTags.Dimensions.HOSTILE_PARADISE);
    }

    @Override
    public String getName() {
        return "Aether Dimension Tags";
    }
}
