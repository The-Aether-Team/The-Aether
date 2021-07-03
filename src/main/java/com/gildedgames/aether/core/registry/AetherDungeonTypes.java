package com.gildedgames.aether.core.registry;

import com.gildedgames.aether.core.api.registers.DungeonType;

import java.util.HashMap;
import java.util.Map;

public class AetherDungeonTypes
{
	public static Map<String, DungeonType> DUNGEON_TYPES = new HashMap<>();

	public static final DungeonType BRONZE = register("bronze");
	public static final DungeonType SILVER = register("silver");
	public static final DungeonType GOLD = register("gold");

	public static DungeonType register(String registryName) {
		DungeonType dungeonType = new DungeonType(registryName);
		DUNGEON_TYPES.put(registryName, dungeonType);
		return dungeonType;
	}
}
