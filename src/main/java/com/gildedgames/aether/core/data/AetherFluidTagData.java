package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.tags.Tag;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AetherFluidTagData extends FluidTagsProvider
{
    public AetherFluidTagData(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, Aether.MODID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Aether Fluid Tags";
    }

    @Override
    protected void addTags() {
        tag(AetherTags.Fluids.FREEZABLE_TO_AEROGEL).add(
                Fluids.LAVA,
                Fluids.FLOWING_LAVA);
    }

    @Nonnull
    protected TagsProvider.TagAppender<Fluid> tag(@Nonnull Tag.Named<Fluid> tag) {
        return super.tag(tag);
    }
}
