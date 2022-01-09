package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.entity.tile.SkyrootSignTileEntity;
import com.gildedgames.aether.common.entity.tile.*;
import com.google.common.collect.Sets;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherTileEntityTypes
{
	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Aether.MODID);

	public static final RegistryObject<BlockEntityType<AltarTileEntity>> ALTAR = TILE_ENTITIES.register("altar", () ->
			new BlockEntityType(AltarTileEntity::new, Sets.newHashSet(AetherBlocks.ALTAR.get()), null));
	public static final RegistryObject<BlockEntityType<FreezerTileEntity>> FREEZER = TILE_ENTITIES.register("freezer", () ->
			new BlockEntityType(FreezerTileEntity::new, Sets.newHashSet(AetherBlocks.FREEZER.get()), null));
	public static final RegistryObject<BlockEntityType<IncubatorTileEntity>> INCUBATOR = TILE_ENTITIES.register("incubator", () ->
			new BlockEntityType(IncubatorTileEntity::new, Sets.newHashSet(AetherBlocks.INCUBATOR.get()), null));
	public static final RegistryObject<BlockEntityType<ChestMimicBlockEntity>> CHEST_MIMIC = TILE_ENTITIES.register("chest_mimic", () ->
			new BlockEntityType(ChestMimicBlockEntity::new, Sets.newHashSet(AetherBlocks.CHEST_MIMIC.get()), null));
	public static final RegistryObject<BlockEntityType<TreasureChestBlockEntity>> TREASURE_CHEST = TILE_ENTITIES.register("treasure_chest", () ->
			new BlockEntityType(TreasureChestBlockEntity::new, Sets.newHashSet(AetherBlocks.TREASURE_CHEST.get()), null));
	public static final RegistryObject<BlockEntityType<SkyrootBedBlockEntity>> SKYROOT_BED = TILE_ENTITIES.register("skyroot_bed", () ->
			new BlockEntityType(SkyrootBedBlockEntity::new, Sets.newHashSet(AetherBlocks.SKYROOT_BED.get()), null));
	public static final RegistryObject<BlockEntityType<SkyrootSignTileEntity>> SKYROOT_SIGN = TILE_ENTITIES.register("custom_sign", () ->
			BlockEntityType.Builder.of(SkyrootSignTileEntity::new, AetherBlocks.SKYROOT_WALL_SIGN.get(), AetherBlocks.SKYROOT_SIGN.get()).build(null));
}
