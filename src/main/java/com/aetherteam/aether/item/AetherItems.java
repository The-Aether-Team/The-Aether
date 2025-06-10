package com.aetherteam.aether.item;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.data.resources.registries.AetherJukeboxSongs;
import com.aetherteam.aether.data.resources.registries.AetherMoaTypes;
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
import com.aetherteam.aether.item.components.AetherDataComponents;
import com.aetherteam.aether.item.components.DungeonKind;
import com.aetherteam.aether.item.food.AetherFoods;
import com.aetherteam.aether.item.food.GummySwetItem;
import com.aetherteam.aether.item.food.HealingStoneItem;
import com.aetherteam.aether.item.food.WhiteAppleItem;
import com.aetherteam.aether.item.materials.AmbrosiumShardItem;
import com.aetherteam.aether.item.materials.SwetBallItem;
import com.aetherteam.aether.item.miscellaneous.*;
import com.aetherteam.aether.item.miscellaneous.bucket.SkyrootBucketItem;
import com.aetherteam.aether.item.miscellaneous.bucket.SkyrootMobBucketItem;
import com.aetherteam.aether.item.miscellaneous.bucket.SkyrootSolidBucketItem;
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
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.AccessoryRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Aether.MODID);

    public static final Rarity AETHER_LOOT = Rarity.valueOf("AETHER_LOOT");

    public static final Component BRONZE_DUNGEON_TOOLTIP = Component.translatable("aether.dungeon.bronze_dungeon").withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.parseColor("#D9AB7E").result().get()));
    public static final Component SILVER_DUNGEON_TOOLTIP = Component.translatable("aether.dungeon.silver_dungeon").withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.parseColor("#E0E0E0").result().get()));
    public static final Component GOLD_DUNGEON_TOOLTIP = Component.translatable("aether.dungeon.gold_dungeon").withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.parseColor("#FDF55F").result().get()));

    // Tools
    public static final DeferredItem<PickaxeItem> SKYROOT_PICKAXE = ITEMS.registerItem("skyroot_pickaxe", SkyrootPickaxeItem::new);
    public static final DeferredItem<AxeItem> SKYROOT_AXE = ITEMS.registerItem("skyroot_axe", SkyrootAxeItem::new);
    public static final DeferredItem<ShovelItem> SKYROOT_SHOVEL = ITEMS.registerItem("skyroot_shovel", SkyrootShovelItem::new);
    public static final DeferredItem<HoeItem> SKYROOT_HOE = ITEMS.registerItem("skyroot_hoe", SkyrootHoeItem::new);

    public static final DeferredItem<PickaxeItem> HOLYSTONE_PICKAXE = ITEMS.registerItem("holystone_pickaxe", HolystonePickaxeItem::new);
    public static final DeferredItem<AxeItem> HOLYSTONE_AXE = ITEMS.registerItem("holystone_axe", HolystoneAxeItem::new);
    public static final DeferredItem<ShovelItem> HOLYSTONE_SHOVEL = ITEMS.registerItem("holystone_shovel", HolystoneShovelItem::new);
    public static final DeferredItem<HoeItem> HOLYSTONE_HOE = ITEMS.registerItem("holystone_hoe", HolystoneHoeItem::new);

    public static final DeferredItem<PickaxeItem> ZANITE_PICKAXE = ITEMS.registerItem("zanite_pickaxe", ZanitePickaxeItem::new);
    public static final DeferredItem<AxeItem> ZANITE_AXE = ITEMS.registerItem("zanite_axe", ZaniteAxeItem::new);
    public static final DeferredItem<ShovelItem> ZANITE_SHOVEL = ITEMS.registerItem("zanite_shovel", ZaniteShovelItem::new);
    public static final DeferredItem<HoeItem> ZANITE_HOE = ITEMS.registerItem("zanite_hoe", ZaniteHoeItem::new);

    public static final DeferredItem<PickaxeItem> GRAVITITE_PICKAXE = ITEMS.registerItem("gravitite_pickaxe", GravititePickaxeItem::new);
    public static final DeferredItem<AxeItem> GRAVITITE_AXE = ITEMS.registerItem("gravitite_axe", GravititeAxeItem::new);
    public static final DeferredItem<ShovelItem> GRAVITITE_SHOVEL = ITEMS.registerItem("gravitite_shovel", GravititeShovelItem::new);
    public static final DeferredItem<HoeItem> GRAVITITE_HOE = ITEMS.registerItem("gravitite_hoe", GravititeHoeItem::new);

    public static final DeferredItem<PickaxeItem> VALKYRIE_PICKAXE = ITEMS.registerItem("valkyrie_pickaxe", ValkyriePickaxeItem::new);
    public static final DeferredItem<AxeItem> VALKYRIE_AXE = ITEMS.registerItem("valkyrie_axe", ValkyrieAxeItem::new);
    public static final DeferredItem<ShovelItem> VALKYRIE_SHOVEL = ITEMS.registerItem("valkyrie_shovel", ValkyrieShovelItem::new);
    public static final DeferredItem<HoeItem> VALKYRIE_HOE = ITEMS.registerItem("valkyrie_hoe", ValkyrieHoeItem::new);

    // Weapons
    public static final DeferredItem<SwordItem> SKYROOT_SWORD = ITEMS.registerItem("skyroot_sword", SkyrootSwordItem::new);
    public static final DeferredItem<SwordItem> HOLYSTONE_SWORD = ITEMS.registerItem("holystone_sword", HolystoneSwordItem::new);
    public static final DeferredItem<SwordItem> ZANITE_SWORD = ITEMS.registerItem("zanite_sword", ZaniteSwordItem::new);
    public static final DeferredItem<SwordItem> GRAVITITE_SWORD = ITEMS.registerItem("gravitite_sword", GravititeSwordItem::new);

    public static final DeferredItem<SwordItem> VALKYRIE_LANCE = ITEMS.registerItem("valkyrie_lance", ValkyrieLanceItem::new);

    public static final DeferredItem<SwordItem> FLAMING_SWORD = ITEMS.registerItem("flaming_sword", FlamingSwordItem::new);
    public static final DeferredItem<SwordItem> LIGHTNING_SWORD = ITEMS.registerItem("lightning_sword", LightningSwordItem::new);
    public static final DeferredItem<SwordItem> HOLY_SWORD = ITEMS.registerItem("holy_sword", HolySwordItem::new);
    public static final DeferredItem<SwordItem> VAMPIRE_BLADE = ITEMS.registerItem("vampire_blade", VampireBladeItem::new);
    public static final DeferredItem<SwordItem> PIG_SLAYER = ITEMS.registerItem("pig_slayer", PigSlayerItem::new);
    public static final DeferredItem<SwordItem> CANDY_CANE_SWORD = ITEMS.registerItem("candy_cane_sword", CandyCaneSwordItem::new);

    public static final DeferredItem<SwordItem> HAMMER_OF_KINGBDOGZ = ITEMS.registerItem("hammer_of_kingbdogz", HammerOfKingbdogzItem::new);

    public static final DeferredItem<Item> LIGHTNING_KNIFE = ITEMS.register("lightning_knife", LightningKnifeItem::new);

    public static final DeferredItem<Item> GOLDEN_DART = ITEMS.register("golden_dart", () -> new GoldenDartItem(new Item.Properties()));
    public static final DeferredItem<Item> POISON_DART = ITEMS.register("poison_dart", () -> new PoisonDartItem(new Item.Properties()));
    public static final DeferredItem<Item> ENCHANTED_DART = ITEMS.register("enchanted_dart", () -> new EnchantedDartItem(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> GOLDEN_DART_SHOOTER = ITEMS.register("golden_dart_shooter", () -> new DartShooterItem(GOLDEN_DART, new Item.Properties().stacksTo(1).enchantable(1)));
    public static final DeferredItem<Item> POISON_DART_SHOOTER = ITEMS.register("poison_dart_shooter", () -> new DartShooterItem(POISON_DART, new Item.Properties().stacksTo(1).enchantable(1)));
    public static final DeferredItem<Item> ENCHANTED_DART_SHOOTER = ITEMS.register("enchanted_dart_shooter", () -> new DartShooterItem(ENCHANTED_DART, new Item.Properties().stacksTo(1).enchantable(1).rarity(Rarity.RARE)));

    public static final DeferredItem<BowItem> PHOENIX_BOW = ITEMS.register("phoenix_bow", PhoenixBowItem::new);

    // Armor
    public static final DeferredItem<Item> ZANITE_HELMET = ITEMS.register("zanite_helmet", () -> new ArmorItem(AetherArmorMaterials.ZANITE, ArmorType.HELMET, new Item.Properties()));
    public static final DeferredItem<Item> ZANITE_CHESTPLATE = ITEMS.register("zanite_chestplate", () -> new ArmorItem(AetherArmorMaterials.ZANITE, ArmorType.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<Item> ZANITE_LEGGINGS = ITEMS.register("zanite_leggings", () -> new ArmorItem(AetherArmorMaterials.ZANITE, ArmorType.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<Item> ZANITE_BOOTS = ITEMS.register("zanite_boots", () -> new ArmorItem(AetherArmorMaterials.ZANITE, ArmorType.BOOTS, new Item.Properties()));

    public static final DeferredItem<Item> GRAVITITE_HELMET = ITEMS.register("gravitite_helmet", () -> new ArmorItem(AetherArmorMaterials.GRAVITITE, ArmorType.HELMET, new Item.Properties()));
    public static final DeferredItem<Item> GRAVITITE_CHESTPLATE = ITEMS.register("gravitite_chestplate", () -> new ArmorItem(AetherArmorMaterials.GRAVITITE, ArmorType.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<Item> GRAVITITE_LEGGINGS = ITEMS.register("gravitite_leggings", () -> new ArmorItem(AetherArmorMaterials.GRAVITITE, ArmorType.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<Item> GRAVITITE_BOOTS = ITEMS.register("gravitite_boots", () -> new ArmorItem(AetherArmorMaterials.GRAVITITE, ArmorType.BOOTS, new Item.Properties()));

    public static final DeferredItem<Item> VALKYRIE_HELMET = ITEMS.register("valkyrie_helmet", () -> new ArmorItem(AetherArmorMaterials.VALKYRIE, ArmorType.HELMET, new Item.Properties().rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> VALKYRIE_CHESTPLATE = ITEMS.register("valkyrie_chestplate", () -> new ArmorItem(AetherArmorMaterials.VALKYRIE, ArmorType.CHESTPLATE, new Item.Properties().rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> VALKYRIE_LEGGINGS = ITEMS.register("valkyrie_leggings", () -> new ArmorItem(AetherArmorMaterials.VALKYRIE, ArmorType.LEGGINGS, new Item.Properties().rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> VALKYRIE_BOOTS = ITEMS.register("valkyrie_boots", () -> new ArmorItem(AetherArmorMaterials.VALKYRIE, ArmorType.BOOTS, new Item.Properties().rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> NEPTUNE_HELMET = ITEMS.register("neptune_helmet", () -> new ArmorItem(AetherArmorMaterials.NEPTUNE, ArmorType.HELMET, new Item.Properties().rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> NEPTUNE_CHESTPLATE = ITEMS.register("neptune_chestplate", () -> new ArmorItem(AetherArmorMaterials.NEPTUNE, ArmorType.CHESTPLATE, new Item.Properties().rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> NEPTUNE_LEGGINGS = ITEMS.register("neptune_leggings", () -> new ArmorItem(AetherArmorMaterials.NEPTUNE, ArmorType.LEGGINGS, new Item.Properties().rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> NEPTUNE_BOOTS = ITEMS.register("neptune_boots", () -> new ArmorItem(AetherArmorMaterials.NEPTUNE, ArmorType.BOOTS, new Item.Properties().rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> PHOENIX_HELMET = ITEMS.register("phoenix_helmet", () -> new ArmorItem(AetherArmorMaterials.PHOENIX, ArmorType.HELMET, new Item.Properties().rarity(AETHER_LOOT).fireResistant()));
    public static final DeferredItem<Item> PHOENIX_CHESTPLATE = ITEMS.register("phoenix_chestplate", () -> new ArmorItem(AetherArmorMaterials.PHOENIX, ArmorType.CHESTPLATE, new Item.Properties().rarity(AETHER_LOOT).fireResistant()));
    public static final DeferredItem<Item> PHOENIX_LEGGINGS = ITEMS.register("phoenix_leggings", () -> new ArmorItem(AetherArmorMaterials.PHOENIX, ArmorType.LEGGINGS, new Item.Properties().rarity(AETHER_LOOT).fireResistant()));
    public static final DeferredItem<Item> PHOENIX_BOOTS = ITEMS.register("phoenix_boots", () -> new ArmorItem(AetherArmorMaterials.PHOENIX, ArmorType.BOOTS, new Item.Properties().rarity(AETHER_LOOT).fireResistant()));

    public static final DeferredItem<Item> OBSIDIAN_HELMET = ITEMS.register("obsidian_helmet", () -> new ArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorType.HELMET, new Item.Properties().rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> OBSIDIAN_CHESTPLATE = ITEMS.register("obsidian_chestplate", () -> new ArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorType.CHESTPLATE, new Item.Properties().rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> OBSIDIAN_LEGGINGS = ITEMS.register("obsidian_leggings", () -> new ArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorType.LEGGINGS, new Item.Properties().rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> OBSIDIAN_BOOTS = ITEMS.register("obsidian_boots", () -> new ArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorType.BOOTS, new Item.Properties().rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> SENTRY_BOOTS = ITEMS.register("sentry_boots", () -> new ArmorItem(AetherArmorMaterials.SENTRY, ArmorType.BOOTS, new Item.Properties().rarity(AETHER_LOOT)));

    // Food
    public static final DeferredItem<Item> BLUE_BERRY = ITEMS.register("blue_berry", () -> new Item(new Item.Properties().food(AetherFoods.BLUE_BERRY, AetherConsumables.FAST_FOOD)));
    public static final DeferredItem<Item> ENCHANTED_BERRY = ITEMS.register("enchanted_berry", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(AetherFoods.ENCHANTED_BERRY, AetherConsumables.FAST_FOOD)));
    public static final DeferredItem<Item> WHITE_APPLE = ITEMS.register("white_apple", WhiteAppleItem::new);
    public static final DeferredItem<Item> BLUE_GUMMY_SWET = ITEMS.register("blue_gummy_swet", GummySwetItem::new);
    public static final DeferredItem<Item> GOLDEN_GUMMY_SWET = ITEMS.register("golden_gummy_swet", GummySwetItem::new);
    public static final DeferredItem<Item> HEALING_STONE = ITEMS.register("healing_stone", HealingStoneItem::new);
    public static final DeferredItem<Item> CANDY_CANE = ITEMS.register("candy_cane", () -> new Item(new Item.Properties().food(AetherFoods.CANDY_CANE, AetherConsumables.FAST_FOOD)));
    public static final DeferredItem<Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man", () -> new Item(new Item.Properties().food(AetherFoods.GINGERBREAD_MAN, AetherConsumables.FAST_FOOD)));

    // Accessories
    public static final DeferredItem<Item> IRON_RING = ITEMS.register("iron_ring", () -> new RingItem(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_IRON_RING, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GOLDEN_RING = ITEMS.register("golden_ring", () -> new RingItem(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GOLD_RING, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ZANITE_RING = ITEMS.register("zanite_ring", () -> new ZaniteRingItem(new Item.Properties().durability(49)));
    public static final DeferredItem<Item> ICE_RING = ITEMS.register("ice_ring", () -> new IceRingItem(new Item.Properties().durability(125)));

    public static final DeferredItem<Item> IRON_PENDANT = ITEMS.register("iron_pendant", () -> new PendantItem("iron_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_IRON_PENDANT, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GOLDEN_PENDANT = ITEMS.register("golden_pendant", () -> new PendantItem("golden_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GOLD_PENDANT, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ZANITE_PENDANT = ITEMS.register("zanite_pendant", () -> new ZanitePendantItem(new Item.Properties().durability(98)));
    public static final DeferredItem<Item> ICE_PENDANT = ITEMS.register("ice_pendant", () -> new IcePendantItem(new Item.Properties().durability(250)));

    public static final DeferredItem<Item> LEATHER_GLOVES = ITEMS.register("leather_gloves", () -> new LeatherGlovesItem(0.25, new Item.Properties().durability(59)));
    public static final DeferredItem<Item> CHAINMAIL_GLOVES = ITEMS.register("chainmail_gloves", () -> new GlovesItem(ArmorMaterials.CHAINMAIL, 0.35, "chainmail_gloves", SoundEvents.ARMOR_EQUIP_CHAIN, new Item.Properties().durability(131)));
    public static final DeferredItem<Item> IRON_GLOVES = ITEMS.register("iron_gloves", () -> new GlovesItem(ArmorMaterials.IRON, 0.5, "iron_gloves", SoundEvents.ARMOR_EQUIP_IRON, new Item.Properties().durability(250)));
    public static final DeferredItem<Item> GOLDEN_GLOVES = ITEMS.register("golden_gloves", () -> new GoldGlovesItem(0.25, new Item.Properties().durability(32)));
    public static final DeferredItem<Item> DIAMOND_GLOVES = ITEMS.register("diamond_gloves", () -> new GlovesItem(ArmorMaterials.DIAMOND, 0.75, "diamond_gloves", SoundEvents.ARMOR_EQUIP_DIAMOND, new Item.Properties().durability(1561)));
    public static final DeferredItem<Item> NETHERITE_GLOVES = ITEMS.register("netherite_gloves", () -> new GlovesItem(ArmorMaterials.NETHERITE, 1.0, "netherite_gloves", SoundEvents.ARMOR_EQUIP_NETHERITE, new Item.Properties().durability(2031).fireResistant()));
    public static final DeferredItem<Item> ZANITE_GLOVES = ITEMS.register("zanite_gloves", () -> new ZaniteGlovesItem(0.5, new Item.Properties().durability(250)));
    public static final DeferredItem<Item> GRAVITITE_GLOVES = ITEMS.register("gravitite_gloves", () -> new GlovesItem(AetherArmorMaterials.GRAVITITE, 0.75, "gravitite_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, new Item.Properties().durability(1561)));
    public static final DeferredItem<Item> VALKYRIE_GLOVES = ITEMS.register("valkyrie_gloves", () -> new GlovesItem(AetherArmorMaterials.VALKYRIE, 1.0, "valkyrie_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).durability(1561)));
    public static final DeferredItem<Item> NEPTUNE_GLOVES = ITEMS.register("neptune_gloves", () -> new GlovesItem(AetherArmorMaterials.NEPTUNE, 0.5, "neptune_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).durability(250)));
    public static final DeferredItem<Item> PHOENIX_GLOVES = ITEMS.register("phoenix_gloves", () -> new GlovesItem(AetherArmorMaterials.PHOENIX, 1.0, "phoenix_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant().durability(1561)));
    public static final DeferredItem<Item> OBSIDIAN_GLOVES = ITEMS.register("obsidian_gloves", () -> new GlovesItem(AetherArmorMaterials.OBSIDIAN, 1.0, "obsidian_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).durability(2031)));

    public static final DeferredItem<Item> RED_CAPE = ITEMS.register("red_cape", () -> new CapeItem("red_cape", new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BLUE_CAPE = ITEMS.register("blue_cape", () -> new CapeItem("blue_cape", new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> YELLOW_CAPE = ITEMS.register("yellow_cape", () -> new CapeItem("yellow_cape", new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> WHITE_CAPE = ITEMS.register("white_cape", () -> new CapeItem("white_cape", new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> AGILITY_CAPE = ITEMS.register("agility_cape", () -> new AgilityCapeItem("agility_cape", new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> SWET_CAPE = ITEMS.register("swet_cape", () -> new CapeItem("swet_cape", new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INVISIBILITY_CLOAK = ITEMS.register("invisibility_cloak", () -> new InvisibilityCloakItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> VALKYRIE_CAPE = ITEMS.register("valkyrie_cape", () -> new ValkyrieCapeItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> GOLDEN_FEATHER = ITEMS.register("golden_feather", () -> new GoldenFeatherItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> REGENERATION_STONE = ITEMS.register("regeneration_stone", () -> new RegenerationStoneItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> IRON_BUBBLE = ITEMS.register("iron_bubble", () -> new IronBubbleItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> SHIELD_OF_REPULSION = ITEMS.register("shield_of_repulsion", () -> new ShieldOfRepulsionItem(new Item.Properties().durability(512).rarity(AETHER_LOOT)));

    // Materials
    public static final DeferredItem<Item> SKYROOT_STICK = ITEMS.register("skyroot_stick", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLDEN_AMBER = ITEMS.register("golden_amber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SWET_BALL = ITEMS.register("swet_ball", () -> new SwetBallItem(new Item.Properties()));
    public static final DeferredItem<Item> AECHOR_PETAL = ITEMS.register("aechor_petal", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AMBROSIUM_SHARD = ITEMS.register("ambrosium_shard", () -> new AmbrosiumShardItem(new Item.Properties()));
    public static final DeferredItem<Item> ZANITE_GEMSTONE = ITEMS.register("zanite_gemstone", () -> new Item(new Item.Properties()));

    // Misc
    public static final DeferredItem<Item> VICTORY_MEDAL = ITEMS.register("victory_medal", () -> new Item(new Item.Properties().stacksTo(10).rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> BRONZE_DUNGEON_KEY = ITEMS.register("bronze_dungeon_key", () -> new Item(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant().component(AetherDataComponents.DUNGEON_KIND, new DungeonKind(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "bronze")))));
    public static final DeferredItem<Item> SILVER_DUNGEON_KEY = ITEMS.register("silver_dungeon_key", () -> new Item(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant().component(AetherDataComponents.DUNGEON_KIND, new DungeonKind(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "silver")))));
    public static final DeferredItem<Item> GOLD_DUNGEON_KEY = ITEMS.register("gold_dungeon_key", () -> new Item(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT).fireResistant().component(AetherDataComponents.DUNGEON_KIND, new DungeonKind(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gold")))));

    public static final DeferredItem<Item> MUSIC_DISC_AETHER_TUNE = ITEMS.register("music_disc_aether_tune", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.AETHER_TUNE)));
    public static final DeferredItem<Item> MUSIC_DISC_ASCENDING_DAWN = ITEMS.register("music_disc_ascending_dawn", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.ASCENDING_DAWN)));
    public static final DeferredItem<Item> MUSIC_DISC_CHINCHILLA = ITEMS.register("music_disc_chinchilla", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.CHINCHILLA)));
    public static final DeferredItem<Item> MUSIC_DISC_HIGH = ITEMS.register("music_disc_high", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.HIGH)));
    public static final DeferredItem<Item> MUSIC_DISC_KLEPTO = ITEMS.register("music_disc_klepto", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.KLEPTO)));
    public static final DeferredItem<Item> MUSIC_DISC_SLIDERS_WRATH = ITEMS.register("music_disc_sliders_wrath", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.SLIDERS_WRATH)));

    public static final DeferredItem<Item> SKYROOT_BUCKET = ITEMS.register("skyroot_bucket", () -> new SkyrootBucketItem(Fluids.EMPTY, new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> SKYROOT_WATER_BUCKET = ITEMS.register("skyroot_water_bucket", () -> new SkyrootBucketItem(Fluids.WATER, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_POISON_BUCKET = ITEMS.register("skyroot_poison_bucket", () -> new Item(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).component(DataComponents.CONSUMABLE, AetherConsumables.POISON_BUCKET).usingConvertsTo(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_REMEDY_BUCKET = ITEMS.register("skyroot_remedy_bucket", () -> new Item(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).component(DataComponents.CONSUMABLE, AetherConsumables.REMEDY_BUCKET).usingConvertsTo(SKYROOT_BUCKET.get()).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> SKYROOT_MILK_BUCKET = ITEMS.register("skyroot_milk_bucket", () -> new Item(new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_POWDER_SNOW_BUCKET = ITEMS.register("skyroot_powder_snow_bucket", () -> new SkyrootSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_COD_BUCKET = ITEMS.register("skyroot_cod_bucket", () -> new SkyrootMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_SALMON_BUCKET = ITEMS.register("skyroot_salmon_bucket", () -> new SkyrootMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_PUFFERFISH_BUCKET = ITEMS.register("skyroot_pufferfish_bucket", () -> new SkyrootMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_TROPICAL_FISH_BUCKET = ITEMS.register("skyroot_tropical_fish_bucket", () -> new SkyrootMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_AXOLOTL_BUCKET = ITEMS.register("skyroot_axolotl_bucket", () -> new SkyrootMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_TADPOLE_BUCKET = ITEMS.register("skyroot_tadpole_bucket", () -> new SkyrootMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, new Item.Properties().craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> SKYROOT_BOAT = ITEMS.register("skyroot_boat", () -> new BoatItem(AetherEntityTypes.SKYROOT_BOAT.get(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_CHEST_BOAT = ITEMS.register("skyroot_chest_boat", () -> new BoatItem(AetherEntityTypes.SKYROOT_CHEST_BOAT.get(), new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> COLD_PARACHUTE = ITEMS.register("cold_parachute", () -> new ParachuteItem(AetherEntityTypes.COLD_PARACHUTE, new Item.Properties().durability(1)));
    public static final DeferredItem<Item> GOLDEN_PARACHUTE = ITEMS.register("golden_parachute", () -> new ParachuteItem(AetherEntityTypes.GOLDEN_PARACHUTE, new Item.Properties().durability(20)));

    public static final DeferredItem<Item> BLUE_MOA_EGG = ITEMS.register("blue_moa_egg", () -> new MoaEggItem(AetherMoaTypes.BLUE, 0x7777FF, new Item.Properties()));
    public static final DeferredItem<Item> WHITE_MOA_EGG = ITEMS.register("white_moa_egg", () -> new MoaEggItem(AetherMoaTypes.WHITE, 0xFFFFFF, new Item.Properties()));
    public static final DeferredItem<Item> BLACK_MOA_EGG = ITEMS.register("black_moa_egg", () -> new MoaEggItem(AetherMoaTypes.BLACK, 0x222222, new Item.Properties()));

    public static final DeferredItem<Item> NATURE_STAFF = ITEMS.register("nature_staff", () -> new Item(new Item.Properties().durability(100)));
    public static final DeferredItem<Item> CLOUD_STAFF = ITEMS.register("cloud_staff", CloudStaffItem::new);

    public static final DeferredItem<Item> LIFE_SHARD = ITEMS.register("life_shard", () -> new LifeShardItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> BOOK_OF_LORE = ITEMS.register("book_of_lore", () -> new LoreBookItem(new Item.Properties().stacksTo(1).rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> AETHER_PORTAL_FRAME = ITEMS.register("aether_portal_frame", () -> new AetherPortalItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<SpawnEggItem> AECHOR_PLANT_SPAWN_EGG = ITEMS.register("aechor_plant_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.AECHOR_PLANT.get(), 0x076178, 0x4BC69E, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> AERBUNNY_SPAWN_EGG = ITEMS.register("aerbunny_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.AERBUNNY.get(), 0xE2FCFF, 0xFFDFF9, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> AERWHALE_SPAWN_EGG = ITEMS.register("aerwhale_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.AERWHALE.get(), 0xC0E7FD, 0x879EAA, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> COCKATRICE_SPAWN_EGG = ITEMS.register("cockatrice_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.COCKATRICE.get(), 0x6CB15C, 0x6C579D, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> FIRE_MINION_SPAWN_EGG = ITEMS.register("fire_minion_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.FIRE_MINION.get(), 0xFF6D01, 0xFEF500, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> FLYING_COW_SPAWN_EGG = ITEMS.register("flying_cow_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.FLYING_COW.get(), 0xD8D8D8, 0xFFD939, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> MIMIC_SPAWN_EGG = ITEMS.register("mimic_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.MIMIC.get(), 0xB18132, 0x605A4E, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> MOA_SPAWN_EGG = ITEMS.register("moa_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.MOA.get(), 0x87BFEF, 0x7A7A7A, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> PHYG_SPAWN_EGG = ITEMS.register("phyg_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.PHYG.get(), 0xFFC1D0, 0xFFD939, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> SENTRY_SPAWN_EGG = ITEMS.register("sentry_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.SENTRY.get(), 0x808080, 0x3A8AEC, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> SHEEPUFF_SPAWN_EGG = ITEMS.register("sheepuff_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.SHEEPUFF.get(), 0xE2FCFF, 0xCB9090, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> BLUE_SWET_SPAWN_EGG = ITEMS.register("blue_swet_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.BLUE_SWET.get(), 0x4FB1DA, 0xCDDA4F, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> GOLDEN_SWET_SPAWN_EGG = ITEMS.register("golden_swet_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.GOLDEN_SWET.get(), 0xCDDA4F, 0x4FB1DA, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> WHIRLWIND_SPAWN_EGG = ITEMS.register("whirlwind_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.WHIRLWIND.get(), 0x9FC3F7, 0xFFFFFF, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> EVIL_WHIRLWIND_SPAWN_EGG = ITEMS.register("evil_whirlwind_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.EVIL_WHIRLWIND.get(), 0x9FC3F7, 0x111111, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> VALKYRIE_SPAWN_EGG = ITEMS.register("valkyrie_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.VALKYRIE.get(), 0xF9F5E3, 0xF2D200, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> VALKYRIE_QUEEN_SPAWN_EGG = ITEMS.register("valkyrie_queen_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.VALKYRIE_QUEEN.get(), 0xF2D200, 0xF9F5E3, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> SLIDER_SPAWN_EGG = ITEMS.register("slider_spawn_egg", () -> new SliderSpawnEggItem(AetherEntityTypes.SLIDER.get(), 0xA7A7A7, 0x5C9FF2, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> SUN_SPIRIT_SPAWN_EGG = ITEMS.register("sun_spirit_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.SUN_SPIRIT.get(), 0xFEF500, 0xFF6D01, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> ZEPHYR_SPAWN_EGG = ITEMS.register("zephyr_spawn_egg", () -> new SpawnEggItem(AetherEntityTypes.ZEPHYR.get(), 0xDFDFDF, 0x99CFE8, new Item.Properties()));

    public static void registerAccessories() {
        AccessoryRegistry.register(AetherItems.IRON_RING.get(), (Accessory) AetherItems.IRON_RING.get());
        AccessoryRegistry.register(AetherItems.GOLDEN_RING.get(), (Accessory) AetherItems.GOLDEN_RING.get());
        AccessoryRegistry.register(AetherItems.ZANITE_RING.get(), (Accessory) AetherItems.ZANITE_RING.get());
        AccessoryRegistry.register(AetherItems.ICE_RING.get(), (Accessory) AetherItems.ICE_RING.get());

        AccessoryRegistry.register(AetherItems.IRON_PENDANT.get(), (Accessory) AetherItems.IRON_PENDANT.get());
        AccessoryRegistry.register(AetherItems.GOLDEN_PENDANT.get(), (Accessory) AetherItems.GOLDEN_PENDANT.get());
        AccessoryRegistry.register(AetherItems.ZANITE_PENDANT.get(), (Accessory) AetherItems.ZANITE_PENDANT.get());
        AccessoryRegistry.register(AetherItems.ICE_PENDANT.get(), (Accessory) AetherItems.ICE_PENDANT.get());

        AccessoryRegistry.register(AetherItems.LEATHER_GLOVES.get(), (Accessory) AetherItems.LEATHER_GLOVES.get());
        AccessoryRegistry.register(AetherItems.CHAINMAIL_GLOVES.get(), (Accessory) AetherItems.CHAINMAIL_GLOVES.get());
        AccessoryRegistry.register(AetherItems.IRON_GLOVES.get(), (Accessory) AetherItems.IRON_GLOVES.get());
        AccessoryRegistry.register(AetherItems.GOLDEN_GLOVES.get(), (Accessory) AetherItems.GOLDEN_GLOVES.get());
        AccessoryRegistry.register(AetherItems.DIAMOND_GLOVES.get(), (Accessory) AetherItems.DIAMOND_GLOVES.get());
        AccessoryRegistry.register(AetherItems.NETHERITE_GLOVES.get(), (Accessory) AetherItems.NETHERITE_GLOVES.get());
        AccessoryRegistry.register(AetherItems.ZANITE_GLOVES.get(), (Accessory) AetherItems.ZANITE_GLOVES.get());
        AccessoryRegistry.register(AetherItems.GRAVITITE_GLOVES.get(), (Accessory) AetherItems.GRAVITITE_GLOVES.get());
        AccessoryRegistry.register(AetherItems.VALKYRIE_GLOVES.get(), (Accessory) AetherItems.VALKYRIE_GLOVES.get());
        AccessoryRegistry.register(AetherItems.NEPTUNE_GLOVES.get(), (Accessory) AetherItems.NEPTUNE_GLOVES.get());
        AccessoryRegistry.register(AetherItems.PHOENIX_GLOVES.get(), (Accessory) AetherItems.PHOENIX_GLOVES.get());
        AccessoryRegistry.register(AetherItems.OBSIDIAN_GLOVES.get(), (Accessory) AetherItems.OBSIDIAN_GLOVES.get());

        AccessoryRegistry.register(AetherItems.RED_CAPE.get(), (Accessory) AetherItems.RED_CAPE.get());
        AccessoryRegistry.register(AetherItems.BLUE_CAPE.get(), (Accessory) AetherItems.BLUE_CAPE.get());
        AccessoryRegistry.register(AetherItems.YELLOW_CAPE.get(), (Accessory) AetherItems.YELLOW_CAPE.get());
        AccessoryRegistry.register(AetherItems.WHITE_CAPE.get(), (Accessory) AetherItems.WHITE_CAPE.get());
        AccessoryRegistry.register(AetherItems.AGILITY_CAPE.get(), (Accessory) AetherItems.AGILITY_CAPE.get());
        AccessoryRegistry.register(AetherItems.SWET_CAPE.get(), (Accessory) AetherItems.SWET_CAPE.get());
        AccessoryRegistry.register(AetherItems.INVISIBILITY_CLOAK.get(), (Accessory) AetherItems.INVISIBILITY_CLOAK.get());
        AccessoryRegistry.register(AetherItems.VALKYRIE_CAPE.get(), (Accessory) AetherItems.VALKYRIE_CAPE.get());

        AccessoryRegistry.register(AetherItems.GOLDEN_FEATHER.get(), (Accessory) AetherItems.GOLDEN_FEATHER.get());
        AccessoryRegistry.register(AetherItems.REGENERATION_STONE.get(), (Accessory) AetherItems.REGENERATION_STONE.get());
        AccessoryRegistry.register(AetherItems.IRON_BUBBLE.get(), (Accessory) AetherItems.IRON_BUBBLE.get());
        AccessoryRegistry.register(AetherItems.SHIELD_OF_REPULSION.get(), (Accessory) AetherItems.SHIELD_OF_REPULSION.get());
    }

    /**
     * Sets up the possible replacements for vanilla buckets to Skyroot buckets.
     *
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

    public static ItemStack createSwetBannerItemStack(HolderGetter<BannerPattern> patternRegistry) {
        ItemStack bannerStack = new ItemStack(Items.BLACK_BANNER);
        BannerPatternLayers layers = new BannerPatternLayers.Builder()
            .add(patternRegistry.getOrThrow(BannerPatterns.STRIPE_DOWNLEFT), DyeColor.CYAN)
            .add(patternRegistry.getOrThrow(BannerPatterns.STRIPE_BOTTOM), DyeColor.CYAN)
            .add(patternRegistry.getOrThrow(BannerPatterns.STRIPE_LEFT), DyeColor.CYAN)
            .add(patternRegistry.getOrThrow(BannerPatterns.HALF_HORIZONTAL), DyeColor.BLACK)
            .add(patternRegistry.getOrThrow(BannerPatterns.STRAIGHT_CROSS), DyeColor.CYAN)
            .add(patternRegistry.getOrThrow(BannerPatterns.BORDER), DyeColor.WHITE)
            .add(patternRegistry.getOrThrow(BannerPatterns.GRADIENT_UP), DyeColor.LIGHT_BLUE)
            .add(patternRegistry.getOrThrow(BannerPatterns.GRADIENT), DyeColor.LIGHT_BLUE)
            .build();
        bannerStack.set(DataComponents.BANNER_PATTERNS, layers);
        bannerStack.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        bannerStack.set(DataComponents.ITEM_NAME, Component.translatable("aether.block.aether.swet_banner").withStyle(ChatFormatting.GOLD));
        return bannerStack;
    }
}
