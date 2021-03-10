package com.gildedgames.aether.core.api.registers;

import org.apache.commons.lang3.Validate;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class DungeonType extends ForgeRegistryEntry<DungeonType>
{
	private final ResourceLocation lootTable;
	
	public DungeonType(ResourceLocation lootTable) {
		this.lootTable = Validate.notNull(lootTable, "A dungeon's loot table was null.");
	}
	
	public ResourceLocation getLootTable() {
		return this.lootTable;
	}
	
	public String getTranslationKey() {
		return "dungeon." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath();
	}
}
