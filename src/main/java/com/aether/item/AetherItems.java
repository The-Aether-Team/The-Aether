package com.aether.item;

import com.aether.Aether;
import com.aether.api.enchantments.AetherEnchantmentFuel;
import com.aether.api.freezables.AetherFreezableFuel;
import com.aether.block.AetherBlocks;
import com.aether.block.IAetherBlockColor;
import com.aether.entity.monster.MimicEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherItems {

	public static final Item ZANITE_GEMSTONE = null;
	public static final Item AMBROSIUM_SHARD = null;
	public static final Item GOLDEN_AMBER = null;
	public static final Item AECHOR_PETAL = null;
	public static final Item SWETTY_BALL = null;
	public static final Item SKYROOT_PICKAXE = null;
	public static final Item SKYROOT_AXE = null;
	public static final Item SKYROOT_SHOVEL = null;
	public static final Item SKYROOT_SWORD = null;
	public static final Item HOLYSTONE_PICKAXE = null;
	public static final Item HOLYSTONE_AXE = null;
	public static final Item HOLYSTONE_SHOVEL = null;
	public static final Item HOLYSTONE_SWORD = null;
	public static final Item ZANITE_PICKAXE = null;
	public static final Item ZANITE_AXE = null;
	public static final Item ZANITE_SHOVEL = null;
	public static final Item ZANITE_SWORD = null;
	public static final Item GRAVITITE_PICKAXE = null;
	public static final Item GRAVITITE_AXE = null;
	public static final Item GRAVITITE_SHOVEL = null;
	public static final Item GRAVITITE_SWORD = null;
	public static final Item VALKYRIE_PICKAXE = null;
	public static final Item VALKYRIE_AXE = null;
	public static final Item VALKYRIE_SHOVEL = null;
	public static final Item VALKYRIE_SWORD = null;
	public static final Item ZANITE_HELMET = null;
	public static final Item ZANITE_CHESTPLATE = null;
	public static final Item ZANITE_LEGGINGS = null;
	public static final Item ZANITE_BOOTS = null;
	public static final Item GRAVITITE_HELMET = null;
	public static final Item GRAVITITE_CHESTPLATE = null;
	public static final Item GRAVITITE_LEGGINGS = null;
	public static final Item GRAVITITE_BOOTS = null;
	public static final Item NEPTUNE_HELMET = null;
	public static final Item NEPTUNE_CHESTPLATE = null;
	public static final Item NEPTUNE_LEGGINGS = null;
	public static final Item NEPTUNE_BOOTS = null;
	public static final Item PHOENIX_HELMET = null;
	public static final Item PHOENIX_CHESTPLATE = null;
	public static final Item PHOENIX_LEGGINGS = null;
	public static final Item PHOENIX_BOOTS = null;
	public static final Item OBSIDIAN_HELMET = null;
	public static final Item OBSIDIAN_CHESTPLATE = null;
	public static final Item OBSIDIAN_LEGGINGS = null;
	public static final Item OBSIDIAN_BOOTS = null;
	public static final Item VALKYRIE_HELMET = null;
	public static final Item VALKYRIE_CHESTPLATE = null;
	public static final Item VALKYRIE_LEGGINGS = null;
	public static final Item VALKYRIE_BOOTS = null;
	public static final Item BLUEBERRY = null;
	public static final Item BLUE_GUMMY_SWET = null;
	public static final Item GOLDEN_GUMMY_SWET = null;
	public static final Item HEALING_STONE = null;
	public static final Item WHITE_APPLE = null;
	public static final Item GINGERBREAD_MAN = null;
	public static final Item CANDY_CANE = null;
	public static final Item ENCHANTED_BLUEBERRY = null;
	public static final Item SKYROOT_STICK = null;
	public static final Item VICTORY_MEDAL = null;
	public static final Item BRONZE_DUNGEON_KEY = null;
	public static final Item SILVER_DUNGEON_KEY = null;
	public static final Item GOLD_DUNGEON_KEY = null;
	public static final Item SKYROOT_BUCKET = null;
	public static final Item SKYROOT_WATER_BUCKET = null;
	public static final Item SKYROOT_POISON_BUCKET = null;
	public static final Item SKYROOT_REMEDY_BUCKET = null;
	public static final Item SKYROOT_MILK_BUCKET = null;
	public static final Item CLOUD_PARACHUTE = null;
	public static final Item GOLDEN_PARACHUTE = null;
	public static final Item NATURE_STAFF = null;
	public static final Item CLOUD_STAFF = null;
	public static final Item MOA_EGG = null;
	public static final Item DART_SHOOTER = null;
	public static final Item PHOENIX_BOX = null;
	public static final Item GOLDEN_DART = null;
	public static final Item POISON_DART = null;
	public static final Item ENCHANTED_DART = null;
	public static final Item FLAMING_SWORD = null;
	public static final Item LIGHTNING_SWORD = null;
	public static final Item HOLY_SWORD = null;
	public static final Item VAMPIRE_BLADE = null;
	public static final Item PIG_SLAYER = null;
	public static final Item CANDY_CANE_SWORD = null;
	public static final Item NOTCH_HAMMER = null;
	public static final Item VALKYRIE_LANCE = null;
	public static final Item LEATHER_GLOVES = null;
	public static final Item IRON_GLOVES = null;
	public static final Item GOLDEN_GLOVES = null;
	public static final Item CHAINMAIL_GLOVES = null;
	public static final Item DIAMOND_GLOVES = null;
	public static final Item ZANITE_GLOVES = null;
	public static final Item GRAVITITE_GLOVES = null;
	public static final Item NEPTUNE_GLOVES = null;
	public static final Item PHOENIX_GLOVES = null;
	public static final Item OBSIDIAN_GLOVES = null;
	public static final Item VALKYRIE_GLOVES = null;
	public static final Item IRON_RING = null;
	public static final Item GOLDEN_RING = null;
	public static final Item ZANITE_RING = null;
	public static final Item ICE_RING = null;
	public static final Item IRON_PENDANT = null;
	public static final Item GOLDEN_PENDANT = null;
	public static final Item ZANITE_PENDANT = null;
	public static final Item ICE_PENDANT = null;
	public static final Item RED_CAPE = null;
	public static final Item BLUE_CAPE = null;
	public static final Item YELLOW_CAPE = null;
	public static final Item WHITE_CAPE = null;
	public static final Item SWET_CAPE = null;
	public static final Item INVISIBILITY_CAPE = null;
	public static final Item AGILITY_CAPE = null;
	public static final Item VALKYRIE_CAPE = null;
	public static final Item GOLDEN_FEATHER = null;
	public static final Item REGENERATION_STONE = null;
	public static final Item IRON_BUBBLE = null;
	public static final Item LIFE_SHARD = null;
	public static final Item SENTRY_BOOTS = null;
	public static final Item LIGHTNING_KNIFE = null;
	public static final Item MUSIC_DISC_AETHER_TUNE = null;
	public static final Item MUSIC_DISC_ASCENDING_DAWN = null;
	public static final Item MUSIC_DISC_WELCOMING_SKIES = null;
	public static final Item MUSIC_DISC_LEGACY = null;
	public static final Item REPULSION_SHIELD = null;
	public static final Item LORE_BOOK = null;
	public static final Item DEVELOPER_STICK = null;
	public static final SpawnEggItem PHYG_SPAWN_EGG = null;
	public static final SpawnEggItem FLYING_COW_SPAWN_EGG = null;
	public static final SpawnEggItem SHEEPUFF_SPAWN_EGG = null;
	public static final SpawnEggItem AERBUNNY_SPAWN_EGG = null;
	public static final SpawnEggItem AERWHALE_SPAWN_EGG = null;
	public static final SpawnEggItem BLUE_SWET_SPAWN_EGG = null;
	public static final SpawnEggItem GOLDEN_SWET_SPAWN_EGG = null;
	public static final SpawnEggItem COCKATRICE_SPAWN_EGG = null;
	public static final SpawnEggItem SENTRY_SPAWN_EGG = null;
	public static final SpawnEggItem ZEPHYR_SPAWN_EGG = null;
	public static final SpawnEggItem AECHOR_PLANT_SPAWN_EGG = null;
	public static final SpawnEggItem MIMIC_SPAWN_EGG = null;
	public static final SpawnEggItem VALKYRIE_SPAWN_EGG = null;
	public static final SpawnEggItem FIRE_MINION_SPAWN_EGG = null;

	@SuppressWarnings("unused")
	@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static final class Registration {

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			registerBlockItems(event);
			event.getRegistry().registerAll(new Item[] {

				item("zanite_gemstone", new Item(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS))),
				item("ambrosium_shard", new AmbrosiumShardItem(new Item.Properties()
						.food(new Food.Builder().setAlwaysEdible().fastToEat()
							.effect(new EffectInstance(Effects.INSTANT_HEALTH), 1.0f).build())
						.group(AetherItemGroups.AETHER_MATERIALS))),
				item("golden_amber", new Item(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS))),
				item("aechor_petal", new Item(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS))),
				item("swetty_ball", new SwettyBallItem(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS))),
				item("skyroot_shovel", new ShovelItem(AetherItemTier.SKYROOT, 1.5f, -3.0f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("skyroot_pickaxe", new PickaxeItem(AetherItemTier.SKYROOT, 1, -2.8f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("skyroot_axe", new AxeItem(AetherItemTier.SKYROOT, 6.0f, -3.2f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("holystone_shovel", new HolystoneShovelItem(AetherItemTier.HOLYSTONE, 1.5f, -3.0f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("holystone_pickaxe", new HolystonePickaxeItem(AetherItemTier.HOLYSTONE, 1, -2.8f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("holystone_axe", new HolystoneAxeItem(AetherItemTier.HOLYSTONE, 8.0f, -3.2f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("zanite_shovel", new ZaniteShovelItem(AetherItemTier.ZANITE, 1.5f, -3.0f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("zanite_pickaxe", new ZanitePickaxeItem(AetherItemTier.ZANITE, 1, -2.8f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("zanite_axe", new ZaniteAxeItem(AetherItemTier.ZANITE, 8.0f, -3.1f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("gravitite_shovel", new GravititeShovelItem(AetherItemTier.GRAVITITE, 1.5f, -3.0f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("gravitite_pickaxe", new GravititePickaxeItem(AetherItemTier.GRAVITITE, 1, -2.8f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("gravitite_axe", new GravititeAxeItem(AetherItemTier.GRAVITITE, 5.0f, -3.0f, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS))),
				item("valkyrie_shovel", new ShovelItem(AetherItemTier.VALKYRIE, 1.5f, -3.0f, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_TOOLS))),
				item("valkyrie_pickaxe", new PickaxeItem(AetherItemTier.VALKYRIE, 1, -2.8f, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_TOOLS))),
				item("valkyrie_axe", new AxeItem(AetherItemTier.VALKYRIE, 5.0f, -3.0f, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_TOOLS))),
				item("zanite_helmet", new ArmorItem(AetherArmorMaterial.ZANITE, EquipmentSlotType.HEAD, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("zanite_chestplate", new ArmorItem(AetherArmorMaterial.ZANITE, EquipmentSlotType.CHEST, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("zanite_leggings", new ArmorItem(AetherArmorMaterial.ZANITE, EquipmentSlotType.LEGS, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("zanite_boots", new ArmorItem(AetherArmorMaterial.ZANITE, EquipmentSlotType.FEET, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("gravitite_helmet", new ArmorItem(AetherArmorMaterial.GRAVITITE, EquipmentSlotType.HEAD, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("gravitite_chestplate", new ArmorItem(AetherArmorMaterial.GRAVITITE, EquipmentSlotType.CHEST, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("gravitite_leggings", new ArmorItem(AetherArmorMaterial.GRAVITITE, EquipmentSlotType.LEGS, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("gravitite_boots", new ArmorItem(AetherArmorMaterial.GRAVITITE, EquipmentSlotType.FEET, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("neptune_helmet", new ArmorItem(AetherArmorMaterial.NEPTUNE, EquipmentSlotType.HEAD, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("neptune_chestplate", new ArmorItem(AetherArmorMaterial.NEPTUNE, EquipmentSlotType.CHEST, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("neptune_leggings", new ArmorItem(AetherArmorMaterial.NEPTUNE, EquipmentSlotType.LEGS, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("neptune_boots", new ArmorItem(AetherArmorMaterial.NEPTUNE, EquipmentSlotType.FEET, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("phoenix_helmet", new ArmorItem(AetherArmorMaterial.PHOENIX, EquipmentSlotType.HEAD, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("phoenix_chestplate", new ArmorItem(AetherArmorMaterial.PHOENIX, EquipmentSlotType.CHEST, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("phoenix_leggings", new ArmorItem(AetherArmorMaterial.PHOENIX, EquipmentSlotType.LEGS, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("phoenix_boots", new ArmorItem(AetherArmorMaterial.PHOENIX, EquipmentSlotType.FEET, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("obsidian_helmet", new ArmorItem(AetherArmorMaterial.OBSIDIAN, EquipmentSlotType.HEAD, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("obsidian_chestplate", new ArmorItem(AetherArmorMaterial.OBSIDIAN, EquipmentSlotType.CHEST, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("obsidian_leggings", new ArmorItem(AetherArmorMaterial.OBSIDIAN, EquipmentSlotType.LEGS, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("obsidian_boots", new ArmorItem(AetherArmorMaterial.OBSIDIAN, EquipmentSlotType.FEET, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("valkyrie_helmet", new ArmorItem(AetherArmorMaterial.VALKYRIE, EquipmentSlotType.HEAD, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("valkyrie_chestplate", new ArmorItem(AetherArmorMaterial.VALKYRIE, EquipmentSlotType.CHEST, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("valkyrie_leggings", new ArmorItem(AetherArmorMaterial.VALKYRIE, EquipmentSlotType.LEGS, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("valkyrie_boots", new ArmorItem(AetherArmorMaterial.VALKYRIE, EquipmentSlotType.FEET, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_COMBAT))),
				item("blueberry", new Item(new Item.Properties()
					.food(new Food.Builder().fastToEat().hunger(2).build())
					.group(AetherItemGroups.AETHER_FOOD))),
				item("white_apple", new Item(new Item.Properties()
					.food(new Food.Builder().setAlwaysEdible().fastToEat().hunger(0).build())
					.group(AetherItemGroups.AETHER_FOOD))),
				item("blue_gummy_swet", new GummySwetItem(new Item.Properties()
					.food(new Food.Builder().hunger(20).build())
					.group(AetherItemGroups.AETHER_FOOD))),
				item("golden_gummy_swet", new GummySwetItem(new Item.Properties()
					.food(new Food.Builder().hunger(20).build())
					.group(AetherItemGroups.AETHER_FOOD))),
				item("healing_stone", new Item(new Item.Properties()
					.food(new Food.Builder().setAlwaysEdible().hunger(0)
						.effect(new EffectInstance(Effects.INSTANT_HEALTH, 610, 0), 1.0f).build())
					.group(AetherItemGroups.AETHER_FOOD))),
				item("candy_cane", new Item(new Item.Properties()
					.food(new Food.Builder().hunger(2).build())
					.group(AetherItemGroups.AETHER_FOOD))),
				item("gingerbread_man", new Item(new Item.Properties()
					.food(new Food.Builder().hunger(2).build())
					.group(AetherItemGroups.AETHER_FOOD))),
				item("skyroot_stick", new Item(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS))),
				
				item("skyroot_sword", new SwordItem(AetherItemTier.SKYROOT, 3, -2.4f, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("holystone_sword", new SwordItem(AetherItemTier.HOLYSTONE, 3, -2.4f, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("zanite_sword", new SwordItem(AetherItemTier.ZANITE, 3, -2.4f, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("gravitite_sword", new SwordItem(AetherItemTier.GRAVITITE, 3, -2.4f, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
				item("mimic_spawn_egg", new SpawnEggItem(
						EntityType.Builder.create(MimicEntity::new, EntityClassification.MONSTER).size(1.0f, 2.0f).build("mimic"),
						/*primary color:*/ 0xB18132, /*secondary color:*/ 0x605A4E,
						new Item.Properties().group(ItemGroup.MISC))),
				
			});
		}

		@SuppressWarnings("deprecation")
		private static void registerBlockItems(RegistryEvent.Register<Item> event) {
			Item.Properties properties = new Item.Properties().group(AetherItemGroups.AETHER_BLOCKS);
			for (Block block : AetherBlocks.Registration.blocks) {
				Item item;
				if (block instanceof IAetherBlockColor) {
					IAetherBlockColor iaetherblockcolor = (IAetherBlockColor) block;
					item = new TintedBlockItem(iaetherblockcolor.getColor(false), iaetherblockcolor.getColor(true),
						block, properties);
				}
				else {
					item = new BlockItem(block, properties);
				}
				item.setRegistryName(block.getRegistryName());
				event.getRegistry().register(item);
			}
		}

		private static BlockItem block(Block block) {
			return block(block, AetherItemGroups.AETHER_BLOCKS);
		}

		private static BlockItem block(Block block, ItemGroup itemGroup) {
			return block(block, new Item.Properties().group(itemGroup));
		}

		private static BlockItem block(Block block, Item.Properties properties) {
			if (block instanceof IAetherBlockColor) {
				IAetherBlockColor iaetherblockcolor = (IAetherBlockColor) block;
				return item(block.getRegistryName().toString(), new TintedBlockItem(iaetherblockcolor.getColor(false),
					iaetherblockcolor.getColor(true), block, properties));
			}
			return item(block.getRegistryName().toString(), new BlockItem(block, properties));
		}

		private static <I extends Item> I item(String name, I item) {
			item.setRegistryName(name);
			return item;
		}

		@SubscribeEvent
		public static void registerEnchantmentFuels(RegistryEvent.Register<AetherEnchantmentFuel> event) {
			event.getRegistry().register(

				new AetherEnchantmentFuel(AetherItems.AMBROSIUM_SHARD, 500)

			);
		}

		@SubscribeEvent
		public static void registerFreezableFuels(RegistryEvent.Register<AetherFreezableFuel> event) {
			event.getRegistry().registerAll(

				new AetherFreezableFuel(AetherBlocks.ICESTONE.asItem(), 500)

			);
		}

	}

}
