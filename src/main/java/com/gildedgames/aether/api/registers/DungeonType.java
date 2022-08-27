package com.gildedgames.aether.api.registers;

import com.gildedgames.aether.api.AetherDungeonTypes;

public class DungeonType {
	public DungeonType() { }

	public String getId() {
		return AetherDungeonTypes.DUNGEON_TYPE_REGISTRY.get().getKey(this).getPath();
	}
}
