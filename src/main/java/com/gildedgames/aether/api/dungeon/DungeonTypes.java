package com.gildedgames.aether.api.dungeon;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.registry.AetherLoot;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

//TODO: Convert to DeferredRegister
@ObjectHolder(Aether.MODID)
public class DungeonTypes {

	public static final DungeonType BRONZE = null;
	public static final DungeonType SILVER = null;
	public static final DungeonType GOLD = null;
	
	@EventBusSubscriber(modid = Aether.MODID, bus = Bus.MOD)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerAetherDungeonTypes(RegistryEvent.Register<DungeonType> event) {
			event.getRegistry().registerAll(new DungeonType[] {
				
				dungeonType("bronze", new DungeonType(AetherLoot.CHESTS_BRONZE_DUNGEON_REWARD)),
				dungeonType("silver", new DungeonType(AetherLoot.CHESTS_SILVER_DUNGEON_REWARD)),
				dungeonType("gold", new DungeonType(AetherLoot.CHESTS_GOLD_DUNGEON_REWARD)),
				
			});
		}
		
		public static DungeonType dungeonType(String name, DungeonType type) {
			type.setRegistryName(name);
			return type;
		}
		
	}
	
}
