package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class AetherFluidTagData extends FluidTagsProvider
{
    public AetherFluidTagData(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, Aether.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Aether Fluid Tags";
    }

    @Override
    protected void addTags() {
        tag(AetherTags.Fluids.FREEZABLE_TO_AEROGEL)
                .add(Fluids.LAVA)
                .add(Fluids.FLOWING_LAVA);
    }

    protected TagsProvider.Builder<Fluid> tag(ITag.INamedTag<Fluid> tag) {
        return super.tag(tag);
    }
}
