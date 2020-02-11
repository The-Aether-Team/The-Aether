package com.aether;

import com.aether.capability.CapabilityPlayerAether;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonProxy {

	public void commonSetup(FMLCommonSetupEvent event) {
		CapabilityPlayerAether.register();
	}
	
	public void clientSetup(FMLClientSetupEvent event) {
		
	}
	
}
