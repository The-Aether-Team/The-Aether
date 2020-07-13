package com.aether;

import com.aether.capability.AetherCapabilities;
import com.aether.world.storage.loot.functions.DoubleDrops;

import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonProxy {

	@SubscribeEvent
	public void commonSetup(FMLCommonSetupEvent event) {
		AetherCapabilities.register();
		registerLootTableFunctions();
		registerLootTableConditions();
	}
	
	@SubscribeEvent
	public void clientSetup(FMLClientSetupEvent event) {
		
	}
	
	protected void registerLootTableFunctions() {
		LootFunctionManager.registerFunction(new DoubleDrops.Serializer());
	}
	
	protected void registerLootTableConditions() {
//		LootConditionManager.registerCondition(new ######.Serializer());
	}
	
}
