package com.gildedgames.aether.common.world.builders;

import com.gildedgames.aether.Aether;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class AetherDimensionBuilders {
    public static DimensionType aetherDimensionType() {
        return DimensionType.create(
                OptionalLong.empty(), // fixed_time
                true, // has_skylight
                false, // has_ceiling
                false, // ultrawarm
                true, // natural
                1.0D, // coordinate_scale
                false, // createDragonFight
                false, // piglin_safe
                true, // bed_works
                false, // respawn_anchor_works
                false, // has_raids
                0, // min_y
                256, // height [This is min_y + max_y. This value is the total height of the building space going from the minimum height to the desired maximum build height]
                256, // logical_height [Ditto, except for processing - ticking and such]
                BlockTags.INFINIBURN_OVERWORLD,
                new ResourceLocation(Aether.MODID, "the_aether"), // effects
                0.1F // ambient_light
        );
    }
}
