package com.aether.item;

import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;

import com.aether.api.dungeon.DungeonType;

import net.minecraft.item.Item;

public class DungeonKeyItem extends Item {
	private final Supplier<DungeonType> dungeonTypeGetter;
	
	public DungeonKeyItem(Supplier<DungeonType> dungeonTypeGetter, Item.Properties properties) {
		super(properties);
		this.dungeonTypeGetter = Validate.notNull(dungeonTypeGetter);
	}
	
	public DungeonType getDungeonType() {
		return dungeonTypeGetter.get();
	}

}
