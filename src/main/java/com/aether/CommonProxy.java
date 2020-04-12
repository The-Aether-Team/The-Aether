package com.aether;

import com.aether.capability.AetherCapabilities;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonProxy {

	public void commonSetup(FMLCommonSetupEvent event) {
		AetherCapabilities.register();
		registerLootTableFunctions();
	}
	
	public void clientSetup(FMLClientSetupEvent event) {
		
	}
	
	protected void registerLootTableFunctions() {
	//	LootFunctionManager.registerFunction(serializer);
	}
	
}
