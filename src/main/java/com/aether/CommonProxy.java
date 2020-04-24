package com.aether;

import com.aether.capability.AetherCapabilities;
import com.aether.world.storage.loot.conditions.LocationCheck;
import com.aether.world.storage.loot.functions.DoubleDrops;

import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonProxy {

	public void commonSetup(FMLCommonSetupEvent event) {
		AetherCapabilities.register();
		registerLootTableFunctions();
		registerLootTableConditions();
	}
	
	public void clientSetup(FMLClientSetupEvent event) {
		
	}
	
	protected void registerLootTableFunctions() {
		LootFunctionManager.registerFunction(new DoubleDrops.Serializer());
	}
	
	protected void registerLootTableConditions() {
		LootConditionManager.registerCondition(new LocationCheck.Serializer());
	}
	
}
