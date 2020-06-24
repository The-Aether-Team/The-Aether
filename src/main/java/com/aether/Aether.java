package com.aether;

import com.aether.biome.AetherBiomes;
import com.aether.client.ClientProxy;

import com.aether.world.AetherDimensions;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

@Mod(Aether.MODID)
public class Aether {

	public static final String MODID = "aether";

	public static DimensionType aether_dimension;
	
	private static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);;
	
	public Aether() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(proxy::commonSetup);
		modEventBus.addListener(proxy::clientSetup);
		AetherDimensions.MOD_DIMENSIONS.register(modEventBus);
		AetherBiomes.BIOMES.register(modEventBus);
	}
	
	public static final Rarity AETHER_LOOT = Rarity.create("AETHER_LOOT", TextFormatting.GREEN);

	@Mod.EventBusSubscriber(modid = MODID)
	public static class ForgeEventBus {

		@SubscribeEvent
		public static void registerModDimension(final RegisterDimensionsEvent event) {
			ResourceLocation aetherLoc = new ResourceLocation(MODID, "aether");
			if(DimensionType.byName(aetherLoc) == null) {
				aether_dimension = DimensionManager.registerDimension(aetherLoc, AetherDimensions.AETHER.get(), null, true);
				DimensionManager.keepLoaded(aether_dimension, false);
			}
			else {
				aether_dimension = DimensionType.byName(aetherLoc);
			}

		}

	}
	
}
