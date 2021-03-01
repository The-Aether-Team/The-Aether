package com.gildedgames.aether.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.dungeon.DungeonTypes;
import com.gildedgames.aether.item.accessories.AccessoryItem;
import com.gildedgames.aether.item.accessories.ring.ZaniteRingItem;
import com.gildedgames.aether.item.food.HealingStoneItem;
import com.gildedgames.aether.item.materials.SkyrootStickItem;
import com.gildedgames.aether.item.misc.DungeonKeyItem;
import com.gildedgames.aether.item.food.GummySwetItem;
import com.gildedgames.aether.item.materials.SwetBallItem;
import com.gildedgames.aether.item.combat.*;
import com.gildedgames.aether.item.misc.LoreBookItem;
import com.gildedgames.aether.item.tools.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Aether.MODID);

	public static final Rarity AETHER_LOOT = Rarity.create("AETHER_LOOT", TextFormatting.GREEN);

	// Tools
	public static final RegistryObject<PickaxeItem> SKYROOT_PICKAXE = ITEMS.register("skyroot_pickaxe", SkyrootPickaxeItem::new);
	public static final RegistryObject<AxeItem> SKYROOT_AXE = ITEMS.register("skyroot_axe", SkyrootAxeItem::new);
	public static final RegistryObject<ShovelItem> SKYROOT_SHOVEL = ITEMS.register("skyroot_shovel", SkyrootShovelItem::new);

	public static final RegistryObject<PickaxeItem> HOLYSTONE_PICKAXE = ITEMS.register("holystone_pickaxe", HolystonePickaxeItem::new);
	public static final RegistryObject<AxeItem> HOLYSTONE_AXE = ITEMS.register("holystone_axe", HolystoneAxeItem::new);
	public static final RegistryObject<ShovelItem> HOLYSTONE_SHOVEL = ITEMS.register("holystone_shovel", HolystoneShovelItem::new);

	public static final RegistryObject<PickaxeItem> ZANITE_PICKAXE = ITEMS.register("zanite_pickaxe", ZanitePickaxeItem::new);
	public static final RegistryObject<AxeItem> ZANITE_AXE = ITEMS.register("zanite_axe", ZaniteAxeItem::new);
	public static final RegistryObject<ShovelItem> ZANITE_SHOVEL = ITEMS.register("zanite_shovel", ZaniteShovelItem::new);

	public static final RegistryObject<PickaxeItem> GRAVITITE_PICKAXE = ITEMS.register("gravitite_pickaxe", GravititePickaxeItem::new);
	public static final RegistryObject<AxeItem> GRAVITITE_AXE = ITEMS.register("gravitite_axe", GravititeAxeItem::new);
	public static final RegistryObject<ShovelItem> GRAVITITE_SHOVEL = ITEMS.register("gravitite_shovel", GravititeShovelItem::new);

	public static final RegistryObject<PickaxeItem> VALKYRIE_PICKAXE = ITEMS.register("valkyrie_pickaxe", () -> new ValkyriePickaxeItem(1, -2.8f));
	public static final RegistryObject<AxeItem> VALKYRIE_AXE = ITEMS.register("valkyrie_axe", () -> new ValkyrieAxeItem(5.0f, -3.0f));
	public static final RegistryObject<ShovelItem> VALKYRIE_SHOVEL = ITEMS.register("valkyrie_shovel", () -> new ValkyrieShovelItem(1.5f, -3.0f));

	// Weapons

	public static final RegistryObject<SwordItem> SKYROOT_SWORD = ITEMS.register("skyroot_sword", SkyrootSwordItem::new);
	public static final RegistryObject<SwordItem> HOLYSTONE_SWORD = ITEMS.register("holystone_sword", HolystoneSwordItem::new);
	public static final RegistryObject<SwordItem> ZANITE_SWORD = ITEMS.register("zanite_sword", ZaniteSwordItem::new);
	public static final RegistryObject<SwordItem> GRAVITITE_SWORD = ITEMS.register("gravitite_sword", GravititeSwordItem::new);

	public static final RegistryObject<Item> VALKYRIE_LANCE = ITEMS.register("valkyrie_lance", () -> new ValkyrieLanceItem(ItemTier.DIAMOND, 3, -2.4F));

	public static final RegistryObject<SwordItem> FLAMING_SWORD = ITEMS.register("flaming_sword", FlamingSwordItem::new);
	public static final RegistryObject<SwordItem> LIGHTNING_SWORD = ITEMS.register("lightning_sword", LightningSwordItem::new);
	public static final RegistryObject<SwordItem> HOLY_SWORD = ITEMS.register("holy_sword", HolySwordItem::new);
	public static final RegistryObject<SwordItem> VAMPIRE_BLADE = ITEMS.register("vampire_blade", VampireBladeItem::new);
	public static final RegistryObject<SwordItem> PIG_SLAYER = ITEMS.register("pig_slayer", PigSlayerItem::new);
	public static final RegistryObject<SwordItem> CANDY_CANE_SWORD = ITEMS.register("candy_cane_sword", CandyCaneSwordItem::new);

	public static final RegistryObject<SwordItem> NOTCH_HAMMER = ITEMS.register("notch_hammer", NotchHammerItem::new);

	public static final RegistryObject<Item> LIGHTNING_KNIFE = ITEMS.register("lightning_knife", LightningKnifeItem::new);

	public static final RegistryObject<Item> GOLDEN_DART = ITEMS.register("golden_dart", () -> new DartItem(new Item.Properties().group(AetherItemGroups.AETHER_WEAPONS)));
	public static final RegistryObject<Item> POISON_DART = ITEMS.register("poison_dart", () -> new DartItem(new Item.Properties().group(AetherItemGroups.AETHER_WEAPONS)));
	public static final RegistryObject<Item> ENCHANTED_DART = ITEMS.register("enchanted_dart", () -> new DartItem(new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_WEAPONS)));

	public static final RegistryObject<Item> GOLDEN_DART_SHOOTER = ITEMS.register("golden_dart_shooter",
			() -> new DartShooterItem(GOLDEN_DART, new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_WEAPONS)));
	public static final RegistryObject<Item> POISON_DART_SHOOTER = ITEMS.register("poison_dart_shooter",
			() -> new DartShooterItem(POISON_DART, new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_WEAPONS)));
	public static final RegistryObject<Item> ENCHANTED_DART_SHOOTER  = ITEMS.register("enchanted_dart_shooter",
			() -> new DartShooterItem(ENCHANTED_DART, new Item.Properties().maxStackSize(1).rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_WEAPONS)));

	public static final RegistryObject<BowItem> PHOENIX_BOW = ITEMS.register("phoenix_bow", PhoenixBowItem::new);

	// Armor
	public static final RegistryObject<Item> ZANITE_HELMET = ITEMS.register("zanite_helmet",
			() -> new AetherArmorItem(AetherArmorMaterial.ZANITE, EquipmentSlotType.HEAD, new Item.Properties().group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> ZANITE_CHESTPLATE = ITEMS.register("zanite_chestplate",
			() -> new AetherArmorItem(AetherArmorMaterial.ZANITE, EquipmentSlotType.CHEST, new Item.Properties().group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> ZANITE_LEGGINGS = ITEMS.register("zanite_leggings",
			() -> new AetherArmorItem(AetherArmorMaterial.ZANITE, EquipmentSlotType.LEGS, new Item.Properties().group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> ZANITE_BOOTS = ITEMS.register("zanite_boots",
			() -> new AetherArmorItem(AetherArmorMaterial.ZANITE, EquipmentSlotType.FEET, new Item.Properties().group(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> GRAVITITE_HELMET = ITEMS.register("gravitite_helmet",
			() -> new AetherArmorItem(AetherArmorMaterial.GRAVITITE, EquipmentSlotType.HEAD, new Item.Properties().group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> GRAVITITE_CHESTPLATE = ITEMS.register("gravitite_chestplate",
			() -> new AetherArmorItem(AetherArmorMaterial.GRAVITITE, EquipmentSlotType.CHEST, new Item.Properties().group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> GRAVITITE_LEGGINGS = ITEMS.register("gravitite_leggings",
			() -> new AetherArmorItem(AetherArmorMaterial.GRAVITITE, EquipmentSlotType.LEGS, new Item.Properties().group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> GRAVITITE_BOOTS = ITEMS.register("gravitite_boots",
			() -> new AetherArmorItem(AetherArmorMaterial.GRAVITITE, EquipmentSlotType.FEET, new Item.Properties().group(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> NEPTUNE_HELMET = ITEMS.register("neptune_helmet",
			() -> new AetherArmorItem(AetherArmorMaterial.NEPTUNE, EquipmentSlotType.HEAD, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> NEPTUNE_CHESTPLATE = ITEMS.register("neptune_chestplate",
			() -> new AetherArmorItem(AetherArmorMaterial.NEPTUNE, EquipmentSlotType.CHEST, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> NEPTUNE_LEGGINGS = ITEMS.register("neptune_leggings",
			() -> new AetherArmorItem(AetherArmorMaterial.NEPTUNE, EquipmentSlotType.LEGS, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> NEPTUNE_BOOTS = ITEMS.register("neptune_boots",
			() -> new AetherArmorItem(AetherArmorMaterial.NEPTUNE, EquipmentSlotType.FEET, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> PHOENIX_HELMET = ITEMS.register("phoenix_helmet",
			() -> new AetherArmorItem(AetherArmorMaterial.PHOENIX, EquipmentSlotType.HEAD, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> PHOENIX_CHESTPLATE = ITEMS.register("phoenix_chestplate",
			() -> new AetherArmorItem(AetherArmorMaterial.PHOENIX, EquipmentSlotType.CHEST, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> PHOENIX_LEGGINGS = ITEMS.register("phoenix_leggings",
			() -> new AetherArmorItem(AetherArmorMaterial.PHOENIX, EquipmentSlotType.LEGS, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> PHOENIX_BOOTS = ITEMS.register("phoenix_boots",
			() -> new AetherArmorItem(AetherArmorMaterial.PHOENIX, EquipmentSlotType.FEET, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> OBSIDIAN_HELMET = ITEMS.register("obsidian_helmet",
			() -> new AetherArmorItem(AetherArmorMaterial.OBSIDIAN, EquipmentSlotType.HEAD, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> OBSIDIAN_CHESTPLATE = ITEMS.register("obsidian_chestplate",
			() -> new AetherArmorItem(AetherArmorMaterial.OBSIDIAN, EquipmentSlotType.CHEST, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> OBSIDIAN_LEGGINGS = ITEMS.register("obsidian_leggings",
			() -> new AetherArmorItem(AetherArmorMaterial.OBSIDIAN, EquipmentSlotType.LEGS, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> OBSIDIAN_BOOTS = ITEMS.register("obsidian_boots",
			() -> new AetherArmorItem(AetherArmorMaterial.OBSIDIAN, EquipmentSlotType.FEET, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> VALKYRIE_HELMET = ITEMS.register("valkyrie_helmet",
			() -> new AetherArmorItem(AetherArmorMaterial.VALKYRIE, EquipmentSlotType.HEAD, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> VALKYRIE_CHESTPLATE = ITEMS.register("valkyrie_chestplate",
			() -> new AetherArmorItem(AetherArmorMaterial.VALKYRIE, EquipmentSlotType.CHEST, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> VALKYRIE_LEGGINGS = ITEMS.register("valkyrie_leggings",
			() -> new AetherArmorItem(AetherArmorMaterial.VALKYRIE, EquipmentSlotType.LEGS, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> VALKYRIE_BOOTS = ITEMS.register("valkyrie_boots",
			() -> new AetherArmorItem(AetherArmorMaterial.VALKYRIE, EquipmentSlotType.FEET, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> SENTRY_BOOTS = ITEMS.register("sentry_boots",
			() -> new AetherArmorItem(AetherArmorMaterial.SENTRY, EquipmentSlotType.FEET, new Item.Properties().rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_ARMOR)));

	// Food
	public static final RegistryObject<Item> BLUE_BERRY = ITEMS.register("blue_berry",
			() -> new Item(new Item.Properties().food(new Food.Builder().fastToEat().hunger(2).build()).group(AetherItemGroups.AETHER_FOOD)));
	public static final RegistryObject<Item> ENCHANTED_BERRY = ITEMS.register("enchanted_berry",
			() -> new Item(new Item.Properties().rarity(Rarity.RARE).food(new Food.Builder().fastToEat().hunger(8).build()).group(AetherItemGroups.AETHER_FOOD)));
	public static final RegistryObject<Item> WHITE_APPLE = ITEMS.register("white_apple",
			() -> new Item(new Item.Properties().food(new Food.Builder().fastToEat().hunger(0).build()).group(AetherItemGroups.AETHER_FOOD)));
	public static final RegistryObject<Item> BLUE_GUMMY_SWET = ITEMS.register("blue_gummy_swet",
			() -> new GummySwetItem(new Item.Properties().food(new Food.Builder().fastToEat().hunger(20).build()).group(AetherItemGroups.AETHER_FOOD)));
	public static final RegistryObject<Item> GOLDEN_GUMMY_SWET = ITEMS.register("golden_gummy_swet",
			() -> new GummySwetItem(new Item.Properties().food(new Food.Builder().fastToEat().hunger(20).build()).group(AetherItemGroups.AETHER_FOOD)));
	public static final RegistryObject<Item> HEALING_STONE = ITEMS.register("healing_stone", HealingStoneItem::new);
	public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane",
			() -> new Item(new Item.Properties().food(new Food.Builder().fastToEat().hunger(2).build()).group(AetherItemGroups.AETHER_FOOD)));
	public static final RegistryObject<Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man",
			() -> new Item(new Item.Properties().food(new Food.Builder().fastToEat().hunger(2).build()).group(AetherItemGroups.AETHER_FOOD)));

	// Accessories
	public static final RegistryObject<Item> IRON_RING = ITEMS.register("iron_ring",
			() -> new AccessoryItem(new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> GOLD_RING = ITEMS.register("golden_ring",
			() -> new AccessoryItem(new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> ZANITE_RING = ITEMS.register("zanite_ring",
			() -> new ZaniteRingItem(new Item.Properties().maxStackSize(1).defaultMaxDamage(49).group(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> ICE_RING = ITEMS.register("ice_ring",
			() -> new AccessoryItem(new Item.Properties().maxStackSize(1).defaultMaxDamage(125).group(AetherItemGroups.AETHER_ACCESSORIES)));

	//item("leather_gloves", new DyeableGlovesItem(ArmorMaterial.LEATHER, new Item.Properties().group(ItemGroup.COMBAT))),
	//item("chainmail_gloves", new GlovesItem(ArmorMaterial.CHAIN, new Item.Properties().group(ItemGroup.COMBAT))),
	//item("iron_gloves", new GlovesItem(ArmorMaterial.IRON, new Item.Properties().group(ItemGroup.COMBAT))),
	//item("diamond_gloves", new GlovesItem(ArmorMaterial.DIAMOND, new Item.Properties().group(ItemGroup.COMBAT))),
	//item("golden_gloves", new GlovesItem(ArmorMaterial.GOLD, new Item.Properties().group(ItemGroup.COMBAT))),

	//item("zanite_gloves", new GlovesItem(AetherArmorMaterial.ZANITE, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
	//item("gravitite_gloves", new GlovesItem(AetherArmorMaterial.GRAVITITE, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
	//item("neptune_gloves", new GlovesItem(AetherArmorMaterial.NEPTUNE, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
	//item("phoenix_gloves", new GlovesItem(AetherArmorMaterial.PHOENIX, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
	//item("obsidian_gloves", new GlovesItem(AetherArmorMaterial.OBSIDIAN, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),
	//item("valkyrie_gloves", new GlovesItem(AetherArmorMaterial.VALKYRIE, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT))),

	//item("iron_pendant", new PendantItem(new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("golden_pendant", new PendantItem(new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("zanite_pendant", new PendantItem(new Item.Properties().maxDamage(98).maxStackSize(1).group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("ice_pendant", new PendantItem(new Item.Properties().maxDamage(250).maxStackSize(1).group(AetherItemGroups.AETHER_ACCESSORIES))),

	//item("red_cape", new CapeItem(???, new Item.Properties().group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("blue_cape", new CapeItem(???, new Item.Properties().group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("yellow_cape", new CapeItem(???, new Item.Properties().group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("white_cape", new CapeItem(???, new Item.Properties().group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("swet_cape", new CapeItem(???, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("invisibility_cape", new CapeItem(???, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("agility_cape", new CapeItem(???, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("valkyrie_cape", new CapeItem(???, new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_ACCESSORIES))),

	//item("golden_feather", new AccessoryItem(new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("regeneration_stone", new AccessoryItem(new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("iron_bubble", new AccessoryItem(new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_ACCESSORIES))),
	//item("repulsion_shield", new ShieldAccessoryItem(new Item.Properties().rarity(Aether.AETHER_LOOT).maxDamage(512).group(AetherItemGroups.AETHER_ACCESSORIES))),

	// Materials
	public static final RegistryObject<Item> SKYROOT_STICK = ITEMS.register("skyroot_stick", () -> new SkyrootStickItem(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> GOLDEN_AMBER = ITEMS.register("golden_amber", () -> new Item(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> SWET_BALL = ITEMS.register("swet_ball", () -> new SwetBallItem(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> AECHOR_PETAL = ITEMS.register("aechor_petal", () -> new Item(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> AMBROSIUM_SHARD = ITEMS.register("ambrosium_shard", () -> new Item(new Item.Properties()
			.food(new Food.Builder().setAlwaysEdible().fastToEat().effect(() -> new EffectInstance(Effects.INSTANT_HEALTH, 1), 1.0F).build()).group(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> ZANITE_GEMSTONE = ITEMS.register("zanite_gemstone", () -> new Item(new Item.Properties().group(AetherItemGroups.AETHER_MATERIALS)));

	// Misc
	public static final RegistryObject<Item> VICTORY_MEDAL = ITEMS.register("victory_medal",
			() -> new Item(new Item.Properties().maxStackSize(10).rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> BRONZE_DUNGEON_KEY = ITEMS.register("bronze_dungeon_key",
			() -> new DungeonKeyItem(DungeonTypes.BRONZE, new Item.Properties().maxStackSize(1).rarity(AETHER_LOOT).isImmuneToFire().group(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> SILVER_DUNGEON_KEY = ITEMS.register("silver_dungeon_key",
			() -> new DungeonKeyItem(DungeonTypes.SILVER, new Item.Properties().maxStackSize(1).rarity(AETHER_LOOT).isImmuneToFire().group(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> GOLD_DUNGEON_KEY = ITEMS.register("gold_dungeon_key",
			() -> new DungeonKeyItem(DungeonTypes.GOLD, new Item.Properties().maxStackSize(1).rarity(AETHER_LOOT).isImmuneToFire().group(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> MUSIC_DISC_AETHER_TUNE = ITEMS.register("music_disc_aether_tune",
			() -> new MusicDiscItem(1, AetherSoundEvents.MUSIC_DISC_AETHER_TUNE, new Item.Properties().maxStackSize(1).rarity(Rarity.RARE).group(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> MUSIC_DISC_ASCENDING_DAWN  = ITEMS.register("music_disc_ascending_dawn",
			() -> new MusicDiscItem(2, AetherSoundEvents.MUSIC_DISC_ASCENDING_DAWN, new Item.Properties().maxStackSize(1).rarity(Rarity.RARE).group(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> MUSIC_DISC_WELCOMING_SKIES  = ITEMS.register("music_disc_welcoming_skies",
			() -> new MusicDiscItem(3, AetherSoundEvents.MUSIC_DISC_WELCOMING_SKIES, new Item.Properties().maxStackSize(1).rarity(Rarity.RARE).group(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> MUSIC_DISC_LEGACY  = ITEMS.register("music_disc_legacy",
			() -> new MusicDiscItem(4, AetherSoundEvents.MUSIC_DISC_LEGACY, new Item.Properties().maxStackSize(1).rarity(Rarity.RARE).group(AetherItemGroups.AETHER_MISC)));

	//skyroot_bucket = item("skyroot_bucket", new SkyrootBucketItem(new Item.Properties().maxStackSize(16).group(AetherItemGroups.MISC))),
	//item("skyroot_water_bucket", new SkyrootWaterBucketItem(new Item.Properties().containerItem(skyroot_bucket).maxStackSize(1).group(AetherItemGroups.AETHER_MISC))),
	//item("skyroot_poison_bucket", new SkyrootPoisonBucketItem(new Item.Properties().containerItem(skyroot_bucket).maxStackSize(1).group(AetherItemGroups.AETHER_MISC))),
	//item("skyroot_remedy_bucket", new SkyrootRemedyBucketItem(new Item.Properties().containerItem(skyroot_bucket).maxStackSize(1).group(AetherItemGroups.AETHER_MISC))),
	//item("skyroot_milk_bucket", new SkyrootMilkBucketItem(new Item.Properties().containerItem(skyroot_bucket).maxStackSize(1).group(AetherItemGroups.AETHER_MISC))),

	//item("cold_parachute", new ColdParachuteItem(new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_MISC))),
	//item("golden_parachute", new GoldenParachuteItem(new Item.Properties().maxDamage(20).maxStackSize(1).group(AetherItemGroups.AETHER_MISC))),

	//item("nature_staff", new NatureStaffItem(new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_TOOLS))),
	//item("cloud_staff", new CloudStaffItem(new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_TOOLS))),

	//item("moa_egg", new MoaEggItem(new Item.Properties().maxStackSize(1).group(AetherItemGroups.AETHER_MISC))),

	//item("life_shard", new LifeShardItem(new Item.Properties().rarity(Aether.AETHER_LOOT).group(AetherItemGroups.AETHER_MISC))),

	public static final RegistryObject<Item> BOOK_OF_LORE = ITEMS.register("book_of_lore",
			() -> new LoreBookItem(new Item.Properties().maxStackSize(1).rarity(AETHER_LOOT).group(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<SpawnEggItem> AECHOR_PLANT_SPAWN_EGG = ITEMS.register("aechor_plant_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.AECHOR_PLANT_TYPE,0x076178, 0x4BC69E, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> AERWHALE_SPAWN_EGG = ITEMS.register("aerwhale_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.AERWHALE_TYPE,0x79B7D1, 0xE0D25C, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> COCKATRICE_SPAWN_EGG = ITEMS.register("cockatrice_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.COCKATRICE_TYPE,0x6CB15C, 0x6C579D, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> FLYING_COW_SPAWN_EGG = ITEMS.register("flying_cow_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.FLYING_COW_TYPE,0xD8D8D8, 0xFFD939, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> MIMIC_SPAWN_EGG = ITEMS.register("mimic_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.MIMIC_TYPE,0xB18132,0x605A4E, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> MOA_SPAWN_EGG = ITEMS.register("moa_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.MOA_TYPE,0x87BFEF, 0x7A7A7A, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> PHYG_SPAWN_EGG = ITEMS.register("phyg_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.PHYG_TYPE,0xFFC1D0, 0xFFD939, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> SENTRY_SPAWN_EGG = ITEMS.register("sentry_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.SENTRY_TYPE,0x808080,0x3A8AEC, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> SHEEPUFF_SPAWN_EGG = ITEMS.register("sheepuff_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.SHEEPUFF_TYPE,0xE2FCFF, 0xCB9090, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> WHIRLWIND_SPAWN_EGG = ITEMS.register("whirlwind_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.WHIRLWIND_TYPE,0x9fc3f7, 0xffffff, new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<SpawnEggItem> ZEPHYR_SPAWN_EGG = ITEMS.register("zephyr_spawn_egg",
			() -> new SpawnEggItem(AetherEntityTypes.ZEPHYR_TYPE,0xDFDFDF, 0x99CFE8, new Item.Properties().group(ItemGroup.MISC)));
}