package com.gildedgames.aether.item;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherCreativeTabs {
    public static CreativeModeTab AETHER_BUILDING_BLOCKS;
    public static CreativeModeTab AETHER_DUNGEON_BLOCKS;
    public static CreativeModeTab AETHER_NATURAL_BLOCKS;
    public static CreativeModeTab AETHER_FUNCTIONAL_BLOCKS;
    public static CreativeModeTab AETHER_REDSTONE_BLOCKS;
    public static CreativeModeTab AETHER_EQUIPMENT_AND_UTILITIES;
    public static CreativeModeTab AETHER_ARMOR_AND_ACCESSORIES;
    public static CreativeModeTab AETHER_FOOD_AND_DRINKS;
    public static CreativeModeTab AETHER_INGREDIENTS;
    public static CreativeModeTab AETHER_SPAWN_EGGS;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        AETHER_BUILDING_BLOCKS = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "building_blocks"),
                List.of(new ResourceLocation(Aether.MODID, "dungeon_blocks")),
                List.of(CreativeModeTabs.SPAWN_EGGS),
                builder -> builder.icon(() -> new ItemStack(AetherBlocks.HOLYSTONE_BRICKS.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".building_blocks"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_LOG.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_WOOD.get()));
                            output.accept(new ItemStack(AetherBlocks.STRIPPED_SKYROOT_LOG.get()));
                            output.accept(new ItemStack(AetherBlocks.STRIPPED_SKYROOT_WOOD.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_PLANKS.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_STAIRS.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_SLAB.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_FENCE.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_FENCE_GATE.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_DOOR.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_TRAPDOOR.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_PRESSURE_PLATE.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_BUTTON.get()));
                            output.accept(new ItemStack(AetherBlocks.GOLDEN_OAK_LOG.get()));
                            output.accept(new ItemStack(AetherBlocks.GOLDEN_OAK_WOOD.get()));
                            output.accept(new ItemStack(AetherBlocks.QUICKSOIL_GLASS.get()));
                            output.accept(new ItemStack(AetherBlocks.QUICKSOIL_GLASS_PANE.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_STAIRS.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_SLAB.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_WALL.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_BUTTON.get()));
                            output.accept(new ItemStack(AetherBlocks.MOSSY_HOLYSTONE.get()));
                            output.accept(new ItemStack(AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get()));
                            output.accept(new ItemStack(AetherBlocks.MOSSY_HOLYSTONE_SLAB.get()));
                            output.accept(new ItemStack(AetherBlocks.MOSSY_HOLYSTONE_WALL.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_BRICKS.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_BRICK_STAIRS.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_BRICK_SLAB.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_BRICK_WALL.get()));
                            output.accept(new ItemStack(AetherBlocks.ICESTONE.get()));
                            output.accept(new ItemStack(AetherBlocks.ICESTONE_STAIRS.get()));
                            output.accept(new ItemStack(AetherBlocks.ICESTONE_SLAB.get()));
                            output.accept(new ItemStack(AetherBlocks.ICESTONE_WALL.get()));
                            output.accept(new ItemStack(AetherBlocks.AMBROSIUM_BLOCK.get()));
                            output.accept(new ItemStack(AetherBlocks.ZANITE_BLOCK.get()));
                            output.accept(new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
                            output.accept(new ItemStack(AetherBlocks.AEROGEL.get()));
                            output.accept(new ItemStack(AetherBlocks.AEROGEL_STAIRS.get()));
                            output.accept(new ItemStack(AetherBlocks.AEROGEL_SLAB.get()));
                            output.accept(new ItemStack(AetherBlocks.AEROGEL_WALL.get()));
                        }));
        AETHER_DUNGEON_BLOCKS = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "dungeon_blocks"),
                List.of(new ResourceLocation(Aether.MODID, "natural_blocks")),
                List.of(new ResourceLocation(Aether.MODID, "building_blocks")),
                builder -> builder.icon(() -> new ItemStack(AetherBlocks.LIGHT_ANGELIC_STONE.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".dungeon_blocks"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(new ItemStack(AetherBlocks.CARVED_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.LOCKED_CARVED_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TRAPPED_CARVED_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.BOSS_DOORWAY_CARVED_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.CARVED_STAIRS.get()));
                            output.accept(new ItemStack(AetherBlocks.CARVED_SLAB.get()));
                            output.accept(new ItemStack(AetherBlocks.CARVED_WALL.get()));
                            output.accept(new ItemStack(AetherBlocks.SENTRY_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.LOCKED_SENTRY_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TRAPPED_SENTRY_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TREASURE_DOORWAY_SENTRY_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.LOCKED_ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TRAPPED_ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.ANGELIC_STAIRS.get()));
                            output.accept(new ItemStack(AetherBlocks.ANGELIC_SLAB.get()));
                            output.accept(new ItemStack(AetherBlocks.ANGELIC_WALL.get()));
                            output.accept(new ItemStack(AetherBlocks.LIGHT_ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.BOSS_DOORWAY_LIGHT_ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TREASURE_DOORWAY_LIGHT_ANGELIC_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.PILLAR.get()));
                            output.accept(new ItemStack(AetherBlocks.PILLAR_TOP.get()));
                            output.accept(new ItemStack(AetherBlocks.HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.LOCKED_HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TRAPPED_HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.HELLFIRE_STAIRS.get()));
                            output.accept(new ItemStack(AetherBlocks.HELLFIRE_SLAB.get()));
                            output.accept(new ItemStack(AetherBlocks.HELLFIRE_WALL.get()));
                            output.accept(new ItemStack(AetherBlocks.LIGHT_HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.BOSS_DOORWAY_LIGHT_HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE.get()));
                            output.accept(new ItemStack(AetherBlocks.TREASURE_CHEST.get()));
                            output.accept(new ItemStack(AetherBlocks.CHEST_MIMIC.get()));
                        }));
        AETHER_NATURAL_BLOCKS = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "natural_blocks"),
                List.of(new ResourceLocation(Aether.MODID, "functional_blocks")),
                List.of(new ResourceLocation(Aether.MODID, "dungeon_blocks")),
                builder -> builder.icon(() -> new ItemStack(AetherBlocks.AETHER_GRASS_BLOCK.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".natural_blocks"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(new ItemStack(AetherBlocks.AETHER_GRASS_BLOCK.get()));
                            output.accept(new ItemStack(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()));
                            output.accept(new ItemStack(AetherBlocks.AETHER_DIRT_PATH.get()));
                            output.accept(new ItemStack(AetherBlocks.AETHER_DIRT.get()));
                            output.accept(new ItemStack(AetherBlocks.AETHER_FARMLAND.get()));
                            output.accept(new ItemStack(AetherBlocks.QUICKSOIL.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE.get()));
                            output.accept(new ItemStack(AetherBlocks.MOSSY_HOLYSTONE.get()));
                            output.accept(new ItemStack(AetherBlocks.ICESTONE.get()));
                            output.accept(new ItemStack(AetherBlocks.AMBROSIUM_ORE.get()));
                            output.accept(new ItemStack(AetherBlocks.ZANITE_ORE.get()));
                            output.accept(new ItemStack(AetherBlocks.GRAVITITE_ORE.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_LOG.get()));
                            output.accept(new ItemStack(AetherBlocks.GOLDEN_OAK_LOG.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_LEAVES.get()));
                            output.accept(new ItemStack(AetherBlocks.GOLDEN_OAK_LEAVES.get()));
                            output.accept(new ItemStack(AetherBlocks.CRYSTAL_LEAVES.get()));
                            output.accept(new ItemStack(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLIDAY_LEAVES.get()));
                            output.accept(new ItemStack(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_SAPLING.get()));
                            output.accept(new ItemStack(AetherBlocks.GOLDEN_OAK_SAPLING.get()));
                            output.accept(new ItemStack(AetherBlocks.BERRY_BUSH_STEM.get()));
                            output.accept(new ItemStack(AetherBlocks.BERRY_BUSH.get()));
                            output.accept(new ItemStack(AetherBlocks.PURPLE_FLOWER.get()));
                            output.accept(new ItemStack(AetherBlocks.WHITE_FLOWER.get()));
                            output.accept(new ItemStack(AetherBlocks.COLD_AERCLOUD.get()));
                            output.accept(new ItemStack(AetherBlocks.BLUE_AERCLOUD.get()));
                            output.accept(new ItemStack(AetherBlocks.GOLDEN_AERCLOUD.get()));
                            output.accept(new ItemStack(AetherBlocks.PINK_AERCLOUD.get()));
                            output.accept(new ItemStack(AetherBlocks.PRESENT.get()));
                        }));
        AETHER_FUNCTIONAL_BLOCKS = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "functional_blocks"),
                List.of(new ResourceLocation(Aether.MODID, "redstone_blocks")),
                List.of(new ResourceLocation(Aether.MODID, "natural_blocks")),
                builder -> builder.icon(() -> new ItemStack(AetherBlocks.SKYROOT_SIGN.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".functional_blocks"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(new ItemStack(AetherBlocks.AMBROSIUM_TORCH.get()));
                            output.accept(new ItemStack(AetherBlocks.ALTAR.get()));
                            output.accept(new ItemStack(AetherBlocks.FREEZER.get()));
                            output.accept(new ItemStack(AetherBlocks.INCUBATOR.get()));
                            output.accept(new ItemStack(AetherBlocks.SUN_ALTAR.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_BOOKSHELF.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_SIGN.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_BED.get()));
                            output.accept(new ItemStack(AetherBlocks.TREASURE_CHEST.get()));
                            output.accept(new ItemStack(AetherBlocks.CHEST_MIMIC.get()));
                            output.accept(new ItemStack(AetherBlocks.PRESENT.get()));
                        }));
        AETHER_REDSTONE_BLOCKS = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "redstone_blocks"),
                List.of(new ResourceLocation(Aether.MODID, "tools_and_utilities")),
                List.of(new ResourceLocation(Aether.MODID, "functional_blocks")),
                builder -> builder.icon(() -> new ItemStack(AetherBlocks.SKYROOT_FENCE_GATE.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".redstone_blocks"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_BUTTON.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_BUTTON.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_PRESSURE_PLATE.get()));
                            output.accept(new ItemStack(AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get()));
                            output.accept(new ItemStack(AetherBlocks.ALTAR.get()));
                            output.accept(new ItemStack(AetherBlocks.FREEZER.get()));
                            output.accept(new ItemStack(AetherBlocks.INCUBATOR.get()));
                            output.accept(new ItemStack(AetherBlocks.TREASURE_CHEST.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_CHEST_BOAT.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_DOOR.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_FENCE_GATE.get()));
                            output.accept(new ItemStack(AetherBlocks.SKYROOT_TRAPDOOR.get()));
                            output.accept(new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
                        }));
        AETHER_EQUIPMENT_AND_UTILITIES = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "equipment_and_utilities"),
                List.of(new ResourceLocation(Aether.MODID, "armor_and_accessories")),
                List.of(new ResourceLocation(Aether.MODID, "redstone_blocks")),
                builder -> builder.icon(() -> new ItemStack(AetherItems.GRAVITITE_PICKAXE.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".equipment_and_utilities"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(new ItemStack(AetherItems.SKYROOT_SWORD.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_SHOVEL.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_PICKAXE.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_AXE.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_HOE.get()));
                            output.accept(new ItemStack(AetherItems.HOLYSTONE_SWORD.get()));
                            output.accept(new ItemStack(AetherItems.HOLYSTONE_SHOVEL.get()));
                            output.accept(new ItemStack(AetherItems.HOLYSTONE_PICKAXE.get()));
                            output.accept(new ItemStack(AetherItems.HOLYSTONE_AXE.get()));
                            output.accept(new ItemStack(AetherItems.HOLYSTONE_HOE.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_SWORD.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_SHOVEL.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_PICKAXE.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_AXE.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_HOE.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_SWORD.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_SHOVEL.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_PICKAXE.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_AXE.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_HOE.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_LANCE.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_SHOVEL.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_PICKAXE.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_AXE.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_HOE.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_DART_SHOOTER.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_DART.get()));
                            output.accept(new ItemStack(AetherItems.POISON_DART_SHOOTER.get()));
                            output.accept(new ItemStack(AetherItems.POISON_DART.get()));
                            output.accept(new ItemStack(AetherItems.ENCHANTED_DART_SHOOTER.get()));
                            output.accept(new ItemStack(AetherItems.ENCHANTED_DART.get()));
                            output.accept(new ItemStack(AetherItems.CANDY_CANE_SWORD.get()));
                            output.accept(new ItemStack(AetherItems.HOLY_SWORD.get()));
                            output.accept(new ItemStack(AetherItems.VAMPIRE_BLADE.get()));
                            output.accept(new ItemStack(AetherItems.LIGHTNING_SWORD.get()));
                            output.accept(new ItemStack(AetherItems.LIGHTNING_KNIFE.get()));
                            output.accept(new ItemStack(AetherItems.FLAMING_SWORD.get()));
                            output.accept(new ItemStack(AetherItems.PHOENIX_BOW.get()));
                            output.accept(new ItemStack(AetherItems.HAMMER_OF_NOTCH.get()));
                            output.accept(new ItemStack(AetherItems.CLOUD_STAFF.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_WATER_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_PUFFERFISH_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_SALMON_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_COD_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_TROPICAL_FISH_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_AXOLOTL_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_TADPOLE_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_MILK_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_REMEDY_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_POISON_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.BOOK_OF_LORE.get()));
                            output.accept(new ItemStack(AetherItems.COLD_PARACHUTE.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_PARACHUTE.get()));
                            output.accept(new ItemStack(AetherItems.AMBROSIUM_SHARD.get()));
                            output.accept(new ItemStack(AetherItems.SWET_BALL.get()));
                            output.accept(new ItemStack(AetherItems.BLUE_MOA_EGG.get()));
                            output.accept(new ItemStack(AetherItems.WHITE_MOA_EGG.get()));
                            output.accept(new ItemStack(AetherItems.BLACK_MOA_EGG.get()));
                            output.accept(new ItemStack(AetherItems.ORANGE_MOA_EGG.get()));
                            output.accept(new ItemStack(AetherItems.NATURE_STAFF.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_BOAT.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_CHEST_BOAT.get()));
                            output.accept(new ItemStack(AetherItems.BRONZE_DUNGEON_KEY.get()));
                            output.accept(new ItemStack(AetherItems.SILVER_DUNGEON_KEY.get()));
                            output.accept(new ItemStack(AetherItems.GOLD_DUNGEON_KEY.get()));
                            output.accept(new ItemStack(AetherItems.VICTORY_MEDAL.get()));
                            output.accept(new ItemStack(AetherItems.MUSIC_DISC_AETHER_TUNE.get()));
                            output.accept(new ItemStack(AetherItems.MUSIC_DISC_ASCENDING_DAWN.get()));
                            output.accept(new ItemStack(AetherItems.MUSIC_DISC_WELCOMING_SKIES.get()));
                            output.accept(new ItemStack(AetherItems.MUSIC_DISC_LEGACY.get()));
                            output.accept(new ItemStack(AetherItems.MUSIC_DISC_CHINCHILLA.get()));
                            output.accept(new ItemStack(AetherItems.AETHER_PORTAL_FRAME.get()));
                        }));
        AETHER_ARMOR_AND_ACCESSORIES = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "armor_and_accessories"),
                List.of(new ResourceLocation(Aether.MODID, "food_and_drinks")),
                List.of(new ResourceLocation(Aether.MODID, "equipment_and_utilities")),
                builder -> builder.icon(() -> new ItemStack(AetherItems.VALKYRIE_CHESTPLATE.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".armor_and_accessories"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(new ItemStack(AetherItems.LEATHER_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.CHAINMAIL_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.IRON_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.DIAMOND_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.NETHERITE_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_HELMET.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_CHESTPLATE.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_LEGGINGS.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_BOOTS.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_HELMET.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_CHESTPLATE.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_LEGGINGS.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_BOOTS.get()));
                            output.accept(new ItemStack(AetherItems.GRAVITITE_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_HELMET.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_CHESTPLATE.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_LEGGINGS.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_BOOTS.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.NEPTUNE_HELMET.get()));
                            output.accept(new ItemStack(AetherItems.NEPTUNE_CHESTPLATE.get()));
                            output.accept(new ItemStack(AetherItems.NEPTUNE_LEGGINGS.get()));
                            output.accept(new ItemStack(AetherItems.NEPTUNE_BOOTS.get()));
                            output.accept(new ItemStack(AetherItems.NEPTUNE_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.PHOENIX_HELMET.get()));
                            output.accept(new ItemStack(AetherItems.PHOENIX_CHESTPLATE.get()));
                            output.accept(new ItemStack(AetherItems.PHOENIX_LEGGINGS.get()));
                            output.accept(new ItemStack(AetherItems.PHOENIX_BOOTS.get()));
                            output.accept(new ItemStack(AetherItems.PHOENIX_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.OBSIDIAN_HELMET.get()));
                            output.accept(new ItemStack(AetherItems.OBSIDIAN_CHESTPLATE.get()));
                            output.accept(new ItemStack(AetherItems.OBSIDIAN_LEGGINGS.get()));
                            output.accept(new ItemStack(AetherItems.OBSIDIAN_BOOTS.get()));
                            output.accept(new ItemStack(AetherItems.OBSIDIAN_GLOVES.get()));
                            output.accept(new ItemStack(AetherItems.SENTRY_BOOTS.get()));
                            output.accept(new ItemStack(AetherItems.IRON_RING.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_RING.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_RING.get()));
                            output.accept(new ItemStack(AetherItems.ICE_RING.get()));
                            output.accept(new ItemStack(AetherItems.IRON_PENDANT.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_PENDANT.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_PENDANT.get()));
                            output.accept(new ItemStack(AetherItems.ICE_PENDANT.get()));
                            output.accept(new ItemStack(AetherItems.WHITE_CAPE.get()));
                            output.accept(new ItemStack(AetherItems.YELLOW_CAPE.get()));
                            output.accept(new ItemStack(AetherItems.RED_CAPE.get()));
                            output.accept(new ItemStack(AetherItems.BLUE_CAPE.get()));
                            output.accept(new ItemStack(AetherItems.SWET_CAPE.get()));
                            output.accept(new ItemStack(AetherItems.AGILITY_CAPE.get()));
                            output.accept(new ItemStack(AetherItems.INVISIBILITY_CLOAK.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_CAPE.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_FEATHER.get()));
                            output.accept(new ItemStack(AetherItems.REGENERATION_STONE.get()));
                            output.accept(new ItemStack(AetherItems.IRON_BUBBLE.get()));
                            output.accept(new ItemStack(AetherItems.SHIELD_OF_REPULSION.get()));
                        }));
        AETHER_FOOD_AND_DRINKS = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "food_and_drinks"),
                List.of(new ResourceLocation(Aether.MODID, "ingredients")),
                List.of(new ResourceLocation(Aether.MODID, "combat")),
                builder -> builder.icon(() -> new ItemStack(AetherItems.BLUE_GUMMY_SWET.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".food_and_drinks"))
                        .displayItems((features, output, hasPermissions) -> {
                            if (AetherConfig.COMMON.edible_ambrosium.get()) {
                                output.accept(new ItemStack(AetherItems.AMBROSIUM_SHARD.get()));
                            }
                            output.accept(new ItemStack(AetherItems.BLUE_BERRY.get()));
                            output.accept(new ItemStack(AetherItems.ENCHANTED_BERRY.get()));
                            output.accept(new ItemStack(AetherItems.WHITE_APPLE.get()));
                            output.accept(new ItemStack(AetherItems.CANDY_CANE.get()));
                            output.accept(new ItemStack(AetherItems.GINGERBREAD_MAN.get()));
                            output.accept(new ItemStack(AetherItems.HEALING_STONE.get()));
                            output.accept(new ItemStack(AetherItems.BLUE_GUMMY_SWET.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_GUMMY_SWET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_MILK_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_REMEDY_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_POISON_BUCKET.get()));
                            output.accept(new ItemStack(AetherItems.AECHOR_PETAL.get()));
                            output.accept(new ItemStack(AetherItems.LIFE_SHARD.get()));
                        }));
        AETHER_INGREDIENTS = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "ingredients"),
                List.of(new ResourceLocation(Aether.MODID, "spawn_eggs")),
                List.of(new ResourceLocation(Aether.MODID, "food_and_drinks")),
                builder -> builder.icon(() -> new ItemStack(AetherItems.AMBROSIUM_SHARD.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".ingredients"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(new ItemStack(AetherItems.AMBROSIUM_SHARD.get()));
                            output.accept(new ItemStack(AetherItems.ZANITE_GEMSTONE.get()));
                            output.accept(new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_STICK.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_AMBER.get()));
                            output.accept(new ItemStack(AetherItems.SWET_BALL.get()));
                            output.accept(new ItemStack(AetherItems.AECHOR_PETAL.get()));
                            output.accept(new ItemStack(AetherItems.SKYROOT_POISON_BUCKET.get()));
                        }));
        AETHER_SPAWN_EGGS = event.registerCreativeModeTab(
                new ResourceLocation(Aether.MODID, "spawn_eggs"),
                List.of(),
                List.of(new ResourceLocation(Aether.MODID, "ingredients")),
                builder -> builder.icon(() -> new ItemStack(AetherItems.AERBUNNY_SPAWN_EGG.get()))
                        .title(Component.translatable("itemGroup." + Aether.MODID + ".spawn_eggs"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(new ItemStack(AetherItems.BLUE_MOA_EGG.get()));
                            output.accept(new ItemStack(AetherItems.WHITE_MOA_EGG.get()));
                            output.accept(new ItemStack(AetherItems.BLACK_MOA_EGG.get()));
                            output.accept(new ItemStack(AetherItems.ORANGE_MOA_EGG.get()));
                            output.accept(new ItemStack(AetherItems.AECHOR_PLANT_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.AERBUNNY_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.AERWHALE_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.BLUE_SWET_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.COCKATRICE_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.EVIL_WHIRLWIND_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.FIRE_MINION_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.FLYING_COW_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.GOLDEN_SWET_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.MIMIC_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.MOA_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.PHYG_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.SENTRY_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.SHEEPUFF_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.WHIRLWIND_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.VALKYRIE_SPAWN_EGG.get()));
                            output.accept(new ItemStack(AetherItems.ZEPHYR_SPAWN_EGG.get()));
                        }));
    }
}