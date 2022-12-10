package com.gildedgames.aether.data.providers;

import com.gildedgames.aether.Aether;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public abstract class AetherItemModelProvider extends ItemModelProvider {
    public AetherItemModelProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, Aether.MODID, fileHelper);
    }

    public String blockName(Supplier<? extends Block> block) {
        return ForgeRegistries.BLOCKS.getKey(block.get()).getPath();
    }

    protected ResourceLocation texture(String name) {
        return modLoc("block/" + name);
    }

    protected ResourceLocation texture(String name, String location) {
        return modLoc("block/" + location + name);
    }

    public ItemModelBuilder item(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath()));
    }

    public ItemModelBuilder lookalikeBlock(Supplier<? extends Block> block, ResourceLocation lookalike) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), lookalike);
    }

    public ItemModelBuilder handheldItem(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath()));
    }

    public ItemModelBuilder lanceItem(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath()))
                .transforms()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND).rotation(0.0F, -90.0F, 45.0F).translation(0.0F, 1.0F, -5.0F).scale(0.85F, 0.85F, 0.85F).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND).rotation(0.0F, 90.0F, -45.0F).translation(0.0F, 1.0F, -5.0F).scale(0.85F, 0.85F, 0.85F).end()
                .end();
    }

    public ItemModelBuilder nameableWeapon(Supplier<? extends Item> item, String location, String renamedVariant) {
        withExistingParent(renamedVariant, mcLoc("item/handheld")).texture("layer0", modLoc("item/" + location + renamedVariant));
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath()))
                .override().predicate(new ResourceLocation(Aether.MODID, "named"), 1).model(getExistingFile(modLoc("item/" + renamedVariant))).end();
    }

    public ItemModelBuilder dartShooterItem(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath()))
                .transforms()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND).rotation(0.0F, -90.0F, 45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F, 0.85F, 0.85F).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND).rotation(0.0F, 90.0F, -45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F, 0.85F, 0.85F).end()
                .end();
    }

    public ItemModelBuilder bowItem(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        withExistingParent(id.getPath() + "_pulling_0", mcLoc("item/bow")).texture("layer0", modLoc("item/" + location + id.getPath() + "_pulling_0"));
        withExistingParent(id.getPath() + "_pulling_1", mcLoc("item/bow")).texture("layer0", modLoc("item/" + location + id.getPath() + "_pulling_1"));
        withExistingParent(id.getPath() + "_pulling_2", mcLoc("item/bow")).texture("layer0", modLoc("item/" + location + id.getPath() + "_pulling_2"));
        return withExistingParent(id.getPath(), mcLoc("item/bow"))
                .texture("layer0", modLoc("item/" + location + id.getPath()))
                .override().predicate(new ResourceLocation("pulling"), 1).model(getExistingFile(modLoc("item/" + id.getPath() + "_pulling_0"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65F).model(getExistingFile(modLoc("item/" + id.getPath() + "_pulling_1"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9F).model(getExistingFile(modLoc("item/" + id.getPath() + "_pulling_2"))).end();
    }

    public ItemModelBuilder dyedItem(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath()))
                .texture("layer1", modLoc("item/" + location + id.getPath() + "_overlay"));
    }

    public ItemModelBuilder keyItem(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath()))
                .transforms()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND).rotation(90.0F, -90.0F, 25.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND).rotation(90.0F, 90.0F, -25.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end()
                .end();
    }

    public ItemModelBuilder moaEggItem(Supplier<? extends Item> item, String location) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + "moa_egg"))
                .texture("layer1", modLoc("item/" + location + "moa_egg_spot"));
    }

    public ItemModelBuilder portalItem(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath()))
                .texture("layer1", modLoc("item/" + location + id.getPath() + "_inside"));
    }

    public ItemModelBuilder eggItem(Supplier<? extends Item> item) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
    }

    public ItemModelBuilder itemBlock(Supplier<? extends Block> block) {
        return withExistingParent(blockName(block), texture(blockName(block)));
    }

    public ItemModelBuilder itemBlock(Supplier<? extends Block> block, String suffix) {
        return withExistingParent(blockName(block), texture(blockName(block) + suffix));
    }

    public ItemModelBuilder pane(Supplier<? extends IronBarsBlock> block, Supplier<? extends GlassBlock> glass, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(glass), location))
                .renderType(new ResourceLocation("translucent"));
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
        return itemOverlayDungeonBlock(block, baseBlock, "lock");
    }

    public ItemModelBuilder itemTrappedDungeonBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        return itemOverlayDungeonBlock(block, baseBlock, "exclamation");
    }

    public ItemModelBuilder itemInvisibleBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        return itemOverlayDungeonBlock(block, baseBlock, "door");
    }

    public ItemModelBuilder itemTreasureBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        return itemOverlayDungeonBlock(block, baseBlock, "treasure");
    }

    public ItemModelBuilder itemOverlayDungeonBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String overlay) {
        return withExistingParent(blockName(block), mcLoc("block/cube"))
                .texture("overlay", texture(overlay, "dungeon/")).texture("face", texture(blockName(baseBlock), "dungeon/"))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).allFaces((direction, builder) -> builder.texture("#face").cullface(direction).end()).end()
                .element().from(0.0F, 0.0F, -0.1F).to(16.0F, 16.0F, -0.1F).rotation().angle(0.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 6.9F).end().face(Direction.NORTH).texture("#overlay").end().end()
                .transforms()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemTransforms.TransformType.GROUND).rotation(90.0F, 0.0F, 0.0F).translation(0.0F, 3.0F, 0.0F).scale(0.25F, 0.25F, 0.25F).end()
                .transform(ItemTransforms.TransformType.GUI).rotation(30.0F, 135.0F, 0.0F).scale(0.625F, 0.625F, 0.625F).end()
                .transform(ItemTransforms.TransformType.FIXED).scale(0.5F, 0.5F, 0.5F).end()
                .end();
    }

    public ItemModelBuilder itemFence(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return withExistingParent(blockName(block), mcLoc("block/fence_inventory"))
                .texture("texture", texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder itemWallBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return wallInventory(blockName(block), texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder translucentItemWallBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return singleTexture(blockName(block), new ResourceLocation(Aether.MODID, BLOCK_FOLDER + "/template_translucent_wall_inventory"), "wall", texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder itemButton(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return withExistingParent(blockName(block), mcLoc("block/button_inventory"))
                .texture("texture", texture(blockName(baseBlock), location));
    }
}
