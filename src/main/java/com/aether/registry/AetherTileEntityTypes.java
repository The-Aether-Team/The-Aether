package com.aether.registry;

import com.aether.Aether;
import com.aether.entity.tile.*;

import com.google.common.collect.Sets;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherTileEntityTypes
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Aether.MODID);

	public static final RegistryObject<TileEntityType<EnchanterTileEntity>> ENCHANTER = TILE_ENTITIES.register("enchanter", () ->
			new TileEntityType<>(EnchanterTileEntity::new, Sets.newHashSet(AetherBlocks.ENCHANTER.get()), null));
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
}
