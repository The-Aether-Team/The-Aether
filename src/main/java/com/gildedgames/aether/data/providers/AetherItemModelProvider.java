package com.gildedgames.aether.data.providers;

import com.gildedgames.aether.Aether;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class AetherItemModelProvider extends ItemModelProvider {
    public AetherItemModelProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }

    public String blockName(Block block) {
        ResourceLocation location = ForgeRegistries.BLOCKS.getKey(block);
        if (location != null) {
            return location.getPath();
        } else {
            throw new IllegalStateException("Unknown block: " + block.toString());
        }
    }

    public String itemName(Item item) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(item);
        if (location != null) {
            return location.getPath();
        } else {
            throw new IllegalStateException("Unknown item: " + item.toString());
        }
    }

    protected ResourceLocation texture(String name) {
        return this.modLoc("block/" + name);
    }

    protected ResourceLocation texture(String name, String location) {
        return this.modLoc("block/" + location + name);
    }

    public void item(Item item, String location) {
        this.withExistingParent(this.itemName(item), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + this.itemName(item)));
    }

    public void handheldItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)));
    }

    public void lanceItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .transforms()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND).rotation(0.0F, -90.0F, 45.0F).translation(0.0F, 1.0F, -5.0F).scale(0.85F, 0.85F, 0.85F).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND).rotation(0.0F, 90.0F, -45.0F).translation(0.0F, 1.0F, -5.0F).scale(0.85F, 0.85F, 0.85F).end()
                .end();
    }

    public void nameableWeapon(Item item, String location, String renamedVariant) {
        this.withExistingParent(renamedVariant, this.mcLoc("item/handheld")).texture("layer0", this.modLoc("item/" + location + renamedVariant));
        this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .override().predicate(new ResourceLocation(Aether.MODID, "named"), 1).model(this.getExistingFile(modLoc("item/" + renamedVariant))).end();
    }

    public void dartShooterItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .transforms()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND).rotation(0.0F, -90.0F, 45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F, 0.85F, 0.85F).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND).rotation(0.0F, 90.0F, -45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F, 0.85F, 0.85F).end()
                .end();
    }

    public void bowItem(Item item, String location) {
        this.withExistingParent(this.itemName(item) + "_pulling_0", this.mcLoc("item/bow")).texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_pulling_0"));
        this.withExistingParent(this.itemName(item) + "_pulling_1", this.mcLoc("item/bow")).texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_pulling_1"));
        this.withExistingParent(this.itemName(item) + "_pulling_2", this.mcLoc("item/bow")).texture("layer0", this.modLoc("item/" + location + this.itemName(item) + "_pulling_2"));
        this.withExistingParent(this.itemName(item), this.mcLoc("item/bow"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .override().predicate(new ResourceLocation("pulling"), 1).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_0"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_1"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9F).model(this.getExistingFile(this.modLoc("item/" + this.itemName(item) + "_pulling_2"))).end();
    }

    public void dyedItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .texture("layer1", this.modLoc("item/" + location + this.itemName(item) + "_overlay"));
    }

    public void keyItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .transforms()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND).rotation(90.0F, -90.0F, 25.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND).rotation(90.0F, 90.0F, -25.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end()
                .end();
    }

    public void moaEggItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + "moa_egg"))
                .texture("layer1", this.modLoc("item/" + location + "moa_egg_spot"));
    }

    public void portalItem(Item item, String location) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .texture("layer1", this.modLoc("item/" + location + this.itemName(item) + "_inside"));
    }

    public void eggItem(Item item) {
        this.withExistingParent(this.itemName(item), this.mcLoc("item/template_spawn_egg"));
    }

    public void itemBlock(Block block) {
        this.withExistingParent(this.blockName(block), this.texture(this.blockName(block)));
    }

    public void itemBlock(Block block, String suffix) {
        this.withExistingParent(this.blockName(block), this.texture(this.blockName(block) + suffix));
    }

    public void pane(Block block, Block glass, String location) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated"))
                .texture("layer0", this.texture(this.blockName(glass), location))
                .renderType(new ResourceLocation("translucent"));
    }

    public void itemTorch(Block block, String location) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated"))
                .texture("layer0", this.texture(this.blockName(block), location));
    }

    public void itemBlockFlat(Block block, String location) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated"))
                .texture("layer0", this.texture(this.blockName(block), location));
    }

    public void itemLockedDungeonBlock(Block block, Block baseBlock) {
        this.itemOverlayDungeonBlock(block, baseBlock, "lock");
    }

    public void itemTrappedDungeonBlock(Block block, Block baseBlock) {
        this.itemOverlayDungeonBlock(block, baseBlock, "exclamation");
    }

    public void itemBossDoorwayDungeonBlock(Block block, Block baseBlock) {
        this.itemOverlayDungeonBlock(block, baseBlock, "door");
    }

    public void itemTreasureDoorwayDungeonBlock(Block block, Block baseBlock) {
        this.itemOverlayDungeonBlock(block, baseBlock, "treasure");
    }

    public void itemOverlayDungeonBlock(Block block, Block baseBlock, String overlay) {
        this.withExistingParent(this.blockName(block), this.mcLoc("block/cube"))
                .texture("overlay", this.texture(overlay, "dungeon/")).texture("face", this.texture(this.blockName(baseBlock), "dungeon/"))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).allFaces((direction, builder) -> builder.texture("#face").cullface(direction).end()).end()
                .element().from(0.0F, 0.0F, -0.1F).to(16.0F, 16.0F, -0.1F).rotation().angle(0.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 6.9F).end().face(Direction.NORTH).texture("#overlay").emissivity(15, 15).end().end()
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

    public void lookalikeBlock(Block block, ResourceLocation lookalike) {
        this.withExistingParent(this.blockName(block), lookalike);
    }

    public void itemFence(Block block, Block baseBlock, String location) {
        this.withExistingParent(this.blockName(block), this.mcLoc("block/fence_inventory"))
                .texture("texture", this.texture(this.blockName(baseBlock), location));
    }

    public void itemButton(Block block, Block baseBlock, String location) {
        this.withExistingParent(this.blockName(block), this.mcLoc("block/button_inventory"))
                .texture("texture", this.texture(this.blockName(baseBlock), location));
    }

    public void itemWallBlock(Block block, Block baseBlock, String location) {
        this.wallInventory(this.blockName(block), this.texture(this.blockName(baseBlock), location));
    }

    public void translucentItemWallBlock(Block block, Block baseBlock, String location) {
        this.singleTexture(this.blockName(block), new ResourceLocation(Aether.MODID, BLOCK_FOLDER + "/template_translucent_wall_inventory"), "wall", this.texture(this.blockName(baseBlock), location));
    }
}
