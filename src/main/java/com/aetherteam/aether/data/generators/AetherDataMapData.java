package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.api.AetherDataMaps;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class AetherDataMapData extends DataMapProvider {
    public AetherDataMapData(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void gather() {
        var compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        this.addCompost(compostables, AetherBlocks.SKYROOT_LEAVES, 0.3F);
        this.addCompost(compostables, AetherBlocks.SKYROOT_SAPLING, 0.3F);
        this.addCompost(compostables, AetherBlocks.GOLDEN_OAK_LEAVES, 0.3F);
        this.addCompost(compostables, AetherBlocks.GOLDEN_OAK_SAPLING, 0.3F);
        this.addCompost(compostables, AetherBlocks.CRYSTAL_LEAVES, 0.3F);
        this.addCompost(compostables, AetherBlocks.CRYSTAL_FRUIT_LEAVES, 0.3F);
        this.addCompost(compostables, AetherBlocks.HOLIDAY_LEAVES, 0.3F);
        this.addCompost(compostables, AetherBlocks.DECORATED_HOLIDAY_LEAVES, 0.3F);
        this.addCompost(compostables, AetherItems.BLUE_BERRY, 0.3F);
        this.addCompost(compostables, AetherItems.ENCHANTED_BERRY, 0.5F);
        this.addCompost(compostables, AetherBlocks.BERRY_BUSH, 0.5F);
        this.addCompost(compostables, AetherBlocks.BERRY_BUSH_STEM, 0.5F);
        this.addCompost(compostables, AetherBlocks.WHITE_FLOWER, 0.65F);
        this.addCompost(compostables, AetherBlocks.PURPLE_FLOWER, 0.65F);
        this.addCompost(compostables, AetherItems.WHITE_APPLE, 0.65F);

        var fuels = this.builder(NeoForgeDataMaps.FURNACE_FUELS);
        fuels.add(AetherBlocks.AMBROSIUM_BLOCK.asItem().builtInRegistryHolder(), new FurnaceFuel(16000), false);
        fuels.add(AetherItems.AMBROSIUM_SHARD, new FurnaceFuel(1600), false);
        fuels.add(AetherBlocks.SKYROOT_PLANKS.asItem().builtInRegistryHolder(), new FurnaceFuel(300), false);
        fuels.add(AetherBlocks.SKYROOT_BOOKSHELF.asItem().builtInRegistryHolder(), new FurnaceFuel(300), false);
        fuels.add(AetherItems.SKYROOT_SWORD, new FurnaceFuel(200), false);
        fuels.add(AetherItems.SKYROOT_PICKAXE, new FurnaceFuel(200), false);
        fuels.add(AetherItems.SKYROOT_AXE, new FurnaceFuel(200), false);
        fuels.add(AetherItems.SKYROOT_SHOVEL, new FurnaceFuel(200), false);
        fuels.add(AetherItems.SKYROOT_HOE, new FurnaceFuel(200), false);
        fuels.add(AetherItems.SKYROOT_BUCKET, new FurnaceFuel(200), false);
        fuels.add(AetherItems.SKYROOT_STICK, new FurnaceFuel(100), false);

        var altar = this.builder(AetherDataMaps.ALTAR_FUEL);
        altar.add(AetherItems.AMBROSIUM_SHARD, new FurnaceFuel(250), false);
        altar.add(AetherBlocks.AMBROSIUM_BLOCK.asItem().builtInRegistryHolder(), new FurnaceFuel(2500), false);

        var freezer = this.builder(AetherDataMaps.FREEZER_FUEL);
        freezer.add(AetherBlocks.ICESTONE.asItem().builtInRegistryHolder(), new FurnaceFuel(400), false);
        freezer.add(AetherBlocks.ICESTONE_SLAB.asItem().builtInRegistryHolder(), new FurnaceFuel(200), false);
        freezer.add(AetherBlocks.ICESTONE_STAIRS.asItem().builtInRegistryHolder(), new FurnaceFuel(400), false);
        freezer.add(AetherBlocks.ICESTONE_WALL.asItem().builtInRegistryHolder(), new FurnaceFuel(400), false);

        var incubator = this.builder(AetherDataMaps.INCUBATOR_FUEL);
        incubator.add(AetherBlocks.AMBROSIUM_TORCH.asItem().builtInRegistryHolder(), new FurnaceFuel(500), false);
    }

    private void addCompost(DataMapProvider.Builder<Compostable, Item> map, ItemLike item, float chance) {
        map.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false);
    }
}
