package com.aether.tileentity;

import com.aether.Aether;
import com.aether.block.AetherBlocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherTileEntityTypes {

	public static final TileEntityType<EnchanterTileEntity> ENCHANTER = null;
	public static final TileEntityType<FreezerTileEntity> FREEZER = null;
	public static final TileEntityType<IncubatorTileEntity> INCUBATOR = null;
	public static final TileEntityType<ChestMimicTileEntity> CHEST_MIMIC = null;
	public static final TileEntityType<TreasureChestTileEntity> TREASURE_CHEST = null;
	
	@EventBusSubscriber(modid = Aether.MODID, bus = Bus.MOD)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
			event.getRegistry().registerAll(new TileEntityType[] {
				
				tileEntity("enchanter", TileEntityType.Builder.<EnchanterTileEntity>create(EnchanterTileEntity::new, AetherBlocks.ENCHANTER)),
				tileEntity("freezer", TileEntityType.Builder.<FreezerTileEntity>create(FreezerTileEntity::new, AetherBlocks.FREEZER)),
				tileEntity("incubator", TileEntityType.Builder.<IncubatorTileEntity>create(IncubatorTileEntity::new, AetherBlocks.INCUBATOR)),
				tileEntity("chest_mimic", TileEntityType.Builder.<ChestMimicTileEntity>create(ChestMimicTileEntity::new, AetherBlocks.CHEST_MIMIC)),
				tileEntity("treasure_chest", TileEntityType.Builder.<TreasureChestTileEntity>create(TreasureChestTileEntity::new, AetherBlocks.TREASURE_CHEST)),
				
			});
		}
		
		private static <T extends TileEntity> TileEntityType<T> tileEntity(String name, TileEntityType.Builder<T> tileEntityTypeBuilder) {
			return tileEntity(name, tileEntityTypeBuilder.build(null));
		}
		
		private static <T extends TileEntity> TileEntityType<T> tileEntity(String name, TileEntityType<T> tileEntityType) {
			tileEntityType.setRegistryName(name);
			return tileEntityType;
		}
		
	}
	
}
