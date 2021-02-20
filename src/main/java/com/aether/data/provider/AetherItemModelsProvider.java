package com.aether.data.provider;

import com.aether.Aether;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public abstract class AetherItemModelsProvider extends ItemModelProvider
{
    public AetherItemModelsProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, Aether.MODID, fileHelper);
    }

    public String blockName(Supplier<? extends Block> block) {
        return block.get().getRegistryName().getPath();
    }

    protected ResourceLocation texture(String name) {
        return modLoc("block/" + name);
    }

    protected ResourceLocation texture(String name, String location) {
        return modLoc("block/" + location + name);
    }

    public ItemModelBuilder item(Supplier<? extends Item> item, String location) {
        return withExistingParent(item.get().getRegistryName().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + item.get().getRegistryName().getPath()));
    }

    public ItemModelBuilder handheldItem(Supplier<? extends Item> item, String location) {
        return withExistingParent(item.get().getRegistryName().getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + item.get().getRegistryName().getPath()));
    }

    public ItemModelBuilder eggItem(Supplier<? extends Item> item) {
        return withExistingParent(item.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
    }

    public ItemModelBuilder itemBlock(Supplier<? extends Block> block) {
        return withExistingParent(blockName(block), texture(blockName(block)));
    }

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location));
    }

    public ItemModelBuilder itemTorch(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location));
    }

    public ItemModelBuilder itemLockedDungeonBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        return withExistingParent(blockName(block), modLoc("item/cube_all_with_overlay"))
                .texture("overlay", texture("lock", "dungeon/"))
                .texture("face", texture(blockName(baseBlock), "dungeon/"));
    }

    public ItemModelBuilder itemTrappedDungeonBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        return withExistingParent(blockName(block), modLoc("item/cube_all_with_overlay"))
                .texture("overlay", texture("exclamation", "dungeon/"))
                .texture("face", texture(blockName(baseBlock), "dungeon/"));
    }

    public ItemModelBuilder itemFence(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return withExistingParent(blockName(block), mcLoc("block/fence_inventory"))
                .texture("texture", texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder itemWallBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return wallInventory(blockName(block), texture(blockName(baseBlock), location));
    }
}
