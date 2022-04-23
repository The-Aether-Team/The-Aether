package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.data.provider.BiomeTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class AetherBiomeTagData extends BiomeTagsProvider {
    public AetherBiomeTagData(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Aether.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(AetherTags.Biomes.MYCELIUM_CONVERSION).add(Biomes.MUSHROOM_FIELDS);
        tag(AetherTags.Biomes.PODZOL_CONVERSION).add(
                Biomes.OLD_GROWTH_PINE_TAIGA,
                Biomes.OLD_GROWTH_SPRUCE_TAIGA,
                Biomes.BAMBOO_JUNGLE);
        tag(AetherTags.Biomes.CRIMSON_NYLIUM_CONVERSION).add(Biomes.CRIMSON_FOREST);
        tag(AetherTags.Biomes.WARPED_NYLIUM_CONVERSION).add(Biomes.WARPED_FOREST);
    }

    @Override
    public String getName() {
        return "Aether Biome Tags";
    }
}
