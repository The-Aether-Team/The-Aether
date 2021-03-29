package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.entity.tile.SkyrootSignTileEntity;
import com.gildedgames.aether.common.entity.tile.*;
import com.google.common.collect.Sets;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherTileEntityTypes
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Aether.MODID);

	public static final RegistryObject<TileEntityType<AltarTileEntity>> ALTAR = TILE_ENTITIES.register("altar", () ->
			new TileEntityType<>(AltarTileEntity::new, Sets.newHashSet(AetherBlocks.ALTAR.get()), null));
	public static final RegistryObject<TileEntityType<FreezerTileEntity>> FREEZER = TILE_ENTITIES.register("freezer", () ->
			new TileEntityType<>(FreezerTileEntity::new, Sets.newHashSet(AetherBlocks.FREEZER.get()), null));
	public static final RegistryObject<TileEntityType<IncubatorTileEntity>> INCUBATOR = TILE_ENTITIES.register("incubator", () ->
			new TileEntityType<>(IncubatorTileEntity::new, Sets.newHashSet(AetherBlocks.INCUBATOR.get()), null));
	public static final RegistryObject<TileEntityType<ChestMimicTileEntity>> CHEST_MIMIC = TILE_ENTITIES.register("chest_mimic", () ->
			new TileEntityType<>(ChestMimicTileEntity::new, Sets.newHashSet(AetherBlocks.CHEST_MIMIC.get()), null));
	public static final RegistryObject<TileEntityType<TreasureChestTileEntity>> TREASURE_CHEST = TILE_ENTITIES.register("treasure_chest", () ->
			new TileEntityType<>(TreasureChestTileEntity::new, Sets.newHashSet(AetherBlocks.TREASURE_CHEST.get()), null));
	public static final RegistryObject<TileEntityType<SkyrootBedTileEntity>> SKYROOT_BED = TILE_ENTITIES.register("skyroot_bed", () ->
			new TileEntityType<>(SkyrootBedTileEntity::new, Sets.newHashSet(AetherBlocks.SKYROOT_BED.get()), null));
	public static final RegistryObject<TileEntityType<SkyrootSignTileEntity>> SKYROOT_SIGN = TILE_ENTITIES.register("custom_sign", () ->
			TileEntityType.Builder.of(SkyrootSignTileEntity::new, AetherBlocks.SKYROOT_WALL_SIGN.get(), AetherBlocks.SKYROOT_SIGN.get()).build(null));
}
