package com.gildedgames.aether.data.generators.tags;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.data.resources.registries.AetherBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherBiomeTagData extends BiomeTagsProvider {
    public AetherBiomeTagData(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, Aether.MODID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherTags.Biomes.IS_AETHER).add(
                AetherBiomes.GOLDEN_FOREST,
                AetherBiomes.SKYROOT_FOREST,
                AetherBiomes.SKYROOT_GROVE,
                AetherBiomes.SKYROOT_THICKET
        );
        this.tag(AetherTags.Biomes.HAS_LARGE_AERCLOUD).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(AetherTags.Biomes.HAS_BRONZE_DUNGEON).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(AetherTags.Biomes.HAS_SILVER_DUNGEON).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(AetherTags.Biomes.HAS_GOLD_DUNGEON).addTag(AetherTags.Biomes.IS_AETHER);

        this.tag(AetherTags.Biomes.MYCELIUM_CONVERSION).add(Biomes.MUSHROOM_FIELDS);
        this.tag(AetherTags.Biomes.PODZOL_CONVERSION).add(
                Biomes.OLD_GROWTH_PINE_TAIGA,
                Biomes.OLD_GROWTH_SPRUCE_TAIGA,
                Biomes.BAMBOO_JUNGLE);
        this.tag(AetherTags.Biomes.CRIMSON_NYLIUM_CONVERSION).add(Biomes.CRIMSON_FOREST);
        this.tag(AetherTags.Biomes.WARPED_NYLIUM_CONVERSION).add(Biomes.WARPED_FOREST);

        this.tag(AetherTags.Biomes.ULTRACOLD).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(AetherTags.Biomes.NO_WHEAT_SEEDS).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(AetherTags.Biomes.FALL_TO_OVERWORLD).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(AetherTags.Biomes.DISPLAY_TRAVEL_TEXT).addTag(AetherTags.Biomes.IS_AETHER).addTag(BiomeTags.IS_OVERWORLD).add(Biomes.THE_VOID);
        this.tag(AetherTags.Biomes.AETHER_MUSIC).addTag(AetherTags.Biomes.IS_AETHER);
    }
}
