package com.gildedgames.aether.blockentity;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherBlockEntityTypes
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Aether.MODID);

	public static final RegistryObject<BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITIES.register("altar", () ->
			BlockEntityType.Builder.of(AltarBlockEntity::new, AetherBlocks.ALTAR.get()).build(null));
	public static final RegistryObject<BlockEntityType<FreezerBlockEntity>> FREEZER = BLOCK_ENTITIES.register("freezer", () ->
			BlockEntityType.Builder.of(FreezerBlockEntity::new, AetherBlocks.FREEZER.get()).build(null));
	public static final RegistryObject<BlockEntityType<IncubatorBlockEntity>> INCUBATOR = BLOCK_ENTITIES.register("incubator", () ->
			BlockEntityType.Builder.of(IncubatorBlockEntity::new, AetherBlocks.INCUBATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<ChestMimicBlockEntity>> CHEST_MIMIC = BLOCK_ENTITIES.register("chest_mimic", () ->
			BlockEntityType.Builder.of(ChestMimicBlockEntity::new, AetherBlocks.CHEST_MIMIC.get()).build(null));
	public static final RegistryObject<BlockEntityType<TreasureChestBlockEntity>> TREASURE_CHEST = BLOCK_ENTITIES.register("treasure_chest", () ->
			BlockEntityType.Builder.of(TreasureChestBlockEntity::new, AetherBlocks.TREASURE_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<SkyrootBedBlockEntity>> SKYROOT_BED = BLOCK_ENTITIES.register("skyroot_bed", () ->
			BlockEntityType.Builder.of(SkyrootBedBlockEntity::new, AetherBlocks.SKYROOT_BED.get()).build(null));
	public static final RegistryObject<BlockEntityType<SkyrootSignBlockEntity>> SKYROOT_SIGN = BLOCK_ENTITIES.register("skyroot_sign", () ->
			BlockEntityType.Builder.of(SkyrootSignBlockEntity::new, AetherBlocks.SKYROOT_WALL_SIGN.get(), AetherBlocks.SKYROOT_SIGN.get()).build(null));
	public static final RegistryObject<BlockEntityType<SunAltarBlockEntity>> SUN_ALTAR = BLOCK_ENTITIES.register("sun_altar", () ->
			BlockEntityType.Builder.of(SunAltarBlockEntity::new, AetherBlocks.SUN_ALTAR.get()).build(null));
}
