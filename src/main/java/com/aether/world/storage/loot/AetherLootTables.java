package com.aether.world.storage.loot;

import java.util.Collections;
import java.util.Set;

import com.aether.Aether;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

public class AetherLootTables {
	private static final Set<ResourceLocation> LOOT_TABLES = Sets.newHashSet();
	private static final Set<ResourceLocation> READ_ONLY_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

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
