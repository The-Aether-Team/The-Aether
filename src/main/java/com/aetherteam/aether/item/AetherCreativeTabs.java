package com.aetherteam.aether.item;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Aether.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_BUILDING_BLOCKS = CREATIVE_MODE_TABS.register("building_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsAfter(new ResourceLocation(Aether.MODID, "dungeon_blocks"))
            .icon(() -> new ItemStack(AetherBlocks.HOLYSTONE_BRICKS.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".building_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherBlocks.SKYROOT_LOG.get());
                output.accept(AetherBlocks.SKYROOT_WOOD.get());
                output.accept(AetherBlocks.STRIPPED_SKYROOT_LOG.get());
                output.accept(AetherBlocks.STRIPPED_SKYROOT_WOOD.get());
                output.accept(AetherBlocks.SKYROOT_PLANKS.get());
                output.accept(AetherBlocks.SKYROOT_STAIRS.get());
                output.accept(AetherBlocks.SKYROOT_SLAB.get());
                output.accept(AetherBlocks.SKYROOT_FENCE.get());
                output.accept(AetherBlocks.SKYROOT_FENCE_GATE.get());
                output.accept(AetherBlocks.SKYROOT_DOOR.get());
                output.accept(AetherBlocks.SKYROOT_TRAPDOOR.get());
                output.accept(AetherBlocks.SKYROOT_PRESSURE_PLATE.get());
                output.accept(AetherBlocks.SKYROOT_BUTTON.get());
                output.accept(AetherBlocks.GOLDEN_OAK_LOG.get());
                output.accept(AetherBlocks.GOLDEN_OAK_WOOD.get());
                output.accept(AetherBlocks.QUICKSOIL_GLASS.get());
                output.accept(AetherBlocks.QUICKSOIL_GLASS_PANE.get());
                output.accept(AetherBlocks.HOLYSTONE.get());
                output.accept(AetherBlocks.HOLYSTONE_STAIRS.get());
                output.accept(AetherBlocks.HOLYSTONE_SLAB.get());
                output.accept(AetherBlocks.HOLYSTONE_WALL.get());
                output.accept(AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get());
                output.accept(AetherBlocks.HOLYSTONE_BUTTON.get());
                output.accept(AetherBlocks.MOSSY_HOLYSTONE.get());
                output.accept(AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get());
                output.accept(AetherBlocks.MOSSY_HOLYSTONE_SLAB.get());
                output.accept(AetherBlocks.MOSSY_HOLYSTONE_WALL.get());
                output.accept(AetherBlocks.HOLYSTONE_BRICKS.get());
                output.accept(AetherBlocks.HOLYSTONE_BRICK_STAIRS.get());
                output.accept(AetherBlocks.HOLYSTONE_BRICK_SLAB.get());
                output.accept(AetherBlocks.HOLYSTONE_BRICK_WALL.get());
                output.accept(AetherBlocks.ICESTONE.get());
                output.accept(AetherBlocks.ICESTONE_STAIRS.get());
                output.accept(AetherBlocks.ICESTONE_SLAB.get());
                output.accept(AetherBlocks.ICESTONE_WALL.get());
                output.accept(AetherBlocks.AMBROSIUM_BLOCK.get());
                output.accept(AetherBlocks.ZANITE_BLOCK.get());
                output.accept(AetherBlocks.ENCHANTED_GRAVITITE.get());
                output.accept(AetherBlocks.AEROGEL.get());
                output.accept(AetherBlocks.AEROGEL_STAIRS.get());
                output.accept(AetherBlocks.AEROGEL_SLAB.get());
                output.accept(AetherBlocks.AEROGEL_WALL.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_DUNGEON_BLOCKS = CREATIVE_MODE_TABS.register("dungeon_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(Aether.MODID, "building_blocks"))
            .withTabsAfter(new ResourceLocation(Aether.MODID, "natural_blocks"))
            .icon(() -> new ItemStack(AetherBlocks.LIGHT_ANGELIC_STONE.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".dungeon_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherBlocks.CARVED_STONE.get());
                output.accept(AetherBlocks.LOCKED_CARVED_STONE.get());
                output.accept(AetherBlocks.TRAPPED_CARVED_STONE.get());
                output.accept(AetherBlocks.BOSS_DOORWAY_CARVED_STONE.get());
                output.accept(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE.get());
                output.accept(AetherBlocks.CARVED_STAIRS.get());
                output.accept(AetherBlocks.CARVED_SLAB.get());
                output.accept(AetherBlocks.CARVED_WALL.get());
                output.accept(AetherBlocks.SENTRY_STONE.get());
                output.accept(AetherBlocks.LOCKED_SENTRY_STONE.get());
                output.accept(AetherBlocks.TRAPPED_SENTRY_STONE.get());
                output.accept(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE.get());
                output.accept(AetherBlocks.TREASURE_DOORWAY_SENTRY_STONE.get());
                output.accept(AetherBlocks.ANGELIC_STONE.get());
                output.accept(AetherBlocks.LOCKED_ANGELIC_STONE.get());
                output.accept(AetherBlocks.TRAPPED_ANGELIC_STONE.get());
                output.accept(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE.get());
                output.accept(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE.get());
                output.accept(AetherBlocks.ANGELIC_STAIRS.get());
                output.accept(AetherBlocks.ANGELIC_SLAB.get());
                output.accept(AetherBlocks.ANGELIC_WALL.get());
                output.accept(AetherBlocks.LIGHT_ANGELIC_STONE.get());
                output.accept(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get());
                output.accept(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get());
                output.accept(AetherBlocks.BOSS_DOORWAY_LIGHT_ANGELIC_STONE.get());
                output.accept(AetherBlocks.TREASURE_DOORWAY_LIGHT_ANGELIC_STONE.get());
                output.accept(AetherBlocks.PILLAR.get());
                output.accept(AetherBlocks.PILLAR_TOP.get());
                output.accept(AetherBlocks.HELLFIRE_STONE.get());
                output.accept(AetherBlocks.LOCKED_HELLFIRE_STONE.get());
                output.accept(AetherBlocks.TRAPPED_HELLFIRE_STONE.get());
                output.accept(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE.get());
                output.accept(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE.get());
                output.accept(AetherBlocks.HELLFIRE_STAIRS.get());
                output.accept(AetherBlocks.HELLFIRE_SLAB.get());
                output.accept(AetherBlocks.HELLFIRE_WALL.get());
                output.accept(AetherBlocks.LIGHT_HELLFIRE_STONE.get());
                output.accept(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get());
                output.accept(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE.get());
                output.accept(AetherBlocks.BOSS_DOORWAY_LIGHT_HELLFIRE_STONE.get());
                output.accept(AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE.get());
                output.accept(AetherBlocks.TREASURE_CHEST.get());
                output.accept(AetherBlocks.CHEST_MIMIC.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_NATURAL_BLOCKS = CREATIVE_MODE_TABS.register("natural_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(Aether.MODID, "dungeon_blocks"))
            .withTabsAfter(new ResourceLocation(Aether.MODID, "functional_blocks"))
            .icon(() -> new ItemStack(AetherBlocks.AETHER_GRASS_BLOCK.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".natural_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherBlocks.AETHER_GRASS_BLOCK.get());
                output.accept(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
                output.accept(AetherBlocks.AETHER_DIRT_PATH.get());
                output.accept(AetherBlocks.AETHER_DIRT.get());
                output.accept(AetherBlocks.AETHER_FARMLAND.get());
                output.accept(AetherBlocks.QUICKSOIL.get());
                output.accept(AetherBlocks.HOLYSTONE.get());
                output.accept(AetherBlocks.MOSSY_HOLYSTONE.get());
                output.accept(AetherBlocks.ICESTONE.get());
                output.accept(AetherBlocks.AMBROSIUM_ORE.get());
                output.accept(AetherBlocks.ZANITE_ORE.get());
                output.accept(AetherBlocks.GRAVITITE_ORE.get());
                output.accept(AetherBlocks.SKYROOT_LOG.get());
                output.accept(AetherBlocks.GOLDEN_OAK_LOG.get());
                output.accept(AetherBlocks.SKYROOT_LEAVES.get());
                output.accept(AetherBlocks.GOLDEN_OAK_LEAVES.get());
                output.accept(AetherBlocks.CRYSTAL_LEAVES.get());
                output.accept(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get());
                output.accept(AetherBlocks.HOLIDAY_LEAVES.get());
                output.accept(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get());
                output.accept(AetherBlocks.SKYROOT_SAPLING.get());
                output.accept(AetherBlocks.GOLDEN_OAK_SAPLING.get());
                output.accept(AetherBlocks.BERRY_BUSH_STEM.get());
                output.accept(AetherBlocks.BERRY_BUSH.get());
                output.accept(AetherBlocks.PURPLE_FLOWER.get());
                output.accept(AetherBlocks.WHITE_FLOWER.get());
                output.accept(AetherBlocks.COLD_AERCLOUD.get());
                output.accept(AetherBlocks.BLUE_AERCLOUD.get());
                output.accept(AetherBlocks.GOLDEN_AERCLOUD.get());
                output.accept(AetherBlocks.PRESENT.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_FUNCTIONAL_BLOCKS = CREATIVE_MODE_TABS.register("functional_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(Aether.MODID, "natural_blocks"))
            .withTabsAfter(new ResourceLocation(Aether.MODID, "redstone_blocks"))
            .icon(() -> new ItemStack(AetherBlocks.SKYROOT_SIGN.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".functional_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherBlocks.AMBROSIUM_TORCH.get());
                output.accept(AetherBlocks.ALTAR.get());
                output.accept(AetherBlocks.FREEZER.get());
                output.accept(AetherBlocks.INCUBATOR.get());
                output.accept(AetherBlocks.SUN_ALTAR.get());
                output.accept(AetherBlocks.SKYROOT_BOOKSHELF.get());
                output.accept(AetherBlocks.SKYROOT_SIGN.get());
                output.accept(AetherBlocks.SKYROOT_HANGING_SIGN.get());
                output.accept(AetherBlocks.SKYROOT_BED.get());
                output.accept(AetherBlocks.TREASURE_CHEST.get());
                output.accept(AetherBlocks.CHEST_MIMIC.get());
                output.accept(AetherBlocks.PRESENT.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_REDSTONE_BLOCKS = CREATIVE_MODE_TABS.register("redstone_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(Aether.MODID, "functional_blocks"))
            .withTabsAfter(new ResourceLocation(Aether.MODID, "tools_and_utilities"))
            .icon(() -> new ItemStack(AetherBlocks.SKYROOT_FENCE_GATE.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".redstone_blocks"))
            .displayItems((features, output) -> {
                output.accept(AetherBlocks.SKYROOT_BUTTON.get());
                output.accept(AetherBlocks.HOLYSTONE_BUTTON.get());
                output.accept(AetherBlocks.SKYROOT_PRESSURE_PLATE.get());
                output.accept(AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get());
                output.accept(AetherBlocks.ALTAR.get());
                output.accept(AetherBlocks.FREEZER.get());
                output.accept(AetherBlocks.INCUBATOR.get());
                output.accept(AetherBlocks.TREASURE_CHEST.get());
                output.accept(AetherItems.SKYROOT_CHEST_BOAT.get());
                output.accept(AetherBlocks.SKYROOT_DOOR.get());
                output.accept(AetherBlocks.SKYROOT_FENCE_GATE.get());
                output.accept(AetherBlocks.SKYROOT_TRAPDOOR.get());
                output.accept(AetherBlocks.ENCHANTED_GRAVITITE.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_EQUIPMENT_AND_UTILITIES = CREATIVE_MODE_TABS.register("equipment_and_utilities", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(Aether.MODID, "redstone_blocks"))
            .withTabsAfter(new ResourceLocation(Aether.MODID, "armor_and_accessories"))
            .icon(() -> new ItemStack(AetherItems.GRAVITITE_PICKAXE.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".equipment_and_utilities"))
            .displayItems((features, output) -> {
                output.accept(AetherItems.SKYROOT_SWORD.get());
                output.accept(AetherItems.SKYROOT_SHOVEL.get());
                output.accept(AetherItems.SKYROOT_PICKAXE.get());
                output.accept(AetherItems.SKYROOT_AXE.get());
                output.accept(AetherItems.SKYROOT_HOE.get());
                output.accept(AetherItems.HOLYSTONE_SWORD.get());
                output.accept(AetherItems.HOLYSTONE_SHOVEL.get());
                output.accept(AetherItems.HOLYSTONE_PICKAXE.get());
                output.accept(AetherItems.HOLYSTONE_AXE.get());
                output.accept(AetherItems.HOLYSTONE_HOE.get());
                output.accept(AetherItems.ZANITE_SWORD.get());
                output.accept(AetherItems.ZANITE_SHOVEL.get());
                output.accept(AetherItems.ZANITE_PICKAXE.get());
                output.accept(AetherItems.ZANITE_AXE.get());
                output.accept(AetherItems.ZANITE_HOE.get());
                output.accept(AetherItems.GRAVITITE_SWORD.get());
                output.accept(AetherItems.GRAVITITE_SHOVEL.get());
                output.accept(AetherItems.GRAVITITE_PICKAXE.get());
                output.accept(AetherItems.GRAVITITE_AXE.get());
                output.accept(AetherItems.GRAVITITE_HOE.get());
                output.accept(AetherItems.VALKYRIE_LANCE.get());
                output.accept(AetherItems.VALKYRIE_SHOVEL.get());
                output.accept(AetherItems.VALKYRIE_PICKAXE.get());
                output.accept(AetherItems.VALKYRIE_AXE.get());
                output.accept(AetherItems.VALKYRIE_HOE.get());
                output.accept(AetherItems.GOLDEN_DART_SHOOTER.get());
                output.accept(AetherItems.GOLDEN_DART.get());
                output.accept(AetherItems.POISON_DART_SHOOTER.get());
                output.accept(AetherItems.POISON_DART.get());
                output.accept(AetherItems.ENCHANTED_DART_SHOOTER.get());
                output.accept(AetherItems.ENCHANTED_DART.get());
                output.accept(AetherItems.CANDY_CANE_SWORD.get());
                output.accept(AetherItems.HOLY_SWORD.get());
                output.accept(AetherItems.VAMPIRE_BLADE.get());
                output.accept(AetherItems.LIGHTNING_SWORD.get());
                output.accept(AetherItems.LIGHTNING_KNIFE.get());
                output.accept(AetherItems.FLAMING_SWORD.get());
                output.accept(AetherItems.PHOENIX_BOW.get());
                output.accept(AetherItems.PIG_SLAYER.get());
                output.accept(AetherItems.HAMMER_OF_KINGBDOGZ.get());
                output.accept(AetherItems.CLOUD_STAFF.get());
                output.accept(AetherItems.SKYROOT_BUCKET.get());
                output.accept(AetherItems.SKYROOT_WATER_BUCKET.get());
                output.accept(AetherItems.SKYROOT_PUFFERFISH_BUCKET.get());
                output.accept(AetherItems.SKYROOT_SALMON_BUCKET.get());
                output.accept(AetherItems.SKYROOT_COD_BUCKET.get());
                output.accept(AetherItems.SKYROOT_TROPICAL_FISH_BUCKET.get());
                output.accept(AetherItems.SKYROOT_AXOLOTL_BUCKET.get());
                output.accept(AetherItems.SKYROOT_TADPOLE_BUCKET.get());
                output.accept(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get());
                output.accept(AetherItems.SKYROOT_MILK_BUCKET.get());
                output.accept(AetherItems.SKYROOT_REMEDY_BUCKET.get());
                output.accept(AetherItems.SKYROOT_POISON_BUCKET.get());
                output.accept(AetherItems.BOOK_OF_LORE.get());
                output.accept(AetherItems.COLD_PARACHUTE.get());
                output.accept(AetherItems.GOLDEN_PARACHUTE.get());
                output.accept(AetherItems.AMBROSIUM_SHARD.get());
                output.accept(AetherItems.SWET_BALL.get());
                output.accept(AetherItems.BLUE_MOA_EGG.get());
                output.accept(AetherItems.WHITE_MOA_EGG.get());
                output.accept(AetherItems.BLACK_MOA_EGG.get());
                output.accept(AetherItems.NATURE_STAFF.get());
                output.accept(AetherItems.SKYROOT_BOAT.get());
                output.accept(AetherItems.SKYROOT_CHEST_BOAT.get());
                output.accept(AetherItems.BRONZE_DUNGEON_KEY.get());
                output.accept(AetherItems.SILVER_DUNGEON_KEY.get());
                output.accept(AetherItems.GOLD_DUNGEON_KEY.get());
                output.accept(AetherItems.VICTORY_MEDAL.get());
                output.accept(AetherItems.MUSIC_DISC_AETHER_TUNE.get());
                output.accept(AetherItems.MUSIC_DISC_ASCENDING_DAWN.get());
                output.accept(AetherItems.AETHER_PORTAL_FRAME.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_ARMOR_AND_ACCESSORIES = CREATIVE_MODE_TABS.register("armor_and_accessories", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(Aether.MODID, "equipment_and_utilities"))
            .withTabsAfter(new ResourceLocation(Aether.MODID, "food_and_drinks"))
            .icon(() -> new ItemStack(AetherItems.VALKYRIE_CHESTPLATE.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".armor_and_accessories"))
            .displayItems((features, output) -> {
                output.accept(AetherItems.ZANITE_HELMET.get());
                output.accept(AetherItems.ZANITE_CHESTPLATE.get());
                output.accept(AetherItems.ZANITE_LEGGINGS.get());
                output.accept(AetherItems.ZANITE_BOOTS.get());
                output.accept(AetherItems.ZANITE_GLOVES.get());
                output.accept(AetherItems.GRAVITITE_HELMET.get());
                output.accept(AetherItems.GRAVITITE_CHESTPLATE.get());
                output.accept(AetherItems.GRAVITITE_LEGGINGS.get());
                output.accept(AetherItems.GRAVITITE_BOOTS.get());
                output.accept(AetherItems.GRAVITITE_GLOVES.get());
                output.accept(AetherItems.NEPTUNE_HELMET.get());
                output.accept(AetherItems.NEPTUNE_CHESTPLATE.get());
                output.accept(AetherItems.NEPTUNE_LEGGINGS.get());
                output.accept(AetherItems.NEPTUNE_BOOTS.get());
                output.accept(AetherItems.NEPTUNE_GLOVES.get());
                output.accept(AetherItems.VALKYRIE_HELMET.get());
                output.accept(AetherItems.VALKYRIE_CHESTPLATE.get());
                output.accept(AetherItems.VALKYRIE_LEGGINGS.get());
                output.accept(AetherItems.VALKYRIE_BOOTS.get());
                output.accept(AetherItems.VALKYRIE_GLOVES.get());
                output.accept(AetherItems.PHOENIX_HELMET.get());
                output.accept(AetherItems.PHOENIX_CHESTPLATE.get());
                output.accept(AetherItems.PHOENIX_LEGGINGS.get());
                output.accept(AetherItems.PHOENIX_BOOTS.get());
                output.accept(AetherItems.PHOENIX_GLOVES.get());
                output.accept(AetherItems.OBSIDIAN_HELMET.get());
                output.accept(AetherItems.OBSIDIAN_CHESTPLATE.get());
                output.accept(AetherItems.OBSIDIAN_LEGGINGS.get());
                output.accept(AetherItems.OBSIDIAN_BOOTS.get());
                output.accept(AetherItems.OBSIDIAN_GLOVES.get());
                output.accept(AetherItems.SENTRY_BOOTS.get());
                output.accept(AetherItems.IRON_RING.get());
                output.accept(AetherItems.IRON_PENDANT.get());
                output.accept(AetherItems.GOLDEN_RING.get());
                output.accept(AetherItems.GOLDEN_PENDANT.get());
                output.accept(AetherItems.ZANITE_RING.get());
                output.accept(AetherItems.ZANITE_PENDANT.get());
                output.accept(AetherItems.ICE_RING.get());
                output.accept(AetherItems.ICE_PENDANT.get());
                output.accept(AetherItems.WHITE_CAPE.get());
                output.accept(AetherItems.YELLOW_CAPE.get());
                output.accept(AetherItems.RED_CAPE.get());
                output.accept(AetherItems.BLUE_CAPE.get());
                output.accept(AetherItems.AGILITY_CAPE.get());
                output.accept(AetherItems.SWET_CAPE.get());
                output.accept(AetherItems.INVISIBILITY_CLOAK.get());
                if (AetherConfig.SERVER.spawn_valkyrie_cape.get()) {
                    output.accept(AetherItems.VALKYRIE_CAPE.get());
                }
                if (AetherConfig.SERVER.spawn_golden_feather.get()) {
                    output.accept(AetherItems.GOLDEN_FEATHER.get());
                }
                output.accept(AetherItems.REGENERATION_STONE.get());
                output.accept(AetherItems.IRON_BUBBLE.get());
                output.accept(AetherItems.SHIELD_OF_REPULSION.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_FOOD_AND_DRINKS = CREATIVE_MODE_TABS.register("food_and_drinks", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(Aether.MODID, "combat"))
            .withTabsAfter(new ResourceLocation(Aether.MODID, "ingredients"))
            .icon(() -> new ItemStack(AetherItems.BLUE_GUMMY_SWET.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".food_and_drinks"))
            .displayItems((features, output) -> {
                output.accept(AetherItems.BLUE_BERRY.get());
                output.accept(AetherItems.ENCHANTED_BERRY.get());
                output.accept(AetherItems.WHITE_APPLE.get());
                if (AetherConfig.SERVER.edible_ambrosium.get()) {
                    output.accept(AetherItems.AMBROSIUM_SHARD.get());
                }
                output.accept(AetherItems.HEALING_STONE.get());
                output.accept(AetherItems.BLUE_GUMMY_SWET.get());
                output.accept(AetherItems.GOLDEN_GUMMY_SWET.get());
                output.accept(AetherItems.GINGERBREAD_MAN.get());
                output.accept(AetherItems.CANDY_CANE.get());
                output.accept(AetherItems.SKYROOT_MILK_BUCKET.get());
                output.accept(AetherItems.SKYROOT_REMEDY_BUCKET.get());
                output.accept(AetherItems.SKYROOT_POISON_BUCKET.get());
                output.accept(AetherItems.LIFE_SHARD.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_INGREDIENTS = CREATIVE_MODE_TABS.register("ingredients", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(Aether.MODID, "food_and_drinks"))
            .withTabsAfter(new ResourceLocation(Aether.MODID, "spawn_eggs"))
            .icon(() -> new ItemStack(AetherItems.AMBROSIUM_SHARD.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".ingredients"))
            .displayItems((features, output) -> {
                output.accept(AetherItems.AMBROSIUM_SHARD.get());
                output.accept(AetherItems.ZANITE_GEMSTONE.get());
                output.accept(AetherBlocks.ENCHANTED_GRAVITITE.get());
                output.accept(AetherItems.SKYROOT_STICK.get());
                output.accept(AetherItems.GOLDEN_AMBER.get());
                output.accept(AetherItems.AECHOR_PETAL.get());
                output.accept(AetherItems.SKYROOT_POISON_BUCKET.get());
                output.accept(AetherItems.SWET_BALL.get());
            }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AETHER_SPAWN_EGGS = CREATIVE_MODE_TABS.register("spawn_eggs", () -> CreativeModeTab.builder()
            .withTabsBefore(new ResourceLocation(Aether.MODID, "ingredients"))
            .icon(() -> new ItemStack(AetherItems.AERBUNNY_SPAWN_EGG.get()))
            .title(Component.translatable("itemGroup." + Aether.MODID + ".spawn_eggs"))
            .displayItems((features, output) -> {
                output.accept(AetherItems.BLUE_MOA_EGG.get());
                output.accept(AetherItems.WHITE_MOA_EGG.get());
                output.accept(AetherItems.BLACK_MOA_EGG.get());
                output.accept(AetherItems.AECHOR_PLANT_SPAWN_EGG.get());
                output.accept(AetherItems.AERBUNNY_SPAWN_EGG.get());
                output.accept(AetherItems.AERWHALE_SPAWN_EGG.get());
                output.accept(AetherItems.BLUE_SWET_SPAWN_EGG.get());
                output.accept(AetherItems.COCKATRICE_SPAWN_EGG.get());
                output.accept(AetherItems.EVIL_WHIRLWIND_SPAWN_EGG.get());
                output.accept(AetherItems.FIRE_MINION_SPAWN_EGG.get());
                output.accept(AetherItems.FLYING_COW_SPAWN_EGG.get());
                output.accept(AetherItems.GOLDEN_SWET_SPAWN_EGG.get());
                output.accept(AetherItems.MIMIC_SPAWN_EGG.get());
                output.accept(AetherItems.MOA_SPAWN_EGG.get());
                output.accept(AetherItems.PHYG_SPAWN_EGG.get());
                output.accept(AetherItems.SENTRY_SPAWN_EGG.get());
                output.accept(AetherItems.SHEEPUFF_SPAWN_EGG.get());
                output.accept(AetherItems.WHIRLWIND_SPAWN_EGG.get());
                output.accept(AetherItems.VALKYRIE_SPAWN_EGG.get());
                output.accept(AetherItems.ZEPHYR_SPAWN_EGG.get());
            }).build());

    @SubscribeEvent
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (tab == CreativeModeTabs.COMBAT) {
            event.getEntries().putAfter(new ItemStack(Items.LEATHER_BOOTS), new ItemStack(AetherItems.LEATHER_GLOVES.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.CHAINMAIL_BOOTS), new ItemStack(AetherItems.CHAINMAIL_GLOVES.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.IRON_BOOTS), new ItemStack(AetherItems.IRON_GLOVES.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.GOLDEN_BOOTS), new ItemStack(AetherItems.GOLDEN_GLOVES.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.DIAMOND_BOOTS), new ItemStack(AetherItems.DIAMOND_GLOVES.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.NETHERITE_BOOTS), new ItemStack(AetherItems.NETHERITE_GLOVES.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
