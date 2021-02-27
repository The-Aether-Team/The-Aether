package com.gildedgames.aether.api.dungeon;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.registry.AetherLoot;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class DungeonTypes
{
	public static final DeferredRegister<DungeonType> DUNGEON_TYPES = DeferredRegister.create(DungeonType.class, Aether.MODID);

	public static final RegistryObject<DungeonType> BRONZE = DUNGEON_TYPES.register("bronze",
			() -> new DungeonType(AetherLoot.CHESTS_BRONZE_DUNGEON_REWARD));
	public static final RegistryObject<DungeonType> SILVER = DUNGEON_TYPES.register("silver",
			() -> new DungeonType(AetherLoot.CHESTS_SILVER_DUNGEON_REWARD));
	public static final RegistryObject<DungeonType> GOLD = DUNGEON_TYPES.register("gold",
			() -> new DungeonType(AetherLoot.CHESTS_GOLD_DUNGEON_REWARD));
}
