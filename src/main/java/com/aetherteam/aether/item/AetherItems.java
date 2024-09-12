package com.aetherteam.aether.item;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.accessories.cape.AgilityCapeItem;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.item.accessories.cape.InvisibilityCloakItem;
import com.aetherteam.aether.item.accessories.cape.ValkyrieCapeItem;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.item.accessories.gloves.GoldGlovesItem;
import com.aetherteam.aether.item.accessories.gloves.LeatherGlovesItem;
import com.aetherteam.aether.item.accessories.gloves.ZaniteGlovesItem;
import com.aetherteam.aether.item.accessories.miscellaneous.GoldenFeatherItem;
import com.aetherteam.aether.item.accessories.miscellaneous.IronBubbleItem;
import com.aetherteam.aether.item.accessories.miscellaneous.RegenerationStoneItem;
import com.aetherteam.aether.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.aetherteam.aether.item.accessories.pendant.IcePendantItem;
import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import com.aetherteam.aether.item.accessories.pendant.ZanitePendantItem;
import com.aetherteam.aether.item.accessories.ring.IceRingItem;
import com.aetherteam.aether.item.accessories.ring.RingItem;
import com.aetherteam.aether.item.accessories.ring.ZaniteRingItem;
import com.aetherteam.aether.item.combat.*;
import com.aetherteam.aether.item.combat.loot.*;
import com.aetherteam.aether.item.food.AetherFoods;
import com.aetherteam.aether.item.food.GummySwetItem;
import com.aetherteam.aether.item.food.HealingStoneItem;
import com.aetherteam.aether.item.food.WhiteAppleItem;
import com.aetherteam.aether.item.materials.AmbrosiumShardItem;
import com.aetherteam.aether.item.materials.SkyrootStickItem;
import com.aetherteam.aether.item.materials.SwetBallItem;
import com.aetherteam.aether.item.miscellaneous.*;
import com.aetherteam.aether.item.miscellaneous.bucket.*;
import com.aetherteam.aether.item.tools.gravitite.GravititeAxeItem;
import com.aetherteam.aether.item.tools.gravitite.GravititeHoeItem;
import com.aetherteam.aether.item.tools.gravitite.GravititePickaxeItem;
import com.aetherteam.aether.item.tools.gravitite.GravititeShovelItem;
import com.aetherteam.aether.item.tools.holystone.HolystoneAxeItem;
import com.aetherteam.aether.item.tools.holystone.HolystoneHoeItem;
import com.aetherteam.aether.item.tools.holystone.HolystonePickaxeItem;
import com.aetherteam.aether.item.tools.holystone.HolystoneShovelItem;
import com.aetherteam.aether.item.tools.skyroot.SkyrootAxeItem;
import com.aetherteam.aether.item.tools.skyroot.SkyrootHoeItem;
import com.aetherteam.aether.item.tools.skyroot.SkyrootPickaxeItem;
import com.aetherteam.aether.item.tools.skyroot.SkyrootShovelItem;
import com.aetherteam.aether.item.tools.valkyrie.ValkyrieAxeItem;
import com.aetherteam.aether.item.tools.valkyrie.ValkyrieHoeItem;
import com.aetherteam.aether.item.tools.valkyrie.ValkyriePickaxeItem;
import com.aetherteam.aether.item.tools.valkyrie.ValkyrieShovelItem;
import com.aetherteam.aether.item.tools.zanite.ZaniteAxeItem;
import com.aetherteam.aether.item.tools.zanite.ZaniteHoeItem;
import com.aetherteam.aether.item.tools.zanite.ZanitePickaxeItem;
import com.aetherteam.aether.item.tools.zanite.ZaniteShovelItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Aether.MODID);

	public static final Rarity AETHER_LOOT = Rarity.create("aether.loot", ChatFormatting.GREEN);

	public static final Component BRONZE_DUNGEON_TOOLTIP = Component.translatable("aether.dungeon.bronze_dungeon").withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.parseColor("#D9AB7E")));
	public static final Component SILVER_DUNGEON_TOOLTIP = Component.translatable("aether.dungeon.silver_dungeon").withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.parseColor("#E0E0E0")));
	public static final Component GOLD_DUNGEON_TOOLTIP = Component.translatable("aether.dungeon.gold_dungeon").withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.parseColor("#FDF55F")));

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

	public static final RegistryObject<PickaxeItem> VALKYRIE_PICKAXE = ITEMS.register("valkyrie_pickaxe", ValkyriePickaxeItem::new);
	public static final RegistryObject<AxeItem> VALKYRIE_AXE = ITEMS.register("valkyrie_axe", ValkyrieAxeItem::new);
	public static final RegistryObject<ShovelItem> VALKYRIE_SHOVEL = ITEMS.register("valkyrie_shovel", ValkyrieShovelItem::new);
	public static final RegistryObject<HoeItem> VALKYRIE_HOE = ITEMS.register("valkyrie_hoe", ValkyrieHoeItem::new);

	// Weapons
	public static final RegistryObject<SwordItem> SKYROOT_SWORD = ITEMS.register("skyroot_sword", SkyrootSwordItem::new);
	public static final RegistryObject<SwordItem> HOLYSTONE_SWORD = ITEMS.register("holystone_sword", HolystoneSwordItem::new);
	public static final RegistryObject<SwordItem> ZANITE_SWORD = ITEMS.register("zanite_sword", ZaniteSwordItem::new);
	public static final RegistryObject<SwordItem> GRAVITITE_SWORD = ITEMS.register("gravitite_sword", GravititeSwordItem::new);

	public static final RegistryObject<SwordItem> VALKYRIE_LANCE = ITEMS.register("valkyrie_lance", ValkyrieLanceItem::new);

	public static final RegistryObject<SwordItem> FLAMING_SWORD = ITEMS.register("flaming_sword", FlamingSwordItem::new);
	public static final RegistryObject<SwordItem> LIGHTNING_SWORD = ITEMS.register("lightning_sword", LightningSwordItem::new);
	public static final RegistryObject<SwordItem> HOLY_SWORD = ITEMS.register("holy_sword", HolySwordItem::new);
	public static final RegistryObject<SwordItem> VAMPIRE_BLADE = ITEMS.register("vampire_blade", VampireBladeItem::new);
	public static final RegistryObject<SwordItem> PIG_SLAYER = ITEMS.register("pig_slayer", PigSlayerItem::new);
	public static final RegistryObject<SwordItem> CANDY_CANE_SWORD = ITEMS.register("candy_cane_sword", CandyCaneSwordItem::new);

	public static final RegistryObject<SwordItem> HAMMER_OF_KINGBDOGZ = ITEMS.register("hammer_of_kingbdogz", HammerOfKingbdogzItem::new);

	public static final RegistryObject<Item> LIGHTNING_KNIFE = ITEMS.register("lightning_knife", LightningKnifeItem::new);

	public static final RegistryObject<Item> GOLDEN_DART = ITEMS.register("golden_dart", () -> new DartItem(AetherEntityTypes.GOLDEN_DART, new Item.Properties()));
	public static final RegistryObject<Item> POISON_DART = ITEMS.register("poison_dart", () -> new DartItem(AetherEntityTypes.POISON_DART, new Item.Properties()));
	public static final RegistryObject<Item> ENCHANTED_DART = ITEMS.register("enchanted_dart", () -> new DartItem(AetherEntityTypes.ENCHANTED_DART, new Item.Properties().rarity(Rarity.RARE)));

	public static final RegistryObject<Item> GOLDEN_DART_SHOOTER = ITEMS.register("golden_dart_shooter", () -> new DartShooterItem(GOLDEN_DART, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> POISON_DART_SHOOTER = ITEMS.register("poison_dart_shooter", () -> new DartShooterItem(POISON_DART, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ENCHANTED_DART_SHOOTER  = ITEMS.register("enchanted_dart_shooter", () -> new DartShooterItem(ENCHANTED_DART, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

	public static final RegistryObject<BowItem> PHOENIX_BOW = ITEMS.register("phoenix_bow", PhoenixBowItem::new);

	// Armor
	public static final RegistryObject<Item> ZANITE_HELMET = ITEMS.register("zanite_helmet", () -> new AetherArmorItem(AetherArmorMaterials.ZANITE, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> ZANITE_CHESTPLATE = ITEMS.register("zanite_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.ZANITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> ZANITE_LEGGINGS = ITEMS.register("zanite_leggings", () -> new AetherArmorItem(AetherArmorMaterials.ZANITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> ZANITE_BOOTS = ITEMS.register("zanite_boots", () -> new AetherArmorItem(AetherArmorMaterials.ZANITE, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final RegistryObject<Item> GRAVITITE_HELMET = ITEMS.register("gravitite_helmet", () -> new AetherArmorItem(AetherArmorMaterials.GRAVITITE, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> GRAVITITE_CHESTPLATE = ITEMS.register("gravitite_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.GRAVITITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> GRAVITITE_LEGGINGS = ITEMS.register("gravitite_leggings", () -> new AetherArmorItem(AetherArmorMaterials.GRAVITITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> GRAVITITE_BOOTS = ITEMS.register("gravitite_boots", () -> new AetherArmorItem(AetherArmorMaterials.GRAVITITE, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final RegistryObject<Item> VALKYRIE_HELMET = ITEMS.register("valkyrie_helmet", () -> new AetherArmorItem(AetherArmorMaterials.VALKYRIE, ArmorItem.Type.HELMET, new Item.Properties().rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> VALKYRIE_CHESTPLATE = ITEMS.register("valkyrie_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.VALKYRIE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> VALKYRIE_LEGGINGS = ITEMS.register("valkyrie_leggings", () -> new AetherArmorItem(AetherArmorMaterials.VALKYRIE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> VALKYRIE_BOOTS = ITEMS.register("valkyrie_boots", () -> new AetherArmorItem(AetherArmorMaterials.VALKYRIE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(AETHER_LOOT)));

	public static final RegistryObject<Item> NEPTUNE_HELMET = ITEMS.register("neptune_helmet", () -> new AetherArmorItem(AetherArmorMaterials.NEPTUNE, ArmorItem.Type.HELMET, new Item.Properties().rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> NEPTUNE_CHESTPLATE = ITEMS.register("neptune_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.NEPTUNE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> NEPTUNE_LEGGINGS = ITEMS.register("neptune_leggings", () -> new AetherArmorItem(AetherArmorMaterials.NEPTUNE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> NEPTUNE_BOOTS = ITEMS.register("neptune_boots", () -> new AetherArmorItem(AetherArmorMaterials.NEPTUNE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(AETHER_LOOT)));

	public static final RegistryObject<Item> PHOENIX_HELMET = ITEMS.register("phoenix_helmet", () -> new AetherArmorItem(AetherArmorMaterials.PHOENIX, ArmorItem.Type.HELMET, new Item.Properties().rarity(AETHER_LOOT).fireResistant()));
	public static final RegistryObject<Item> PHOENIX_CHESTPLATE = ITEMS.register("phoenix_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.PHOENIX, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(AETHER_LOOT).fireResistant()));
	public static final RegistryObject<Item> PHOENIX_LEGGINGS = ITEMS.register("phoenix_leggings", () -> new AetherArmorItem(AetherArmorMaterials.PHOENIX, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(AETHER_LOOT).fireResistant()));
	public static final RegistryObject<Item> PHOENIX_BOOTS = ITEMS.register("phoenix_boots", () -> new AetherArmorItem(AetherArmorMaterials.PHOENIX, ArmorItem.Type.BOOTS, new Item.Properties().rarity(AETHER_LOOT).fireResistant()));

	public static final RegistryObject<Item> OBSIDIAN_HELMET = ITEMS.register("obsidian_helmet", () -> new AetherArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorItem.Type.HELMET, new Item.Properties().rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> OBSIDIAN_CHESTPLATE = ITEMS.register("obsidian_chestplate", () -> new AetherArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> OBSIDIAN_LEGGINGS = ITEMS.register("obsidian_leggings", () -> new AetherArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> OBSIDIAN_BOOTS = ITEMS.register("obsidian_boots", () -> new AetherArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorItem.Type.BOOTS, new Item.Properties().rarity(AETHER_LOOT)));

	public static final RegistryObject<Item> SENTRY_BOOTS = ITEMS.register("sentry_boots", () -> new AetherArmorItem(AetherArmorMaterials.SENTRY, ArmorItem.Type.BOOTS, new Item.Properties().rarity(AETHER_LOOT)));

	// Food
	public static final RegistryObject<Item> BLUE_BERRY = ITEMS.register("blue_berry", () -> new Item(new Item.Properties().food(AetherFoods.BLUE_BERRY)));
	public static final RegistryObject<Item> ENCHANTED_BERRY = ITEMS.register("enchanted_berry", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(AetherFoods.ENCHANTED_BERRY)));
	public static final RegistryObject<Item> WHITE_APPLE = ITEMS.register("white_apple", WhiteAppleItem::new);
	public static final RegistryObject<Item> BLUE_GUMMY_SWET = ITEMS.register("blue_gummy_swet", GummySwetItem::new);
	public static final RegistryObject<Item> GOLDEN_GUMMY_SWET = ITEMS.register("golden_gummy_swet", GummySwetItem::new);
	public static final RegistryObject<Item> HEALING_STONE = ITEMS.register("healing_stone", HealingStoneItem::new);
	public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane", () -> new Item(new Item.Properties().food(AetherFoods.CANDY_CANE)));
	public static final RegistryObject<Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man", () -> new Item(new Item.Properties().food(AetherFoods.GINGERBREAD_MAN)));

	// Accessories
	public static final RegistryObject<Item> IRON_RING = ITEMS.register("iron_ring", () -> new RingItem(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_IRON_RING, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> GOLDEN_RING = ITEMS.register("golden_ring", () -> new RingItem(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GOLD_RING, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ZANITE_RING = ITEMS.register("zanite_ring", () -> new ZaniteRingItem(new Item.Properties().durability(49)));
	public static final RegistryObject<Item> ICE_RING = ITEMS.register("ice_ring", () -> new IceRingItem(new Item.Properties().durability(125)));

	public static final RegistryObject<Item> IRON_PENDANT = ITEMS.register("iron_pendant", () -> new PendantItem("iron_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_IRON_PENDANT, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> GOLDEN_PENDANT = ITEMS.register("golden_pendant", () -> new PendantItem("golden_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GOLD_PENDANT, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ZANITE_PENDANT = ITEMS.register("zanite_pendant", () -> new ZanitePendantItem(new Item.Properties().durability(98)));
	public static final RegistryObject<Item> ICE_PENDANT = ITEMS.register("ice_pendant", () -> new IcePendantItem(new Item.Properties().durability(250)));

	public static final RegistryObject<Item> LEATHER_GLOVES = ITEMS.register("leather_gloves", () -> new LeatherGlovesItem(0.25, new Item.Properties().durability(59)));
	public static final RegistryObject<Item> CHAINMAIL_GLOVES = ITEMS.register("chainmail_gloves", () -> new GlovesItem(ArmorMaterials.CHAIN, 0.35, "chainmail_gloves", () -> SoundEvents.ARMOR_EQUIP_CHAIN, new Item.Properties().durability(131)));
	public static final RegistryObject<Item> IRON_GLOVES = ITEMS.register("iron_gloves", () -> new GlovesItem(ArmorMaterials.IRON, 0.5, "iron_gloves", () -> SoundEvents.ARMOR_EQUIP_IRON, new Item.Properties().durability(250)));
	public static final RegistryObject<Item> GOLDEN_GLOVES = ITEMS.register("golden_gloves", () -> new GoldGlovesItem(0.25, new Item.Properties().durability(32)));
	public static final RegistryObject<Item> DIAMOND_GLOVES = ITEMS.register("diamond_gloves",  () -> new GlovesItem(ArmorMaterials.DIAMOND, 0.75, "diamond_gloves", () -> SoundEvents.ARMOR_EQUIP_DIAMOND, new Item.Properties().durability(1561)));
	public static final RegistryObject<Item> NETHERITE_GLOVES = ITEMS.register("netherite_gloves", () -> new GlovesItem(ArmorMaterials.NETHERITE, 1.0, "netherite_gloves", () -> SoundEvents.ARMOR_EQUIP_NETHERITE, new Item.Properties().durability(2031).fireResistant()));
	public static final RegistryObject<Item> ZANITE_GLOVES = ITEMS.register("zanite_gloves", () -> new ZaniteGlovesItem(0.5, new Item.Properties().durability(250)));
	public static final RegistryObject<Item> GRAVITITE_GLOVES = ITEMS.register("gravitite_gloves", () -> new GlovesItem(AetherArmorMaterials.GRAVITITE, 0.75, "gravitite_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, new Item.Properties().durability(1561)));
	public static final RegistryObject<Item> VALKYRIE_GLOVES = ITEMS.register("valkyrie_gloves", () -> new GlovesItem(AetherArmorMaterials.VALKYRIE, 1.0, "valkyrie_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).durability(1561)));
	public static final RegistryObject<Item> NEPTUNE_GLOVES = ITEMS.register("neptune_gloves", () -> new GlovesItem(AetherArmorMaterials.NEPTUNE, 0.5, "neptune_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).durability(250)));
	public static final RegistryObject<Item> PHOENIX_GLOVES = ITEMS.register("phoenix_gloves", () -> new GlovesItem(AetherArmorMaterials.PHOENIX, 1.0, "phoenix_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant().durability(1561)));
	public static final RegistryObject<Item> OBSIDIAN_GLOVES = ITEMS.register("obsidian_gloves", () -> new GlovesItem(AetherArmorMaterials.OBSIDIAN, 1.0, "obsidian_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).durability(2031)));

	public static final RegistryObject<Item> RED_CAPE = ITEMS.register("red_cape", () -> new CapeItem("red_cape", new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> BLUE_CAPE = ITEMS.register("blue_cape", () -> new CapeItem("blue_cape", new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> YELLOW_CAPE = ITEMS.register("yellow_cape", () -> new CapeItem("yellow_cape", new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> WHITE_CAPE = ITEMS.register("white_cape", () -> new CapeItem("white_cape", new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> AGILITY_CAPE = ITEMS.register("agility_cape", () -> new AgilityCapeItem("agility_cape", new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> SWET_CAPE = ITEMS.register("swet_cape", () -> new CapeItem("swet_cape", new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> INVISIBILITY_CLOAK = ITEMS.register("invisibility_cloak", () -> new InvisibilityCloakItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> VALKYRIE_CAPE = ITEMS.register("valkyrie_cape", () -> new ValkyrieCapeItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));

	public static final RegistryObject<Item> GOLDEN_FEATHER = ITEMS.register("golden_feather", () -> new GoldenFeatherItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> REGENERATION_STONE = ITEMS.register("regeneration_stone", () -> new RegenerationStoneItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> IRON_BUBBLE = ITEMS.register("iron_bubble", () -> new IronBubbleItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
	public static final RegistryObject<Item> SHIELD_OF_REPULSION = ITEMS.register("shield_of_repulsion", () -> new ShieldOfRepulsionItem(new Item.Properties().durability(512).rarity(AETHER_LOOT)));

	// Materials
	public static final RegistryObject<Item> SKYROOT_STICK = ITEMS.register("skyroot_stick", () -> new SkyrootStickItem(new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_AMBER = ITEMS.register("golden_amber", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SWET_BALL = ITEMS.register("swet_ball", () -> new SwetBallItem(new Item.Properties()));
	public static final RegistryObject<Item> AECHOR_PETAL = ITEMS.register("aechor_petal", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> AMBROSIUM_SHARD = ITEMS.register("ambrosium_shard", () -> new AmbrosiumShardItem(new Item.Properties()));
	public static final RegistryObject<Item> ZANITE_GEMSTONE = ITEMS.register("zanite_gemstone", () -> new Item(new Item.Properties()));

	// Misc
	public static final RegistryObject<Item> VICTORY_MEDAL = ITEMS.register("victory_medal", () -> new Item(new Item.Properties().stacksTo(10).rarity(AETHER_LOOT)));

	public static final RegistryObject<Item> BRONZE_DUNGEON_KEY = ITEMS.register("bronze_dungeon_key", () -> new DungeonKeyItem(new ResourceLocation(Aether.MODID, "bronze"), new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant()));
	public static final RegistryObject<Item> SILVER_DUNGEON_KEY = ITEMS.register("silver_dungeon_key", () -> new DungeonKeyItem(new ResourceLocation(Aether.MODID, "silver"), new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant()));
	public static final RegistryObject<Item> GOLD_DUNGEON_KEY = ITEMS.register("gold_dungeon_key", () -> new DungeonKeyItem(new ResourceLocation(Aether.MODID, "gold"), new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant()));

	public static final RegistryObject<Item> MUSIC_DISC_AETHER_TUNE = ITEMS.register("music_disc_aether_tune", () -> new RecordItem(1, AetherSoundEvents.ITEM_MUSIC_DISC_AETHER_TUNE, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2980));
	public static final RegistryObject<Item> MUSIC_DISC_ASCENDING_DAWN  = ITEMS.register("music_disc_ascending_dawn", () -> new RecordItem(2, AetherSoundEvents.ITEM_MUSIC_DISC_ASCENDING_DAWN, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 7000));
	public static final RegistryObject<Item> MUSIC_DISC_CHINCHILLA  = ITEMS.register("music_disc_chinchilla", () -> new RecordItem(3, AetherSoundEvents.ITEM_MUSIC_DISC_CHINCHILLA, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3260));
	public static final RegistryObject<Item> MUSIC_DISC_HIGH = ITEMS.register("music_disc_high", () -> new RecordItem(4, AetherSoundEvents.ITEM_MUSIC_DISC_HIGH, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3720));
	public static final RegistryObject<Item> MUSIC_DISC_KLEPTO = ITEMS.register("music_disc_klepto", () -> new RecordItem(5, AetherSoundEvents.ITEM_MUSIC_DISC_KLEPTO, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3820));

	public static final RegistryObject<Item> SKYROOT_BUCKET = ITEMS.register("skyroot_bucket", () -> new SkyrootBucketItem(() -> Fluids.EMPTY, new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> SKYROOT_WATER_BUCKET = ITEMS.register("skyroot_water_bucket", () -> new SkyrootBucketItem(() -> Fluids.WATER, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_POISON_BUCKET = ITEMS.register("skyroot_poison_bucket", () -> new SkyrootPoisonBucketItem(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_REMEDY_BUCKET = ITEMS.register("skyroot_remedy_bucket", () -> new SkyrootRemedyBucketItem(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SKYROOT_MILK_BUCKET = ITEMS.register("skyroot_milk_bucket", () -> new SkyrootMilkBucketItem(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_POWDER_SNOW_BUCKET = ITEMS.register("skyroot_powder_snow_bucket", () -> new SkyrootSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_COD_BUCKET = ITEMS.register("skyroot_cod_bucket", () -> new SkyrootMobBucketItem(() -> EntityType.COD, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_SALMON_BUCKET = ITEMS.register("skyroot_salmon_bucket", () -> new SkyrootMobBucketItem(() -> EntityType.SALMON, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_PUFFERFISH_BUCKET = ITEMS.register("skyroot_pufferfish_bucket", () -> new SkyrootMobBucketItem(() -> EntityType.PUFFERFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_TROPICAL_FISH_BUCKET = ITEMS.register("skyroot_tropical_fish_bucket", () -> new SkyrootMobBucketItem(() -> EntityType.TROPICAL_FISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_AXOLOTL_BUCKET = ITEMS.register("skyroot_axolotl_bucket", () -> new SkyrootMobBucketItem(() -> EntityType.AXOLOTL, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_AXOLOTL, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_TADPOLE_BUCKET = ITEMS.register("skyroot_tadpole_bucket", () -> new SkyrootMobBucketItem(() -> EntityType.TADPOLE, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_TADPOLE, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));

	public static final RegistryObject<Item> SKYROOT_BOAT = ITEMS.register("skyroot_boat", () -> new SkyrootBoatItem(false, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> SKYROOT_CHEST_BOAT = ITEMS.register("skyroot_chest_boat", () -> new SkyrootBoatItem(true, new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> COLD_PARACHUTE = ITEMS.register("cold_parachute", () -> new ParachuteItem(AetherEntityTypes.COLD_PARACHUTE, new Item.Properties().durability(1)));
	public static final RegistryObject<Item> GOLDEN_PARACHUTE = ITEMS.register("golden_parachute", () -> new ParachuteItem(AetherEntityTypes.GOLDEN_PARACHUTE, new Item.Properties().durability(20)));

	public static final RegistryObject<Item> BLUE_MOA_EGG = ITEMS.register("blue_moa_egg", () -> new MoaEggItem(AetherMoaTypes.BLUE, 0x7777FF, new Item.Properties()));
	public static final RegistryObject<Item> WHITE_MOA_EGG = ITEMS.register("white_moa_egg", () -> new MoaEggItem(AetherMoaTypes.WHITE, 0xFFFFFF, new Item.Properties()));
	public static final RegistryObject<Item> BLACK_MOA_EGG = ITEMS.register("black_moa_egg", () -> new MoaEggItem(AetherMoaTypes.BLACK, 0x222222, new Item.Properties()));

	public static final RegistryObject<Item> NATURE_STAFF = ITEMS.register("nature_staff", () -> new Item(new Item.Properties().durability(100)));
	public static final RegistryObject<Item> CLOUD_STAFF = ITEMS.register("cloud_staff", CloudStaffItem::new);

	public static final RegistryObject<Item> LIFE_SHARD = ITEMS.register("life_shard", () -> new LifeShardItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));

	public static final RegistryObject<Item> BOOK_OF_LORE = ITEMS.register("book_of_lore", () -> new LoreBookItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));

	public static final RegistryObject<Item> AETHER_PORTAL_FRAME = ITEMS.register("aether_portal_frame", () -> new AetherPortalItem(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<SpawnEggItem> AECHOR_PLANT_SPAWN_EGG = ITEMS.register("aechor_plant_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.AECHOR_PLANT, 0x076178, 0x4BC69E, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> AERBUNNY_SPAWN_EGG = ITEMS.register("aerbunny_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.AERBUNNY, 0xE2FCFF, 0xFFDFF9, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> AERWHALE_SPAWN_EGG = ITEMS.register("aerwhale_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.AERWHALE, 0xC0E7FD, 0x879EAA, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> COCKATRICE_SPAWN_EGG = ITEMS.register("cockatrice_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.COCKATRICE, 0x6CB15C, 0x6C579D, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> FIRE_MINION_SPAWN_EGG = ITEMS.register("fire_minion_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.FIRE_MINION, 0xFF6D01, 0xFEF500, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> FLYING_COW_SPAWN_EGG = ITEMS.register("flying_cow_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.FLYING_COW, 0xD8D8D8, 0xFFD939, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> MIMIC_SPAWN_EGG = ITEMS.register("mimic_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.MIMIC, 0xB18132,0x605A4E, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> MOA_SPAWN_EGG = ITEMS.register("moa_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.MOA, 0x87BFEF, 0x7A7A7A, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> PHYG_SPAWN_EGG = ITEMS.register("phyg_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.PHYG, 0xFFC1D0, 0xFFD939, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> SENTRY_SPAWN_EGG = ITEMS.register("sentry_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.SENTRY, 0x808080,0x3A8AEC, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> SHEEPUFF_SPAWN_EGG = ITEMS.register("sheepuff_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.SHEEPUFF, 0xE2FCFF, 0xCB9090, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> BLUE_SWET_SPAWN_EGG = ITEMS.register("blue_swet_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.BLUE_SWET, 0x4FB1DA, 0xCDDA4F, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> GOLDEN_SWET_SPAWN_EGG = ITEMS.register("golden_swet_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.GOLDEN_SWET, 0xCDDA4F, 0x4FB1DA, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> WHIRLWIND_SPAWN_EGG = ITEMS.register("whirlwind_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.WHIRLWIND, 0x9FC3F7, 0xFFFFFF, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> EVIL_WHIRLWIND_SPAWN_EGG = ITEMS.register("evil_whirlwind_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.EVIL_WHIRLWIND, 0x9FC3F7, 0x111111, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> VALKYRIE_SPAWN_EGG = ITEMS.register("valkyrie_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.VALKYRIE, 0xF9F5E3, 0xF2D200, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> VALKYRIE_QUEEN_SPAWN_EGG = ITEMS.register("valkyrie_queen_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.VALKYRIE_QUEEN, 0xF2D200, 0xF9F5E3, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> SLIDER_SPAWN_EGG = ITEMS.register("slider_spawn_egg", () -> new SliderSpawnEggItem(AetherEntityTypes.SLIDER, 0xA7A7A7, 0x5C9FF2, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> SUN_SPIRIT_SPAWN_EGG = ITEMS.register("sun_spirit_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.SUN_SPIRIT, 0xFEF500, 0xFF6D01, new Item.Properties()));
	public static final RegistryObject<SpawnEggItem> ZEPHYR_SPAWN_EGG = ITEMS.register("zephyr_spawn_egg", () -> new ForgeSpawnEggItem(AetherEntityTypes.ZEPHYR, 0xDFDFDF, 0x99CFE8, new Item.Properties()));

	/**
	 * Sets up the possible replacements for vanilla buckets to Skyroot buckets.
	 * @see com.aetherteam.aether.event.hooks.EntityHooks#pickupBucketable(Entity, Player, InteractionHand)
	 */
	public static void setupBucketReplacements() {
		SkyrootBucketItem.REPLACEMENTS.put(() -> Items.WATER_BUCKET, AetherItems.SKYROOT_WATER_BUCKET);
		SkyrootBucketItem.REPLACEMENTS.put(() -> Items.POWDER_SNOW_BUCKET, AetherItems.SKYROOT_POWDER_SNOW_BUCKET);
		SkyrootBucketItem.REPLACEMENTS.put(() -> Items.COD_BUCKET, AetherItems.SKYROOT_COD_BUCKET);
		SkyrootBucketItem.REPLACEMENTS.put(() -> Items.SALMON_BUCKET, AetherItems.SKYROOT_SALMON_BUCKET);
		SkyrootBucketItem.REPLACEMENTS.put(() -> Items.PUFFERFISH_BUCKET, AetherItems.SKYROOT_PUFFERFISH_BUCKET);
		SkyrootBucketItem.REPLACEMENTS.put(() -> Items.TROPICAL_FISH_BUCKET, AetherItems.SKYROOT_TROPICAL_FISH_BUCKET);
		SkyrootBucketItem.REPLACEMENTS.put(() -> Items.AXOLOTL_BUCKET, AetherItems.SKYROOT_AXOLOTL_BUCKET);
		SkyrootBucketItem.REPLACEMENTS.put(() -> Items.TADPOLE_BUCKET, AetherItems.SKYROOT_TADPOLE_BUCKET);
	}

	public static ItemStack createSwetBannerItemStack() {
		ItemStack bannerStack = new ItemStack(Items.BLACK_BANNER).setHoverName(Component.translatable("aether.block.aether.swet_banner").withStyle(ChatFormatting.GOLD));
		CompoundTag tag = new CompoundTag();
		tag.put("Patterns", AetherBlocks.SWET_BANNER_PATTERN.toListTag());
		BlockItem.setBlockEntityData(bannerStack, BlockEntityType.BANNER, tag);
		bannerStack.hideTooltipPart(ItemStack.TooltipPart.ADDITIONAL);
		return bannerStack;
	}
}