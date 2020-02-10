package com.aether;

import com.aether.capability.CapabilityPlayerAether;
import com.aether.client.ClientProxy;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Aether.MODID)
public class Aether {

	public static final String MODID = "aether_legacy";
	
	public static final CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);;
	
	public Aether() {	
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}
	
	private void setup(FMLCommonSetupEvent event) {
		CapabilityPlayerAether.register();
		
		proxy.setup();
	}
	
}
