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

    public static final DeferredItem<Item> LIGHTNING_KNIFE = ITEMS.registerItem("lightning_knife", LightningKnifeItem::new);

    public static final DeferredItem<Item> GOLDEN_DART = ITEMS.registerItem("golden_dart", GoldenDartItem::new);
    public static final DeferredItem<Item> POISON_DART = ITEMS.registerItem("poison_dart", PoisonDartItem::new);
    public static final DeferredItem<Item> ENCHANTED_DART = ITEMS.registerItem("enchanted_dart", EnchantedDartItem::new, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<Item> GOLDEN_DART_SHOOTER = ITEMS.registerItem("golden_dart_shooter", (properties) -> new DartShooterItem(GOLDEN_DART, properties.stacksTo(1).enchantable(1)));
    public static final DeferredItem<Item> POISON_DART_SHOOTER = ITEMS.registerItem("poison_dart_shooter", (properties) -> new DartShooterItem(POISON_DART, properties.stacksTo(1).enchantable(1)));
    public static final DeferredItem<Item> ENCHANTED_DART_SHOOTER = ITEMS.registerItem("enchanted_dart_shooter", (properties) -> new DartShooterItem(ENCHANTED_DART, properties.stacksTo(1).enchantable(1).rarity(Rarity.RARE)));

    public static final DeferredItem<BowItem> PHOENIX_BOW = ITEMS.registerItem("phoenix_bow", PhoenixBowItem::new);

    // Armor
    public static final DeferredItem<Item> ZANITE_HELMET = ITEMS.registerItem("zanite_helmet", (properties) -> new ArmorItem(AetherArmorMaterials.ZANITE, ArmorType.HELMET, properties));
    public static final DeferredItem<Item> ZANITE_CHESTPLATE = ITEMS.registerItem("zanite_chestplate", (properties) -> new ArmorItem(AetherArmorMaterials.ZANITE, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<Item> ZANITE_LEGGINGS = ITEMS.registerItem("zanite_leggings", (properties) -> new ArmorItem(AetherArmorMaterials.ZANITE, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<Item> ZANITE_BOOTS = ITEMS.registerItem("zanite_boots", (properties) -> new ArmorItem(AetherArmorMaterials.ZANITE, ArmorType.BOOTS, properties));

    public static final DeferredItem<Item> GRAVITITE_HELMET = ITEMS.registerItem("gravitite_helmet", (properties) -> new ArmorItem(AetherArmorMaterials.GRAVITITE, ArmorType.HELMET, properties));
    public static final DeferredItem<Item> GRAVITITE_CHESTPLATE = ITEMS.registerItem("gravitite_chestplate", (properties) -> new ArmorItem(AetherArmorMaterials.GRAVITITE, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<Item> GRAVITITE_LEGGINGS = ITEMS.registerItem("gravitite_leggings", (properties) -> new ArmorItem(AetherArmorMaterials.GRAVITITE, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<Item> GRAVITITE_BOOTS = ITEMS.registerItem("gravitite_boots", (properties) -> new ArmorItem(AetherArmorMaterials.GRAVITITE, ArmorType.BOOTS, properties));

    public static final DeferredItem<Item> VALKYRIE_HELMET = ITEMS.registerItem("valkyrie_helmet", (properties) -> new ArmorItem(AetherArmorMaterials.VALKYRIE, ArmorType.HELMET, properties.rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> VALKYRIE_CHESTPLATE = ITEMS.registerItem("valkyrie_chestplate", (properties) -> new ArmorItem(AetherArmorMaterials.VALKYRIE, ArmorType.CHESTPLATE, properties.rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> VALKYRIE_LEGGINGS = ITEMS.registerItem("valkyrie_leggings", (properties) -> new ArmorItem(AetherArmorMaterials.VALKYRIE, ArmorType.LEGGINGS, properties.rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> VALKYRIE_BOOTS = ITEMS.registerItem("valkyrie_boots", (properties) -> new ArmorItem(AetherArmorMaterials.VALKYRIE, ArmorType.BOOTS, properties.rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> NEPTUNE_HELMET = ITEMS.registerItem("neptune_helmet", (properties) -> new ArmorItem(AetherArmorMaterials.NEPTUNE, ArmorType.HELMET, properties.rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> NEPTUNE_CHESTPLATE = ITEMS.registerItem("neptune_chestplate", (properties) -> new ArmorItem(AetherArmorMaterials.NEPTUNE, ArmorType.CHESTPLATE, properties.rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> NEPTUNE_LEGGINGS = ITEMS.registerItem("neptune_leggings", (properties) -> new ArmorItem(AetherArmorMaterials.NEPTUNE, ArmorType.LEGGINGS, properties.rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> NEPTUNE_BOOTS = ITEMS.registerItem("neptune_boots", (properties) -> new ArmorItem(AetherArmorMaterials.NEPTUNE, ArmorType.BOOTS, properties.rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> PHOENIX_HELMET = ITEMS.registerItem("phoenix_helmet", (properties) -> new ArmorItem(AetherArmorMaterials.PHOENIX, ArmorType.HELMET, properties.rarity(AETHER_LOOT).fireResistant()));
    public static final DeferredItem<Item> PHOENIX_CHESTPLATE = ITEMS.registerItem("phoenix_chestplate", (properties) -> new ArmorItem(AetherArmorMaterials.PHOENIX, ArmorType.CHESTPLATE, properties.rarity(AETHER_LOOT).fireResistant()));
    public static final DeferredItem<Item> PHOENIX_LEGGINGS = ITEMS.registerItem("phoenix_leggings", (properties) -> new ArmorItem(AetherArmorMaterials.PHOENIX, ArmorType.LEGGINGS, properties.rarity(AETHER_LOOT).fireResistant()));
    public static final DeferredItem<Item> PHOENIX_BOOTS = ITEMS.registerItem("phoenix_boots", (properties) -> new ArmorItem(AetherArmorMaterials.PHOENIX, ArmorType.BOOTS, properties.rarity(AETHER_LOOT).fireResistant()));

    public static final DeferredItem<Item> OBSIDIAN_HELMET = ITEMS.registerItem("obsidian_helmet", (properties) -> new ArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorType.HELMET, properties.rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> OBSIDIAN_CHESTPLATE = ITEMS.registerItem("obsidian_chestplate", (properties) -> new ArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorType.CHESTPLATE, properties.rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> OBSIDIAN_LEGGINGS = ITEMS.registerItem("obsidian_leggings", (properties) -> new ArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorType.LEGGINGS, properties.rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> OBSIDIAN_BOOTS = ITEMS.registerItem("obsidian_boots", (properties) -> new ArmorItem(AetherArmorMaterials.OBSIDIAN, ArmorType.BOOTS, properties.rarity(AETHER_LOOT)));

    public static final DeferredItem<Item> SENTRY_BOOTS = ITEMS.registerItem("sentry_boots", (properties) -> new ArmorItem(AetherArmorMaterials.SENTRY, ArmorType.BOOTS, properties.rarity(AETHER_LOOT)));

    // Food
    public static final DeferredItem<Item> BLUE_BERRY = ITEMS.registerSimpleItem("blue_berry", new Item.Properties().food(AetherFoods.BLUE_BERRY, AetherConsumables.FAST_FOOD));
    public static final DeferredItem<Item> ENCHANTED_BERRY = ITEMS.registerSimpleItem("enchanted_berry", new Item.Properties().rarity(Rarity.RARE).food(AetherFoods.ENCHANTED_BERRY, AetherConsumables.FAST_FOOD));
    public static final DeferredItem<Item> WHITE_APPLE = ITEMS.registerItem("white_apple", WhiteAppleItem::new);
    public static final DeferredItem<Item> BLUE_GUMMY_SWET = ITEMS.registerItem("blue_gummy_swet", GummySwetItem::new);
    public static final DeferredItem<Item> GOLDEN_GUMMY_SWET = ITEMS.registerItem("golden_gummy_swet", GummySwetItem::new);
    public static final DeferredItem<Item> HEALING_STONE = ITEMS.registerItem("healing_stone", HealingStoneItem::new);
    public static final DeferredItem<Item> CANDY_CANE = ITEMS.registerSimpleItem("candy_cane", new Item.Properties().food(AetherFoods.CANDY_CANE, AetherConsumables.FAST_FOOD));
    public static final DeferredItem<Item> GINGERBREAD_MAN = ITEMS.registerSimpleItem("gingerbread_man", new Item.Properties().food(AetherFoods.GINGERBREAD_MAN, AetherConsumables.FAST_FOOD));

    // Accessories
    public static final DeferredItem<Item> IRON_RING = ITEMS.registerItem("iron_ring", (properties) -> new RingItem(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_IRON_RING, properties.stacksTo(1)));
    public static final DeferredItem<Item> GOLDEN_RING = ITEMS.registerItem("golden_ring", (properties) -> new RingItem(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GOLD_RING, properties.stacksTo(1)));
    public static final DeferredItem<Item> ZANITE_RING = ITEMS.registerItem("zanite_ring", ZaniteRingItem::new, new Item.Properties().durability(49));
    public static final DeferredItem<Item> ICE_RING = ITEMS.registerItem("ice_ring", IceRingItem::new, new Item.Properties().durability(125));

    public static final DeferredItem<Item> IRON_PENDANT = ITEMS.registerItem("iron_pendant", (properties) -> new PendantItem("iron_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_IRON_PENDANT, properties.stacksTo(1)));
    public static final DeferredItem<Item> GOLDEN_PENDANT = ITEMS.registerItem("golden_pendant", (properties) -> new PendantItem("golden_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GOLD_PENDANT, properties.stacksTo(1)));
    public static final DeferredItem<Item> ZANITE_PENDANT = ITEMS.registerItem("zanite_pendant", ZanitePendantItem::new, new Item.Properties().durability(98));
    public static final DeferredItem<Item> ICE_PENDANT = ITEMS.registerItem("ice_pendant", IcePendantItem::new, new Item.Properties().durability(250));

    public static final DeferredItem<Item> LEATHER_GLOVES = ITEMS.registerItem("leather_gloves", (properties) -> new LeatherGlovesItem(0.25, properties.durability(59)));
    public static final DeferredItem<Item> CHAINMAIL_GLOVES = ITEMS.registerItem("chainmail_gloves", (properties) -> new GlovesItem(ArmorMaterials.CHAINMAIL, 0.35, "chainmail_gloves", SoundEvents.ARMOR_EQUIP_CHAIN, properties.durability(131)));
    public static final DeferredItem<Item> IRON_GLOVES = ITEMS.registerItem("iron_gloves", (properties) -> new GlovesItem(ArmorMaterials.IRON, 0.5, "iron_gloves", SoundEvents.ARMOR_EQUIP_IRON, properties.durability(250)));
    public static final DeferredItem<Item> GOLDEN_GLOVES = ITEMS.registerItem("golden_gloves", (properties) -> new GoldGlovesItem(0.25, properties.durability(32)));
    public static final DeferredItem<Item> DIAMOND_GLOVES = ITEMS.registerItem("diamond_gloves", (properties) -> new GlovesItem(ArmorMaterials.DIAMOND, 0.75, "diamond_gloves", SoundEvents.ARMOR_EQUIP_DIAMOND, properties.durability(1561)));
    public static final DeferredItem<Item> NETHERITE_GLOVES = ITEMS.registerItem("netherite_gloves", (properties) -> new GlovesItem(ArmorMaterials.NETHERITE, 1.0, "netherite_gloves", SoundEvents.ARMOR_EQUIP_NETHERITE, properties.durability(2031).fireResistant()));
    public static final DeferredItem<Item> ZANITE_GLOVES = ITEMS.registerItem("zanite_gloves", (properties) -> new ZaniteGlovesItem(0.5, properties.durability(250)));
    public static final DeferredItem<Item> GRAVITITE_GLOVES = ITEMS.registerItem("gravitite_gloves", (properties) -> new GlovesItem(AetherArmorMaterials.GRAVITITE, 0.75, "gravitite_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, properties.durability(1561)));
    public static final DeferredItem<Item> VALKYRIE_GLOVES = ITEMS.registerItem("valkyrie_gloves", (properties) -> new GlovesItem(AetherArmorMaterials.VALKYRIE, 1.0, "valkyrie_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, properties.stacksTo(1).rarity(AETHER_LOOT).durability(1561)));
    public static final DeferredItem<Item> NEPTUNE_GLOVES = ITEMS.registerItem("neptune_gloves", (properties) -> new GlovesItem(AetherArmorMaterials.NEPTUNE, 0.5, "neptune_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, properties.stacksTo(1).rarity(AETHER_LOOT).durability(250)));
    public static final DeferredItem<Item> PHOENIX_GLOVES = ITEMS.registerItem("phoenix_gloves", (properties) -> new GlovesItem(AetherArmorMaterials.PHOENIX, 1.0, "phoenix_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, properties.stacksTo(1).rarity(AETHER_LOOT).fireResistant().durability(1561)));
    public static final DeferredItem<Item> OBSIDIAN_GLOVES = ITEMS.registerItem("obsidian_gloves", (properties) -> new GlovesItem(AetherArmorMaterials.OBSIDIAN, 1.0, "obsidian_gloves", AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, properties.stacksTo(1).rarity(AETHER_LOOT).durability(2031)));

    public static final DeferredItem<Item> RED_CAPE = ITEMS.registerItem("red_cape", (properties) -> new CapeItem("red_cape", properties.stacksTo(1)));
    public static final DeferredItem<Item> BLUE_CAPE = ITEMS.registerItem("blue_cape", (properties) -> new CapeItem("blue_cape", properties.stacksTo(1)));
    public static final DeferredItem<Item> YELLOW_CAPE = ITEMS.registerItem("yellow_cape", (properties) -> new CapeItem("yellow_cape", properties.stacksTo(1)));
    public static final DeferredItem<Item> WHITE_CAPE = ITEMS.registerItem("white_cape", (properties) -> new CapeItem("white_cape", properties.stacksTo(1)));
    public static final DeferredItem<Item> AGILITY_CAPE = ITEMS.registerItem("agility_cape", (properties) -> new AgilityCapeItem("agility_cape", properties.stacksTo(1).rarity(AETHER_LOOT)));
    public static final DeferredItem<Item> SWET_CAPE = ITEMS.registerItem("swet_cape", (properties) -> new CapeItem("swet_cape", properties.stacksTo(1)));
    public static final DeferredItem<Item> INVISIBILITY_CLOAK = ITEMS.registerItem("invisibility_cloak", InvisibilityCloakItem::new, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT));
    public static final DeferredItem<Item> VALKYRIE_CAPE = ITEMS.registerItem("valkyrie_cape", ValkyrieCapeItem::new, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT));

    public static final DeferredItem<Item> GOLDEN_FEATHER = ITEMS.registerItem("golden_feather", GoldenFeatherItem::new, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT));
    public static final DeferredItem<Item> REGENERATION_STONE = ITEMS.registerItem("regeneration_stone", RegenerationStoneItem::new, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT));
    public static final DeferredItem<Item> IRON_BUBBLE = ITEMS.registerItem("iron_bubble", IronBubbleItem::new, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT));
    public static final DeferredItem<Item> SHIELD_OF_REPULSION = ITEMS.registerItem("shield_of_repulsion", ShieldOfRepulsionItem::new, new Item.Properties().durability(512).rarity(AETHER_LOOT));

    // Materials
    public static final DeferredItem<Item> SKYROOT_STICK = ITEMS.registerSimpleItem("skyroot_stick");
    public static final DeferredItem<Item> GOLDEN_AMBER = ITEMS.registerSimpleItem("golden_amber");
    public static final DeferredItem<Item> SWET_BALL = ITEMS.registerItem("swet_ball", SwetBallItem::new);
    public static final DeferredItem<Item> AECHOR_PETAL = ITEMS.registerSimpleItem("aechor_petal");
    public static final DeferredItem<Item> AMBROSIUM_SHARD = ITEMS.registerItem("ambrosium_shard", AmbrosiumShardItem::new);
    public static final DeferredItem<Item> ZANITE_GEMSTONE = ITEMS.registerSimpleItem("zanite_gemstone");

    // Misc
    public static final DeferredItem<Item> VICTORY_MEDAL = ITEMS.registerSimpleItem("victory_medal", new Item.Properties().stacksTo(10).rarity(AETHER_LOOT));

    public static final DeferredItem<Item> BRONZE_DUNGEON_KEY = ITEMS.registerItem("bronze_dungeon_key", (properties) -> new Item(properties.stacksTo(1).rarity(AETHER_LOOT).fireResistant().component(AetherDataComponents.DUNGEON_KIND.get(), new DungeonKind(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "bronze")))));
    public static final DeferredItem<Item> SILVER_DUNGEON_KEY = ITEMS.registerItem("silver_dungeon_key", (properties) -> new Item(properties.stacksTo(1).rarity(AETHER_LOOT).fireResistant().component(AetherDataComponents.DUNGEON_KIND.get(), new DungeonKind(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "silver")))));
    public static final DeferredItem<Item> GOLD_DUNGEON_KEY = ITEMS.registerItem("gold_dungeon_key", (properties) -> new Item(properties.stacksTo(1).rarity(AETHER_LOOT).fireResistant().component(AetherDataComponents.DUNGEON_KIND.get(), new DungeonKind(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gold")))));

    public static final DeferredItem<Item> MUSIC_DISC_AETHER_TUNE = ITEMS.registerSimpleItem("music_disc_aether_tune", new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.AETHER_TUNE));
    public static final DeferredItem<Item> MUSIC_DISC_ASCENDING_DAWN = ITEMS.registerSimpleItem("music_disc_ascending_dawn", new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.ASCENDING_DAWN));
    public static final DeferredItem<Item> MUSIC_DISC_CHINCHILLA = ITEMS.registerSimpleItem("music_disc_chinchilla", new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.CHINCHILLA));
    public static final DeferredItem<Item> MUSIC_DISC_HIGH = ITEMS.registerSimpleItem("music_disc_high", new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.HIGH));
    public static final DeferredItem<Item> MUSIC_DISC_KLEPTO = ITEMS.registerSimpleItem("music_disc_klepto", new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.KLEPTO));
    public static final DeferredItem<Item> MUSIC_DISC_SLIDERS_WRATH = ITEMS.registerSimpleItem("music_disc_sliders_wrath", new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(AetherJukeboxSongs.SLIDERS_WRATH));

    public static final DeferredItem<Item> SKYROOT_BUCKET = ITEMS.registerItem("skyroot_bucket", (properties) -> new SkyrootBucketItem(Fluids.EMPTY, properties.stacksTo(16)));
    public static final DeferredItem<Item> SKYROOT_WATER_BUCKET = ITEMS.registerItem("skyroot_water_bucket", (properties) -> new SkyrootBucketItem(Fluids.WATER, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_POISON_BUCKET = ITEMS.registerItem("skyroot_poison_bucket", (properties) -> new Item(properties.craftRemainder(SKYROOT_BUCKET.get()).component(DataComponents.CONSUMABLE, AetherConsumables.POISON_BUCKET).usingConvertsTo(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_REMEDY_BUCKET = ITEMS.registerItem("skyroot_remedy_bucket", (properties) -> new Item(properties.craftRemainder(SKYROOT_BUCKET.get()).component(DataComponents.CONSUMABLE, AetherConsumables.REMEDY_BUCKET).usingConvertsTo(SKYROOT_BUCKET.get()).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> SKYROOT_MILK_BUCKET = ITEMS.registerItem("skyroot_milk_bucket", (properties) -> new Item(properties.craftRemainder(SKYROOT_BUCKET.get()).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_POWDER_SNOW_BUCKET = ITEMS.registerItem("skyroot_powder_snow_bucket", (properties) -> new SkyrootSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_COD_BUCKET = ITEMS.registerItem("skyroot_cod_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_SALMON_BUCKET = ITEMS.registerItem("skyroot_salmon_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_PUFFERFISH_BUCKET = ITEMS.registerItem("skyroot_pufferfish_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_TROPICAL_FISH_BUCKET = ITEMS.registerItem("skyroot_tropical_fish_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_AXOLOTL_BUCKET = ITEMS.registerItem("skyroot_axolotl_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> SKYROOT_TADPOLE_BUCKET = ITEMS.registerItem("skyroot_tadpole_bucket", (properties) -> new SkyrootMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, properties.craftRemainder(SKYROOT_BUCKET.get()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> SKYROOT_BOAT = ITEMS.registerItem("skyroot_boat", (properties) -> new BoatItem(AetherEntityTypes.SKYROOT_BOAT.get(), properties.stacksTo(1)));
    public static final DeferredItem<Item> SKYROOT_CHEST_BOAT = ITEMS.registerItem("skyroot_chest_boat", (properties) -> new BoatItem(AetherEntityTypes.SKYROOT_CHEST_BOAT.get(), properties.stacksTo(1)));

    public static final DeferredItem<Item> COLD_PARACHUTE = ITEMS.registerItem("cold_parachute", (properties) -> new ParachuteItem(AetherEntityTypes.COLD_PARACHUTE, properties.durability(1)));
    public static final DeferredItem<Item> GOLDEN_PARACHUTE = ITEMS.registerItem("golden_parachute", (properties) -> new ParachuteItem(AetherEntityTypes.GOLDEN_PARACHUTE, properties.durability(20)));

    public static final DeferredItem<Item> BLUE_MOA_EGG = ITEMS.registerItem("blue_moa_egg", (properties) -> new MoaEggItem(AetherMoaTypes.BLUE, 0x7777FF, properties));
    public static final DeferredItem<Item> WHITE_MOA_EGG = ITEMS.registerItem("white_moa_egg", (properties) -> new MoaEggItem(AetherMoaTypes.WHITE, 0xFFFFFF, properties));
    public static final DeferredItem<Item> BLACK_MOA_EGG = ITEMS.registerItem("black_moa_egg", (properties) -> new MoaEggItem(AetherMoaTypes.BLACK, 0x222222, properties));

    public static final DeferredItem<Item> NATURE_STAFF = ITEMS.registerSimpleItem("nature_staff", new Item.Properties().durability(100));
    public static final DeferredItem<Item> CLOUD_STAFF = ITEMS.registerItem("cloud_staff", CloudStaffItem::new);

    public static final DeferredItem<Item> LIFE_SHARD = ITEMS.registerItem("life_shard", LifeShardItem::new, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT));

    public static final DeferredItem<Item> BOOK_OF_LORE = ITEMS.registerItem("book_of_lore", LoreBookItem::new, new Item.Properties().stacksTo(1).rarity(AETHER_LOOT));

    public static final DeferredItem<Item> AETHER_PORTAL_FRAME = ITEMS.registerItem("aether_portal_frame", AetherPortalItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<SpawnEggItem> AECHOR_PLANT_SPAWN_EGG = ITEMS.registerItem("aechor_plant_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.AECHOR_PLANT.get(), 0x076178, 0x4BC69E, properties));
    public static final DeferredItem<SpawnEggItem> AERBUNNY_SPAWN_EGG = ITEMS.registerItem("aerbunny_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.AERBUNNY.get(), 0xE2FCFF, 0xFFDFF9, properties));
    public static final DeferredItem<SpawnEggItem> AERWHALE_SPAWN_EGG = ITEMS.registerItem("aerwhale_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.AERWHALE.get(), 0xC0E7FD, 0x879EAA, properties));
    public static final DeferredItem<SpawnEggItem> COCKATRICE_SPAWN_EGG = ITEMS.registerItem("cockatrice_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.COCKATRICE.get(), 0x6CB15C, 0x6C579D, properties));
    public static final DeferredItem<SpawnEggItem> FIRE_MINION_SPAWN_EGG = ITEMS.registerItem("fire_minion_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.FIRE_MINION.get(), 0xFF6D01, 0xFEF500, properties));
    public static final DeferredItem<SpawnEggItem> FLYING_COW_SPAWN_EGG = ITEMS.registerItem("flying_cow_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.FLYING_COW.get(), 0xD8D8D8, 0xFFD939, properties));
    public static final DeferredItem<SpawnEggItem> MIMIC_SPAWN_EGG = ITEMS.registerItem("mimic_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.MIMIC.get(), 0xB18132, 0x605A4E, properties));
    public static final DeferredItem<SpawnEggItem> MOA_SPAWN_EGG = ITEMS.registerItem("moa_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.MOA.get(), 0x87BFEF, 0x7A7A7A, properties));
    public static final DeferredItem<SpawnEggItem> PHYG_SPAWN_EGG = ITEMS.registerItem("phyg_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.PHYG.get(), 0xFFC1D0, 0xFFD939, properties));
    public static final DeferredItem<SpawnEggItem> SENTRY_SPAWN_EGG = ITEMS.registerItem("sentry_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.SENTRY.get(), 0x808080, 0x3A8AEC, properties));
    public static final DeferredItem<SpawnEggItem> SHEEPUFF_SPAWN_EGG = ITEMS.registerItem("sheepuff_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.SHEEPUFF.get(), 0xE2FCFF, 0xCB9090, properties));
    public static final DeferredItem<SpawnEggItem> BLUE_SWET_SPAWN_EGG = ITEMS.registerItem("blue_swet_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.BLUE_SWET.get(), 0x4FB1DA, 0xCDDA4F, properties));
    public static final DeferredItem<SpawnEggItem> GOLDEN_SWET_SPAWN_EGG = ITEMS.registerItem("golden_swet_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.GOLDEN_SWET.get(), 0xCDDA4F, 0x4FB1DA, properties));
    public static final DeferredItem<SpawnEggItem> WHIRLWIND_SPAWN_EGG = ITEMS.registerItem("whirlwind_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.WHIRLWIND.get(), 0x9FC3F7, 0xFFFFFF, properties));
    public static final DeferredItem<SpawnEggItem> EVIL_WHIRLWIND_SPAWN_EGG = ITEMS.registerItem("evil_whirlwind_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.EVIL_WHIRLWIND.get(), 0x9FC3F7, 0x111111, properties));
    public static final DeferredItem<SpawnEggItem> VALKYRIE_SPAWN_EGG = ITEMS.registerItem("valkyrie_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.VALKYRIE.get(), 0xF9F5E3, 0xF2D200, properties));
    public static final DeferredItem<SpawnEggItem> VALKYRIE_QUEEN_SPAWN_EGG = ITEMS.registerItem("valkyrie_queen_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.VALKYRIE_QUEEN.get(), 0xF2D200, 0xF9F5E3, properties));
    public static final DeferredItem<SpawnEggItem> SLIDER_SPAWN_EGG = ITEMS.registerItem("slider_spawn_egg", (properties) -> new SliderSpawnEggItem(AetherEntityTypes.SLIDER.get(), 0xA7A7A7, 0x5C9FF2, properties));
    public static final DeferredItem<SpawnEggItem> SUN_SPIRIT_SPAWN_EGG = ITEMS.registerItem("sun_spirit_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.SUN_SPIRIT.get(), 0xFEF500, 0xFF6D01, properties));
    public static final DeferredItem<SpawnEggItem> ZEPHYR_SPAWN_EGG = ITEMS.registerItem("zephyr_spawn_egg", (properties) -> new SpawnEggItem(AetherEntityTypes.ZEPHYR.get(), 0xDFDFDF, 0x99CFE8, properties));

    public static ItemStack SWET_BANNER = null;

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
        SkyrootBucketItem.REPLACEMENTS.put(() -> Items.MILK_BUCKET, AetherItems.SKYROOT_MILK_BUCKET);
    }

    public static ItemStack createSwetBannerItemStack(HolderGetter<BannerPattern> patternRegistry) {
        if (SWET_BANNER == null) {
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
            SWET_BANNER = bannerStack;
        }
        return SWET_BANNER;
    }
}
