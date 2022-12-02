package com.gildedgames.aether.loot;

import java.util.HashSet;
import java.util.Set;

import com.gildedgames.aether.Aether;

import net.minecraft.resources.ResourceLocation;

public class AetherLoot {
	private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();

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

	public static final ResourceLocation BRONZE_DUNGEON = register("chests/dungeon/bronze/bronze_dungeon");
	public static final ResourceLocation BRONZE_DUNGEON_SUB_1 = register("chests/dungeon/bronze/bronze_dungeon_sub_1");
	public static final ResourceLocation BRONZE_DUNGEON_SUB_2 = register("chests/dungeon/bronze/bronze_dungeon_sub_2");
	public static final ResourceLocation BRONZE_DUNGEON_SUB_3 = register("chests/dungeon/bronze/bronze_dungeon_sub_3");
	public static final ResourceLocation BRONZE_DUNGEON_SUB_4 = register("chests/dungeon/bronze/bronze_dungeon_sub_4");
	public static final ResourceLocation BRONZE_DUNGEON_REWARD = register("chests/dungeon/bronze/bronze_dungeon_reward");
	public static final ResourceLocation BRONZE_DUNGEON_REWARD_SUB_1 = register("chests/dungeon/bronze/bronze_dungeon_reward_sub_1");

	public static final ResourceLocation SILVER_DUNGEON = register("chests/dungeon/silver/silver_dungeon");
	public static final ResourceLocation SILVER_DUNGEON_SUB_1 = register("chests/dungeon/silver/silver_dungeon_sub_1");
	public static final ResourceLocation SILVER_DUNGEON_SUB_2 = register("chests/dungeon/silver/silver_dungeon_sub_2");
	public static final ResourceLocation SILVER_DUNGEON_SUB_3 = register("chests/dungeon/silver/silver_dungeon_sub_3");
	public static final ResourceLocation SILVER_DUNGEON_SUB_4 = register("chests/dungeon/silver/silver_dungeon_sub_4");
	public static final ResourceLocation SILVER_DUNGEON_SUB_5 = register("chests/dungeon/silver/silver_dungeon_sub_5");
	public static final ResourceLocation SILVER_DUNGEON_SUB_6 = register("chests/dungeon/silver/silver_dungeon_sub_6");
	public static final ResourceLocation SILVER_DUNGEON_SUB_7 = register("chests/dungeon/silver/silver_dungeon_sub_7");
	public static final ResourceLocation SILVER_DUNGEON_REWARD = register("chests/dungeon/silver/silver_dungeon_reward");
	public static final ResourceLocation SILVER_DUNGEON_REWARD_SUB_1 = register("chests/dungeon/silver/silver_dungeon_reward_sub_1");
	public static final ResourceLocation SILVER_DUNGEON_REWARD_SUB_2 = register("chests/dungeon/silver/silver_dungeon_reward_sub_2");

	public static final ResourceLocation GOLD_DUNGEON_REWARD = register("chests/dungeon/gold/gold_dungeon_reward");
	public static final ResourceLocation GOLD_DUNGEON_REWARD_SUB_1 = register("chests/dungeon/gold/gold_dungeon_reward_sub_1");
	public static final ResourceLocation GOLD_DUNGEON_REWARD_SUB_2 = register("chests/dungeon/gold/gold_dungeon_reward_sub_2");
	public static final ResourceLocation GOLD_DUNGEON_REWARD_SUB_3 = register("chests/dungeon/gold/gold_dungeon_reward_sub_3");
	public static final ResourceLocation GOLD_DUNGEON_REWARD_SUB_4 = register("chests/dungeon/gold/gold_dungeon_reward_sub_4");
	public static final ResourceLocation GOLD_DUNGEON_REWARD_SUB_5 = register("chests/dungeon/gold/gold_dungeon_reward_sub_5");

	public static final ResourceLocation ENTER_AETHER = register("advancements/enter_aether");

	public static final ResourceLocation STRIP_GOLDEN_OAK = register("stripping/strip_golden_oak");

	public static final ResourceLocation WHIRLWIND_JUNK = register("selectors/whirlwind_junk");
	public static final ResourceLocation EVIL_WHIRLWIND_JUNK = register("selectors/evil_whirlwind_junk");

	private static ResourceLocation register(String id) {
		return register(new ResourceLocation(Aether.MODID, id));
	}

	private static ResourceLocation register(ResourceLocation id) {
		if (LOOT_TABLES.add(id)) {
			return id;
		} else {
			throw new IllegalArgumentException(id + " is already a registered built-in loot table");
		}
	}
}
