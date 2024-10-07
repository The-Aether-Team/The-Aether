package com.aetherteam.aether.loot;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AetherLoot {
    private static final Set<ResourceKey<LootTable>> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceKey<LootTable>> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_WHITE = register("entities/sheepuff/white");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_ORANGE = register("entities/sheepuff/orange");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_MAGENTA = register("entities/sheepuff/magenta");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_LIGHT_BLUE = register("entities/sheepuff/light_blue");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_YELLOW = register("entities/sheepuff/yellow");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_LIME = register("entities/sheepuff/lime");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_PINK = register("entities/sheepuff/pink");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_GRAY = register("entities/sheepuff/gray");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_LIGHT_GRAY = register("entities/sheepuff/light_gray");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_CYAN = register("entities/sheepuff/cyan");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_PURPLE = register("entities/sheepuff/purple");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_BLUE = register("entities/sheepuff/blue");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_BROWN = register("entities/sheepuff/brown");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_GREEN = register("entities/sheepuff/green");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_RED = register("entities/sheepuff/red");
    public static final ResourceKey<LootTable> ENTITIES_SHEEPUFF_BLACK = register("entities/sheepuff/black");

    public static final ResourceKey<LootTable> BRONZE_DUNGEON = register("chests/dungeon/bronze/bronze_dungeon");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_LOOT = register("chests/dungeon/bronze/bronze_dungeon_loot");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_TRASH = register("chests/dungeon/bronze/bronze_dungeon_trash");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_DISC = register("chests/dungeon/bronze/bronze_dungeon_disc");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_REWARD = register("chests/dungeon/bronze/bronze_dungeon_reward");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_TREASURE = register("chests/dungeon/bronze/bronze_dungeon_treasure");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_NEPTUNE = register("chests/dungeon/bronze/bronze_dungeon_neptune");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_GUMMIES = register("chests/dungeon/bronze/bronze_dungeon_gummies");

    public static final ResourceKey<LootTable> SILVER_DUNGEON = register("chests/dungeon/silver/silver_dungeon");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_LOOT = register("chests/dungeon/silver/silver_dungeon_loot");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_TRASH = register("chests/dungeon/silver/silver_dungeon_trash");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_DISC = register("chests/dungeon/silver/silver_dungeon_disc");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_REWARD = register("chests/dungeon/silver/silver_dungeon_reward");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_TREASURE = register("chests/dungeon/silver/silver_dungeon_treasure");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_VALKYRIE = register("chests/dungeon/silver/silver_dungeon_valkyrie");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_GRAVITITE = register("chests/dungeon/silver/silver_dungeon_gravitite");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_CAPE_CONFIG = register("chests/dungeon/silver/silver_dungeon_cape_config");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_GUMMIES = register("chests/dungeon/silver/silver_dungeon_gummies");

    public static final ResourceKey<LootTable> GOLD_DUNGEON_REWARD = register("chests/dungeon/gold/gold_dungeon_reward");
    public static final ResourceKey<LootTable> GOLD_DUNGEON_TREASURE = register("chests/dungeon/gold/gold_dungeon_treasure");

    public static final ResourceKey<LootTable> RUINED_PORTAL = register("chests/ruined_portal");

    public static final ResourceKey<LootTable> ENTER_AETHER = register("advancements/enter_aether");

    public static final ResourceKey<LootTable> STRIP_GOLDEN_OAK = register("stripping/strip_golden_oak");

    public static final ResourceKey<LootTable> WHIRLWIND_JUNK = register("selectors/whirlwind_junk");
    public static final ResourceKey<LootTable> EVIL_WHIRLWIND_JUNK = register("selectors/evil_whirlwind_junk");

    private static ResourceKey<LootTable> register(String id) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Aether.MODID, id)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> id) {
        if (LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }
}
