package com.gildedgames.aether.common.item.misc;

import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;

import com.gildedgames.aether.core.api.registers.DungeonType;

import net.minecraft.item.Item;

public class DungeonKeyItem extends Item
{
	private final Supplier<DungeonType> dungeonTypeGetter;
	
	public DungeonKeyItem(Supplier<DungeonType> dungeonTypeGetter, Item.Properties properties) {
		super(properties);
		this.dungeonTypeGetter = Validate.notNull(dungeonTypeGetter);
	}
	
	public DungeonType getDungeonType() {
		return dungeonTypeGetter.get();
	}
}
