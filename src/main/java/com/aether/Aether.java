package com.aether;

import com.aether.client.ClientProxy;

import com.aether.registry.*;
import com.aether.world.gen.feature.AetherFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Aether.MODID)
public class Aether
{
	public static final String MODID = "aether";
	public static final Logger LOGGER = LogManager.getLogger();
	
	private static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public Aether() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.register(proxy);
		MinecraftForge.EVENT_BUS.register(CommonProxy.class);

		DeferredRegister<?>[] registers = {
				AetherBlocks.BLOCKS,
				AetherFeatures.FEATURES,
				AetherEntityTypes.ENTITIES,
				AetherItems.ITEMS,
				AetherParticleTypes.PARTICLES,
				AetherPOI.POI,
				AetherSoundEvents.SOUNDS,
				AetherContainerTypes.CONTAINERS,
				AetherTileEntityTypes.TILE_ENTITIES,
		};

		for (DeferredRegister<?> register : registers) {
			register.register(modEventBus);
		}
	}
}
