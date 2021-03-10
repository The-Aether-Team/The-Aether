package com.gildedgames.aether.core.registry;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.core.api.registers.DungeonType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherDungeonTypes
{
	public static final DeferredRegister<DungeonType> DUNGEON_TYPES = DeferredRegister.create(DungeonType.class, Aether.MODID);

	public static final RegistryObject<DungeonType> BRONZE = DUNGEON_TYPES.register("bronze",
			() -> new DungeonType(AetherLoot.CHESTS_BRONZE_DUNGEON_REWARD));
	public static final RegistryObject<DungeonType> SILVER = DUNGEON_TYPES.register("silver",
			() -> new DungeonType(AetherLoot.CHESTS_SILVER_DUNGEON_REWARD));
	public static final RegistryObject<DungeonType> GOLD = DUNGEON_TYPES.register("gold",
			() -> new DungeonType(AetherLoot.CHESTS_GOLD_DUNGEON_REWARD));
}
