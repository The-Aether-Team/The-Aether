package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.registry.worldgen.AetherBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class AetherBiomeTagData extends BiomeTagsProvider {
    public AetherBiomeTagData(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Aether.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(AetherTags.Biomes.IS_AETHER).add(
                AetherBiomes.GOLDEN_FOREST.get(),
                AetherBiomes.SKYROOT_FOREST.get(),
                AetherBiomes.SKYROOT_GROVE.get(),
                AetherBiomes.SKYROOT_THICKET.get()
        );
    }
}
