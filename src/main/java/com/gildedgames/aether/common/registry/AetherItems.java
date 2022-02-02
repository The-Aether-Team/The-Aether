package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.item.accessories.cape.*;
import com.gildedgames.aether.common.item.accessories.gloves.GlovesItem;
import com.gildedgames.aether.common.item.accessories.gloves.LeatherGlovesItem;
import com.gildedgames.aether.common.item.accessories.gloves.ZaniteGlovesItem;
import com.gildedgames.aether.common.item.accessories.gloves.GoldGlovesItem;
import com.gildedgames.aether.common.item.accessories.miscellaneous.GoldenFeatherItem;
import com.gildedgames.aether.common.item.accessories.miscellaneous.IronBubbleItem;
import com.gildedgames.aether.common.item.accessories.miscellaneous.RegenerationStoneItem;
import com.gildedgames.aether.common.item.accessories.miscellaneous.RepulsionShieldItem;
import com.gildedgames.aether.common.item.accessories.pendant.IcePendantItem;
import com.gildedgames.aether.common.item.accessories.pendant.PendantItem;
import com.gildedgames.aether.common.item.accessories.pendant.ZanitePendantItem;
import com.gildedgames.aether.common.item.accessories.ring.IceRingItem;
import com.gildedgames.aether.common.item.accessories.ring.RingItem;
import com.gildedgames.aether.common.item.combat.loot.*;
import com.gildedgames.aether.common.item.food.WhiteAppleItem;
import com.gildedgames.aether.common.item.materials.AmbrosiumShardItem;
import com.gildedgames.aether.common.item.materials.util.ISwetBallConversion;
import com.gildedgames.aether.common.item.miscellaneous.bucket.*;
import com.gildedgames.aether.common.item.tools.gravitite.GravititeAxeItem;
import com.gildedgames.aether.common.item.tools.gravitite.GravititeHoeItem;
import com.gildedgames.aether.common.item.tools.gravitite.GravititePickaxeItem;
import com.gildedgames.aether.common.item.tools.gravitite.GravititeShovelItem;
import com.gildedgames.aether.common.item.tools.holystone.HolystoneAxeItem;
import com.gildedgames.aether.common.item.tools.holystone.HolystoneHoeItem;
import com.gildedgames.aether.common.item.tools.holystone.HolystonePickaxeItem;
import com.gildedgames.aether.common.item.tools.holystone.HolystoneShovelItem;
import com.gildedgames.aether.common.item.tools.skyroot.SkyrootAxeItem;
import com.gildedgames.aether.common.item.tools.skyroot.SkyrootHoeItem;
import com.gildedgames.aether.common.item.tools.skyroot.SkyrootPickaxeItem;
import com.gildedgames.aether.common.item.tools.skyroot.SkyrootShovelItem;
import com.gildedgames.aether.common.item.tools.valkyrie.ValkyrieAxeItem;
import com.gildedgames.aether.common.item.tools.valkyrie.ValkyrieHoeItem;
import com.gildedgames.aether.common.item.tools.valkyrie.ValkyriePickaxeItem;
import com.gildedgames.aether.common.item.tools.valkyrie.ValkyrieShovelItem;
import com.gildedgames.aether.common.item.tools.zanite.ZaniteAxeItem;
import com.gildedgames.aether.common.item.tools.zanite.ZaniteHoeItem;
import com.gildedgames.aether.common.item.tools.zanite.ZanitePickaxeItem;
import com.gildedgames.aether.common.item.tools.zanite.ZaniteShovelItem;
import com.gildedgames.aether.core.registry.AetherDungeonTypes;
import com.gildedgames.aether.common.item.accessories.ring.ZaniteRingItem;
import com.gildedgames.aether.common.item.food.HealingStoneItem;
import com.gildedgames.aether.common.item.miscellaneous.*;
import com.gildedgames.aether.common.item.materials.SkyrootStickItem;
import com.gildedgames.aether.common.item.food.GummySwetItem;
import com.gildedgames.aether.common.item.materials.SwetBallItem;
import com.gildedgames.aether.common.item.combat.*;
import com.gildedgames.aether.common.item.miscellaneous.LoreBookItem;
import com.gildedgames.aether.core.registry.AetherMoaTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.RegistryObject;

