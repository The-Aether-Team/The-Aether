package com.aether;

import com.aether.client.ClientProxy;

import com.aether.world.gen.feature.AetherFeatures;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Aether.MODID)
public class Aether {

	public static final String MODID = "aether";
	
	private static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public Aether() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.register(proxy);
		MinecraftForge.EVENT_BUS.register(CommonProxy.class);
		AetherFeatures.FEATURES.register(modEventBus);
	}
	
	public static final Rarity AETHER_LOOT = Rarity.create("AETHER_LOOT", TextFormatting.GREEN);
	
}
