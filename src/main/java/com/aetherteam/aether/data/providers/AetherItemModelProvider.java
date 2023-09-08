package com.aetherteam.aether.data.providers;

//import com.aetherteam.aether.Aether;
//import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
//import net.minecraft.core.Direction;
//import net.minecraft.data.PackOutput;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemDisplayContext;
//import net.minecraft.world.level.block.Block;
//import net.minecraftforge.common.data.ExistingFileHelper;
//
//public abstract class AetherItemModelProvider extends NitrogenItemModelProvider {
//    public AetherItemModelProvider(PackOutput output, String id, ExistingFileHelper helper) {
//        super(output, id, helper);
//    }
//
//    public void lanceItem(Item item, String location) {
//        this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld"))
//                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
//                .transforms()
//                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0.0F, -90.0F, 45.0F).translation(0.0F, 1.0F, -5.0F).scale(0.85F, 0.85F, 0.85F).end()
//                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(0.0F, 90.0F, -45.0F).translation(0.0F, 1.0F, -5.0F).scale(0.85F, 0.85F, 0.85F).end()
//                .end();
//    }
//
//    public void nameableWeapon(Item item, String location, String renamedVariant) {
//        this.withExistingParent(renamedVariant, this.mcLoc("item/handheld")).texture("layer0", this.modLoc("item/" + location + renamedVariant));
//        this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld"))
//                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
//                .override().predicate(new ResourceLocation(Aether.MODID, "named"), 1).model(this.getExistingFile(modLoc("item/" + renamedVariant))).end();
//    }
//
//    public void dartShooterItem(Item item, String location) {
//        this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld"))
//                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
//                .transforms()
//                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0.0F, -90.0F, 45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F, 0.85F, 0.85F).end()
//                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(0.0F, 90.0F, -45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F, 0.85F, 0.85F).end()
//                .end();
//    }
//
//    public void rotatedItem(Item item, String location) {
//        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
//                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
//                .transforms()
//                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(90.0F, -90.0F, 25.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end()
//                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(90.0F, 90.0F, -25.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end()
//                .end();
//    }
//
//    public void moaEggItem(Item item, String location) {
//        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
//                .texture("layer0", this.modLoc("item/" + location + "moa_egg"))
//                .texture("layer1", this.modLoc("item/" + location + "moa_egg_spot"));
//    }
//
//    public void portalItem(Item item, String location) {
//        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
//                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
//                .texture("layer1", this.modLoc("item/" + location + this.itemName(item) + "_inside"));
//    }
//
//    public void itemLockedDungeonBlock(Block block, Block baseBlock) {
//        this.itemOverlayDungeonBlock(block, baseBlock, "lock");
//    }
//
//    public void itemTrappedDungeonBlock(Block block, Block baseBlock) {
//        this.itemOverlayDungeonBlock(block, baseBlock, "exclamation");
//    }
//
//    public void itemBossDoorwayDungeonBlock(Block block, Block baseBlock) {
//        this.itemOverlayDungeonBlock(block, baseBlock, "door");
//    }
//
//    public void itemTreasureDoorwayDungeonBlock(Block block, Block baseBlock) {
//        this.itemOverlayDungeonBlock(block, baseBlock, "treasure");
//    }
//
//    public void itemOverlayDungeonBlock(Block block, Block baseBlock, String overlay) {
//        this.withExistingParent(this.blockName(block), this.mcLoc("block/cube"))
//                .texture("overlay", new ResourceLocation(Aether.MODID, "block/dungeon/" + overlay)).texture("face", this.texture(this.blockName(baseBlock), "dungeon/"))
//                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).allFaces((direction, builder) -> builder.texture("#face").cullface(direction).end()).end()
//                .element().from(0.0F, 0.0F, -0.1F).to(16.0F, 16.0F, -0.1F).rotation().angle(0.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 6.9F).end().face(Direction.NORTH).texture("#overlay").emissivity(15, 15).end().end()
//                .transforms()
//                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
//                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
//                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
//                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
//                .transform(ItemDisplayContext.GROUND).rotation(90.0F, 0.0F, 0.0F).translation(0.0F, 3.0F, 0.0F).scale(0.25F, 0.25F, 0.25F).end()
//                .transform(ItemDisplayContext.GUI).rotation(30.0F, 135.0F, 0.0F).scale(0.625F, 0.625F, 0.625F).end()
//                .transform(ItemDisplayContext.FIXED).scale(0.5F, 0.5F, 0.5F).end()
//                .end();
//    }
//}