public class AetherItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Aether.MODID);

	public static final Rarity AETHER_LOOT = Rarity.create("AETHER_LOOT", ChatFormatting.GREEN);

	// Tools
	public static final RegistryObject<PickaxeItem> SKYROOT_PICKAXE = ITEMS.register("skyroot_pickaxe", SkyrootPickaxeItem::new);
	public static final RegistryObject<AxeItem> SKYROOT_AXE = ITEMS.register("skyroot_axe", SkyrootAxeItem::new);
	public static final RegistryObject<ShovelItem> SKYROOT_SHOVEL = ITEMS.register("skyroot_shovel", SkyrootShovelItem::new);
	public static final RegistryObject<HoeItem> SKYROOT_HOE = ITEMS.register("skyroot_hoe", SkyrootHoeItem::new);

	public static final RegistryObject<PickaxeItem> HOLYSTONE_PICKAXE = ITEMS.register("holystone_pickaxe", HolystonePickaxeItem::new);
	public static final RegistryObject<AxeItem> HOLYSTONE_AXE = ITEMS.register("holystone_axe", HolystoneAxeItem::new);
	public static final RegistryObject<ShovelItem> HOLYSTONE_SHOVEL = ITEMS.register("holystone_shovel", HolystoneShovelItem::new);
	public static final RegistryObject<HoeItem> HOLYSTONE_HOE = ITEMS.register("holystone_hoe", HolystoneHoeItem::new);

	public static final RegistryObject<PickaxeItem> ZANITE_PICKAXE = ITEMS.register("zanite_pickaxe", ZanitePickaxeItem::new);
	public static final RegistryObject<AxeItem> ZANITE_AXE = ITEMS.register("zanite_axe", ZaniteAxeItem::new);
	public static final RegistryObject<ShovelItem> ZANITE_SHOVEL = ITEMS.register("zanite_shovel", ZaniteShovelItem::new);
	public static final RegistryObject<HoeItem> ZANITE_HOE = ITEMS.register("zanite_hoe", ZaniteHoeItem::new);

	public static final RegistryObject<PickaxeItem> GRAVITITE_PICKAXE = ITEMS.register("gravitite_pickaxe", GravititePickaxeItem::new);
	public static final RegistryObject<AxeItem> GRAVITITE_AXE = ITEMS.register("gravitite_axe", GravititeAxeItem::new);
	public static final RegistryObject<ShovelItem> GRAVITITE_SHOVEL = ITEMS.register("gravitite_shovel", GravititeShovelItem::new);
	public static final RegistryObject<HoeItem> GRAVITITE_HOE = ITEMS.register("gravitite_hoe", GravititeHoeItem::new);

	public static final RegistryObject<PickaxeItem> VALKYRIE_PICKAXE = ITEMS.register("valkyrie_pickaxe", () -> new ValkyriePickaxeItem(2, -2.8F));
	public static final RegistryObject<AxeItem> VALKYRIE_AXE = ITEMS.register("valkyrie_axe", () -> new ValkyrieAxeItem(6.0F, -3.0F));
	public static final RegistryObject<ShovelItem> VALKYRIE_SHOVEL = ITEMS.register("valkyrie_shovel", () -> new ValkyrieShovelItem(2.5F, -3.0F));
	public static final RegistryObject<HoeItem> VALKYRIE_HOE = ITEMS.register("valkyrie_hoe", () -> new ValkyrieHoeItem(-3, 0.0F));

	// Weapons
	public static final RegistryObject<SwordItem> SKYROOT_SWORD = ITEMS.register("skyroot_sword", SkyrootSwordItem::new);
	public static final RegistryObject<SwordItem> HOLYSTONE_SWORD = ITEMS.register("holystone_sword", HolystoneSwordItem::new);
	public static final RegistryObject<SwordItem> ZANITE_SWORD = ITEMS.register("zanite_sword", ZaniteSwordItem::new);
	public static final RegistryObject<SwordItem> GRAVITITE_SWORD = ITEMS.register("gravitite_sword", GravititeSwordItem::new);

	public static final RegistryObject<Item> VALKYRIE_LANCE = ITEMS.register("valkyrie_lance", () -> new ValkyrieLanceItem(Tiers.DIAMOND, 4, -2.4F));

	public static final RegistryObject<SwordItem> FLAMING_SWORD = ITEMS.register("flaming_sword", FlamingSwordItem::new);
	public static final RegistryObject<SwordItem> LIGHTNING_SWORD = ITEMS.register("lightning_sword", LightningSwordItem::new);
	public static final RegistryObject<SwordItem> HOLY_SWORD = ITEMS.register("holy_sword", HolySwordItem::new);
	public static final RegistryObject<SwordItem> VAMPIRE_BLADE = ITEMS.register("vampire_blade", VampireBladeItem::new);
	public static final RegistryObject<SwordItem> PIG_SLAYER = ITEMS.register("pig_slayer", PigSlayerItem::new);
	public static final RegistryObject<SwordItem> CANDY_CANE_SWORD = ITEMS.register("candy_cane_sword", CandyCaneSwordItem::new);

	public static final RegistryObject<SwordItem> HAMMER_OF_NOTCH = ITEMS.register("hammer_of_notch", HammerOfNotchItem::new);

	public static final RegistryObject<Item> LIGHTNING_KNIFE = ITEMS.register("lightning_knife", LightningKnifeItem::new);

	public static final RegistryObject<Item> GOLDEN_DART = ITEMS.register("golden_dart", () -> new DartItem(AetherEntityTypes.GOLDEN_DART::get, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS)));
	public static final RegistryObject<Item> POISON_DART = ITEMS.register("poison_dart", () -> new DartItem(AetherEntityTypes.POISON_DART::get, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS)));
	public static final RegistryObject<Item> ENCHANTED_DART = ITEMS.register("enchanted_dart", () -> new DartItem(AetherEntityTypes.ENCHANTED_DART::get, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS)));

	public static final RegistryObject<Item> GOLDEN_DART_SHOOTER = ITEMS.register("golden_dart_shooter", () -> new DartShooterItem(GOLDEN_DART, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_WEAPONS)));
	public static final RegistryObject<Item> POISON_DART_SHOOTER = ITEMS.register("poison_dart_shooter", () -> new DartShooterItem(POISON_DART, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_WEAPONS)));
	public static final RegistryObject<Item> ENCHANTED_DART_SHOOTER  = ITEMS.register("enchanted_dart_shooter", () -> new DartShooterItem(ENCHANTED_DART, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS)));

	public static final RegistryObject<BowItem> PHOENIX_BOW = ITEMS.register("phoenix_bow", PhoenixBowItem::new);

	// Armor
	public static final RegistryObject<Item> ZANITE_HELMET = ITEMS.register("zanite_helmet", () -> new AetherArmorItem(AetherArmorMaterials.ZANITE, EquipmentSlot.HEAD, new Item.Properties().tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> ZANITE_CHESTPLATE = ITEMS.register("zanite_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.ZANITE, EquipmentSlot.CHEST, new Item.Properties().tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> ZANITE_LEGGINGS = ITEMS.register("zanite_leggings", () -> new AetherArmorItem(AetherArmorMaterials.ZANITE, EquipmentSlot.LEGS, new Item.Properties().tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> ZANITE_BOOTS = ITEMS.register("zanite_boots", () -> new AetherArmorItem(AetherArmorMaterials.ZANITE, EquipmentSlot.FEET, new Item.Properties().tab(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> GRAVITITE_HELMET = ITEMS.register("gravitite_helmet", () -> new AetherArmorItem(AetherArmorMaterials.GRAVITITE, EquipmentSlot.HEAD, new Item.Properties().tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> GRAVITITE_CHESTPLATE = ITEMS.register("gravitite_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.GRAVITITE, EquipmentSlot.CHEST, new Item.Properties().tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> GRAVITITE_LEGGINGS = ITEMS.register("gravitite_leggings", () -> new AetherArmorItem(AetherArmorMaterials.GRAVITITE, EquipmentSlot.LEGS, new Item.Properties().tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> GRAVITITE_BOOTS = ITEMS.register("gravitite_boots", () -> new AetherArmorItem(AetherArmorMaterials.GRAVITITE, EquipmentSlot.FEET, new Item.Properties().tab(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> NEPTUNE_HELMET = ITEMS.register("neptune_helmet", () -> new AetherArmorItem(AetherArmorMaterials.NEPTUNE, EquipmentSlot.HEAD, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> NEPTUNE_CHESTPLATE = ITEMS.register("neptune_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.NEPTUNE, EquipmentSlot.CHEST, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> NEPTUNE_LEGGINGS = ITEMS.register("neptune_leggings", () -> new AetherArmorItem(AetherArmorMaterials.NEPTUNE, EquipmentSlot.LEGS, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> NEPTUNE_BOOTS = ITEMS.register("neptune_boots", () -> new AetherArmorItem(AetherArmorMaterials.NEPTUNE, EquipmentSlot.FEET, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> PHOENIX_HELMET = ITEMS.register("phoenix_helmet", () -> new AetherArmorItem(AetherArmorMaterials.PHOENIX, EquipmentSlot.HEAD, new Item.Properties().rarity(AETHER_LOOT).fireResistant().tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> PHOENIX_CHESTPLATE = ITEMS.register("phoenix_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.PHOENIX, EquipmentSlot.CHEST, new Item.Properties().rarity(AETHER_LOOT).fireResistant().tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> PHOENIX_LEGGINGS = ITEMS.register("phoenix_leggings", () -> new AetherArmorItem(AetherArmorMaterials.PHOENIX, EquipmentSlot.LEGS, new Item.Properties().rarity(AETHER_LOOT).fireResistant().tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> PHOENIX_BOOTS = ITEMS.register("phoenix_boots", () -> new AetherArmorItem(AetherArmorMaterials.PHOENIX, EquipmentSlot.FEET, new Item.Properties().rarity(AETHER_LOOT).fireResistant().tab(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> OBSIDIAN_HELMET = ITEMS.register("obsidian_helmet", () -> new AetherArmorItem(AetherArmorMaterials.OBSIDIAN, EquipmentSlot.HEAD, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> OBSIDIAN_CHESTPLATE = ITEMS.register("obsidian_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.OBSIDIAN, EquipmentSlot.CHEST, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> OBSIDIAN_LEGGINGS = ITEMS.register("obsidian_leggings", () -> new AetherArmorItem(AetherArmorMaterials.OBSIDIAN, EquipmentSlot.LEGS, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> OBSIDIAN_BOOTS = ITEMS.register("obsidian_boots", () -> new AetherArmorItem(AetherArmorMaterials.OBSIDIAN, EquipmentSlot.FEET, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> VALKYRIE_HELMET = ITEMS.register("valkyrie_helmet", () -> new AetherArmorItem(AetherArmorMaterials.VALKYRIE, EquipmentSlot.HEAD, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> VALKYRIE_CHESTPLATE = ITEMS.register("valkyrie_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.VALKYRIE, EquipmentSlot.CHEST, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> VALKYRIE_LEGGINGS = ITEMS.register("valkyrie_leggings", () -> new AetherArmorItem(AetherArmorMaterials.VALKYRIE, EquipmentSlot.LEGS, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));
	public static final RegistryObject<Item> VALKYRIE_BOOTS = ITEMS.register("valkyrie_boots", () -> new AetherArmorItem(AetherArmorMaterials.VALKYRIE, EquipmentSlot.FEET, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));

	public static final RegistryObject<Item> SENTRY_BOOTS = ITEMS.register("sentry_boots", () -> new AetherArmorItem(AetherArmorMaterials.SENTRY, EquipmentSlot.FEET, new Item.Properties().rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_ARMOR)));

	// Food
	public static final RegistryObject<Item> BLUE_BERRY = ITEMS.register("blue_berry", () -> new Item(new Item.Properties().food(AetherFoods.BLUE_BERRY).tab(AetherItemGroups.AETHER_FOOD)));
	public static final RegistryObject<Item> ENCHANTED_BERRY = ITEMS.register("enchanted_berry", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(AetherFoods.ENCHANTED_BERRY).tab(AetherItemGroups.AETHER_FOOD)));
	public static final RegistryObject<Item> WHITE_APPLE = ITEMS.register("white_apple", WhiteAppleItem::new);
	public static final RegistryObject<Item> BLUE_GUMMY_SWET = ITEMS.register("blue_gummy_swet", GummySwetItem::new);
	public static final RegistryObject<Item> GOLDEN_GUMMY_SWET = ITEMS.register("golden_gummy_swet", GummySwetItem::new);
	public static final RegistryObject<Item> HEALING_STONE = ITEMS.register("healing_stone", HealingStoneItem::new);
	public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane", () -> new Item(new Item.Properties().food(AetherFoods.CANDY_CANE).tab(AetherItemGroups.AETHER_FOOD)));
	public static final RegistryObject<Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man", () -> new Item(new Item.Properties().food(AetherFoods.GINGERBREAD_MAN).tab(AetherItemGroups.AETHER_FOOD)));

	// Accessories
	public static final RegistryObject<Item> IRON_RING = ITEMS.register("iron_ring", () -> new RingItem(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_IRON_RING, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> GOLDEN_RING = ITEMS.register("golden_ring", () -> new RingItem(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GOLD_RING, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> ZANITE_RING = ITEMS.register("zanite_ring", () -> new ZaniteRingItem(new Item.Properties().durability(49).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> ICE_RING = ITEMS.register("ice_ring", () -> new IceRingItem(new Item.Properties().durability(125).tab(AetherItemGroups.AETHER_ACCESSORIES)));

	public static final RegistryObject<Item> IRON_PENDANT = ITEMS.register("iron_pendant", () -> new PendantItem("iron_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_IRON_PENDANT, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> GOLDEN_PENDANT = ITEMS.register("golden_pendant", () -> new PendantItem("golden_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GOLD_PENDANT, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> ZANITE_PENDANT = ITEMS.register("zanite_pendant", () -> new ZanitePendantItem(new Item.Properties().durability(98).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> ICE_PENDANT = ITEMS.register("ice_pendant", () -> new IcePendantItem(new Item.Properties().durability(250).tab(AetherItemGroups.AETHER_ACCESSORIES)));

	public static final RegistryObject<Item> LEATHER_GLOVES = ITEMS.register("leather_gloves", () -> new LeatherGlovesItem(0.5D, new Item.Properties().durability(59).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> CHAINMAIL_GLOVES = ITEMS.register("chainmail_gloves", () -> new GlovesItem(0.75D,"chainmail_gloves", () -> SoundEvents.ARMOR_EQUIP_CHAIN, new Item.Properties().durability(131).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> IRON_GLOVES = ITEMS.register("iron_gloves", () -> new GlovesItem(1.0D, "iron_gloves", () -> SoundEvents.ARMOR_EQUIP_IRON, new Item.Properties().durability(250).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> GOLDEN_GLOVES = ITEMS.register("golden_gloves", () -> new GoldGlovesItem(0.5D, new Item.Properties().durability(32).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> DIAMOND_GLOVES = ITEMS.register("diamond_gloves",  () -> new GlovesItem(1.5D, "diamond_gloves", () -> SoundEvents.ARMOR_EQUIP_DIAMOND, new Item.Properties().durability(1561).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> NETHERITE_GLOVES = ITEMS.register("netherite_gloves", () -> new GlovesItem(2.0D, "netherite_gloves", () -> SoundEvents.ARMOR_EQUIP_NETHERITE, new Item.Properties().durability(2031).fireResistant().tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> ZANITE_GLOVES = ITEMS.register("zanite_gloves", () -> new ZaniteGlovesItem(1.0D, new Item.Properties().durability(250).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> GRAVITITE_GLOVES = ITEMS.register("gravitite_gloves", () -> new GlovesItem(1.5D, "gravitite_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, new Item.Properties().durability(1561).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> NEPTUNE_GLOVES = ITEMS.register("neptune_gloves", () -> new GlovesItem(1.5D, "neptune_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, new Item.Properties().stacksTo(1).durability(2031).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> PHOENIX_GLOVES = ITEMS.register("phoenix_gloves", () -> new GlovesItem(1.5D, "phoenix_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, new Item.Properties().stacksTo(1).durability(2031).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> OBSIDIAN_GLOVES = ITEMS.register("obsidian_gloves", () -> new GlovesItem(1.5D, "obsidian_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, new Item.Properties().stacksTo(1).durability(2031).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> VALKYRIE_GLOVES = ITEMS.register("valkyrie_gloves", () -> new GlovesItem(1.5D, "valkyrie_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, new Item.Properties().stacksTo(1).durability(2031).tab(AetherItemGroups.AETHER_ACCESSORIES)));

	public static final RegistryObject<Item> RED_CAPE = ITEMS.register("red_cape", () -> new CapeItem("red_cape", new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> BLUE_CAPE = ITEMS.register("blue_cape", () -> new CapeItem("blue_cape", new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> YELLOW_CAPE = ITEMS.register("yellow_cape", () -> new CapeItem("yellow_cape", new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> WHITE_CAPE = ITEMS.register("white_cape", () -> new CapeItem("white_cape", new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> SWET_CAPE = ITEMS.register("swet_cape", () -> new CapeItem("swet_cape", new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> INVISIBILITY_CLOAK = ITEMS.register("invisibility_cloak", () -> new InvisibilityCloakItem(new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> AGILITY_CAPE = ITEMS.register("agility_cape", () -> new AgilityCapeItem("agility_cape", new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> VALKYRIE_CAPE = ITEMS.register("valkyrie_cape", () -> new ValkyrieCapeItem(new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));

	public static final RegistryObject<Item> GOLDEN_FEATHER = ITEMS.register("golden_feather", () -> new GoldenFeatherItem(new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> REGENERATION_STONE = ITEMS.register("regeneration_stone", () -> new RegenerationStoneItem(new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> IRON_BUBBLE = ITEMS.register("iron_bubble", () -> new IronBubbleItem(new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_ACCESSORIES)));
	public static final RegistryObject<Item> REPULSION_SHIELD = ITEMS.register("repulsion_shield", () -> new RepulsionShieldItem(new Item.Properties().durability(512).tab(AetherItemGroups.AETHER_ACCESSORIES)));

	// Materials
	public static final RegistryObject<Item> SKYROOT_STICK = ITEMS.register("skyroot_stick", () -> new SkyrootStickItem(new Item.Properties().tab(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> GOLDEN_AMBER = ITEMS.register("golden_amber", () -> new Item(new Item.Properties().tab(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> SWET_BALL = ITEMS.register("swet_ball", () -> new SwetBallItem(new Item.Properties().tab(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> AECHOR_PETAL = ITEMS.register("aechor_petal", () -> new Item(new Item.Properties().tab(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> AMBROSIUM_SHARD = ITEMS.register("ambrosium_shard", () -> new AmbrosiumShardItem(new Item.Properties().tab(AetherItemGroups.AETHER_MATERIALS)));
	public static final RegistryObject<Item> ZANITE_GEMSTONE = ITEMS.register("zanite_gemstone", () -> new Item(new Item.Properties().tab(AetherItemGroups.AETHER_MATERIALS)));

	// Misc
	public static final RegistryObject<Item> VICTORY_MEDAL = ITEMS.register("victory_medal", () -> new Item(new Item.Properties().stacksTo(10).rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> BRONZE_DUNGEON_KEY = ITEMS.register("bronze_dungeon_key", () -> new DungeonKeyItem(() -> AetherDungeonTypes.BRONZE, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant().tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> SILVER_DUNGEON_KEY = ITEMS.register("silver_dungeon_key", () -> new DungeonKeyItem(() -> AetherDungeonTypes.SILVER, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant().tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> GOLD_DUNGEON_KEY = ITEMS.register("gold_dungeon_key", () -> new DungeonKeyItem(() -> AetherDungeonTypes.GOLD, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant().tab(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> MUSIC_DISC_AETHER_TUNE = ITEMS.register("music_disc_aether_tune", () -> new RecordItem(1, AetherSoundEvents.ITEM_MUSIC_DISC_AETHER_TUNE, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> MUSIC_DISC_ASCENDING_DAWN  = ITEMS.register("music_disc_ascending_dawn", () -> new RecordItem(2, AetherSoundEvents.ITEM_MUSIC_DISC_ASCENDING_DAWN, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> MUSIC_DISC_WELCOMING_SKIES  = ITEMS.register("music_disc_welcoming_skies", () -> new RecordItem(3, AetherSoundEvents.ITEM_MUSIC_DISC_WELCOMING_SKIES, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> MUSIC_DISC_LEGACY  = ITEMS.register("music_disc_legacy", () -> new RecordItem(4, AetherSoundEvents.ITEM_MUSIC_DISC_LEGACY, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).tab(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> SKYROOT_BUCKET = ITEMS.register("skyroot_bucket", () -> new SkyrootBucketItem(new Item.Properties().stacksTo(16).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> SKYROOT_WATER_BUCKET = ITEMS.register("skyroot_water_bucket", () -> new SkyrootWaterBucketItem(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> SKYROOT_POISON_BUCKET = ITEMS.register("skyroot_poison_bucket", () -> new SkyrootPoisonBucketItem(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> SKYROOT_REMEDY_BUCKET = ITEMS.register("skyroot_remedy_bucket", () -> new SkyrootRemedyBucketItem(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).rarity(Rarity.RARE).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> SKYROOT_MILK_BUCKET = ITEMS.register("skyroot_milk_bucket", () -> new SkyrootMilkBucketItem(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).tab(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> COLD_PARACHUTE = ITEMS.register("cold_parachute", () -> new ParachuteItem(AetherEntityTypes.COLD_PARACHUTE::get, new Item.Properties().durability(1).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> GOLDEN_PARACHUTE = ITEMS.register("golden_parachute", () -> new ParachuteItem(AetherEntityTypes.GOLDEN_PARACHUTE::get, new Item.Properties().durability(20).tab(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> BLUE_MOA_EGG = ITEMS.register("blue_moa_egg", () -> new MoaEggItem(() -> AetherMoaTypes.BLUE, 0x7777FF, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> WHITE_MOA_EGG = ITEMS.register("white_moa_egg", () -> new MoaEggItem(() -> AetherMoaTypes.WHITE, 0xFFFFFF, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> BLACK_MOA_EGG = ITEMS.register("black_moa_egg", () -> new MoaEggItem(() -> AetherMoaTypes.BLACK, 0x222222, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> ORANGE_MOA_EGG = ITEMS.register("orange_moa_egg", () -> new MoaEggItem(() -> AetherMoaTypes.ORANGE, -0xC3D78, new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> NATURE_STAFF = ITEMS.register("nature_staff", () -> new Item(new Item.Properties().durability(100).rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_MISC)));
	public static final RegistryObject<Item> CLOUD_STAFF = ITEMS.register("cloud_staff", CloudStaffItem::new);

	public static final RegistryObject<Item> LIFE_SHARD = ITEMS.register("life_shard", () -> new LifeShardItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> BOOK_OF_LORE = ITEMS.register("book_of_lore", () -> new LoreBookItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).tab(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<Item> AETHER_PORTAL_FRAME = ITEMS.register("aether_portal_frame", () -> new AetherPortalItem(new Item.Properties().stacksTo(1).tab(AetherItemGroups.AETHER_MISC)));

	public static final RegistryObject<SpawnEggItem> AECHOR_PLANT_SPAWN_EGG = ITEMS.register("aechor_plant_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.AECHOR_PLANT, 0x076178, 0x4BC69E, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> AERBUNNY_SPAWN_EGG = ITEMS.register("aerbunny_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.AERBUNNY, 0xe2fcff, 0xffdff9, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> AERWHALE_SPAWN_EGG = ITEMS.register("aerwhale_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.AERWHALE, 0x79B7D1, 0xE0D25C, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> COCKATRICE_SPAWN_EGG = ITEMS.register("cockatrice_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.COCKATRICE, 0x6CB15C, 0x6C579D, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> FLYING_COW_SPAWN_EGG = ITEMS.register("flying_cow_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.FLYING_COW, 0xD8D8D8, 0xFFD939, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> MIMIC_SPAWN_EGG = ITEMS.register("mimic_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.MIMIC, 0xB18132,0x605A4E, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> MOA_SPAWN_EGG = ITEMS.register("moa_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.MOA, 0x87BFEF, 0x7A7A7A, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> PHYG_SPAWN_EGG = ITEMS.register("phyg_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.PHYG, 0xFFC1D0, 0xFFD939, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> SENTRY_SPAWN_EGG = ITEMS.register("sentry_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.SENTRY, 0x808080,0x3A8AEC, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> SHEEPUFF_SPAWN_EGG = ITEMS.register("sheepuff_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.SHEEPUFF, 0xE2FCFF, 0xCB9090, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> BLUE_SWET_SPAWN_EGG = ITEMS.register("blue_swet_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.BLUE_SWET, 0xcdda4f, 0x4fb1da, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> GOLDEN_SWET_SPAWN_EGG = ITEMS.register("golden_swet_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.GOLDEN_SWET, 0x4fb1da, 0xcdda4f, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> WHIRLWIND_SPAWN_EGG = ITEMS.register("whirlwind_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.WHIRLWIND, 0x9fc3f7, 0xffffff, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> EVIL_WHIRLWIND_SPAWN_EGG = ITEMS.register("evil_whirlwind_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.EVIL_WHIRLWIND, 0x9fc3f7, 0x111111, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpawnEggItem> ZEPHYR_SPAWN_EGG = ITEMS.register("zephyr_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.ZEPHYR, 0xDFDFDF, 0x99CFE8, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static void registerAbilities() {
		ISwetBallConversion.registerDefaultConversions();
		ISwetBallConversion.registerBiomeConversions();
	}
}