package com.aether.registry;

import java.util.Collections;
import java.util.Set;

import com.aether.Aether;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

public class AetherLootTables
{
	private static final Set<ResourceLocation> LOOT_TABLES = Sets.newHashSet();
	private static final Set<ResourceLocation> READ_ONLY_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

	public static final ResourceLocation ENTITIES_SHEEPUFF_WHITE = register("entities/sheepuff/white");
	public static final ResourceLocation ENTITIES_SHEEPUFF_ORANGE = register("entities/sheepuff/orange");
	public static final ResourceLocation ENTITIES_SHEEPUFF_MAGENTA = register("entities/sheepuff/magenta");
	public static final ResourceLocation ENTITIES_SHEEPUFF_LIGHT_BLUE = register("entities/sheepuff/light_blue");
	public static final ResourceLocation ENTITIES_SHEEPUFF_YELLOW = register("entities/sheepuff/yellow");
	public static final ResourceLocation ENTITIES_SHEEPUFF_LIME = register("entities/sheepuff/lime");
	public static final ResourceLocation ENTITIES_SHEEPUFF_PINK = register("entities/sheepuff/pink");
	public static final ResourceLocation ENTITIES_SHEEPUFF_GRAY = register("entities/sheepuff/gray");
	public static final ResourceLocation ENTITIES_SHEEPUFF_LIGHT_GRAY = register("entities/sheepuff/light_gray");
	public static final ResourceLocation ENTITIES_SHEEPUFF_CYAN = register("entities/sheepuff/cyan");
	public static final ResourceLocation ENTITIES_SHEEPUFF_PURPLE = register("entities/sheepuff/purple");
	public static final ResourceLocation ENTITIES_SHEEPUFF_BLUE = register("entities/sheepuff/blue");
	public static final ResourceLocation ENTITIES_SHEEPUFF_BROWN = register("entities/sheepuff/brown");
	public static final ResourceLocation ENTITIES_SHEEPUFF_GREEN = register("entities/sheepuff/green");
	public static final ResourceLocation ENTITIES_SHEEPUFF_RED = register("entities/sheepuff/red");
	public static final ResourceLocation ENTITIES_SHEEPUFF_BLACK = register("entities/sheepuff/black");

	public static final ResourceLocation CHESTS_BRONZE_DUNGEON_REWARD = register("chests/bronze_dungeon_reward");
	public static final ResourceLocation CHESTS_SILVER_DUNGEON_REWARD = register("chests/silver_dungeon_reward");
	public static final ResourceLocation CHESTS_GOLD_DUNGEON_REWARD = register("chests/gold_dungeon_reward");
	public static final ResourceLocation CHESTS_BRONZE_DUNGEON_CHEST = register("chests/bronze_dungeon_chest");
	public static final ResourceLocation CHESTS_SILVER_DUNGEON_CHEST = register("chests/silver_dungeon_chest");

	private static ResourceLocation register(String id) {
		return register(new ResourceLocation(Aether.MODID, id));
	}

	private static ResourceLocation register(ResourceLocation id) {
		if (LOOT_TABLES.add(id)) {
			return id;
		}
		else {
			throw new IllegalArgumentException(id + " is already a registered built-in loot table");
		}
	}

	public static Set<ResourceLocation> getReadOnlyLootTables() {
		return READ_ONLY_LOOT_TABLES;
	}
}
