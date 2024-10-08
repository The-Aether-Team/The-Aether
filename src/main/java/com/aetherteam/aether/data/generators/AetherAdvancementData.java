package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.advancement.IncubationTrigger;
import com.aetherteam.aether.advancement.LoreTrigger;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.data.resources.registries.AetherMoaTypes;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.mixin.mixins.common.accessor.HoeItemAccessor;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class AetherAdvancementData extends AdvancementProvider {
    public AetherAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new AetherAdvancements()));
    }

    public static class AetherAdvancements implements AdvancementGenerator {
        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            AdvancementHolder theAether = Advancement.Builder.advancement()
                    .display(AetherItems.AETHER_PORTAL_FRAME.get(),
                            Component.translatable("advancement.aether.the_aether"),
                            Component.translatable("advancement.aether.the_aether.desc"),
                            ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/block/dungeon/carved_stone.png"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("the_aether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherDimensions.AETHER_LEVEL))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "the_aether"), existingFileHelper);

            AdvancementHolder enterAether = Advancement.Builder.advancement()
                    .parent(theAether)
                    .display(Blocks.GLOWSTONE,
                            Component.translatable("advancement.aether.enter_aether"),
                            Component.translatable("advancement.aether.enter_aether.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("enter_aether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherDimensions.AETHER_LEVEL))
                    .rewards(new AdvancementRewards(0, List.of(AetherLoot.ENTER_AETHER), List.of(), Optional.empty()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "enter_aether"), existingFileHelper);

            AdvancementHolder readLore = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherItems.BOOK_OF_LORE.get(),
                            Component.translatable("advancement.aether.read_lore"),
                            Component.translatable("advancement.aether.read_lore.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("lore_book_entry", LoreTrigger.Instance.forAny())
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "read_lore"), existingFileHelper);

            AdvancementHolder loreception = Advancement.Builder.advancement()
                    .parent(readLore)
                    .display(AetherItems.BOOK_OF_LORE.get(),
                            Component.translatable("advancement.aether.loreception"),
                            Component.translatable("advancement.aether.loreception.desc"),
                            null,
                            AdvancementType.GOAL, true, true, true)
                    .addCriterion("lore_book_entry", LoreTrigger.Instance.forItem(AetherItems.BOOK_OF_LORE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "loreception"), existingFileHelper);

            AdvancementHolder zanite = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherItems.ZANITE_GEMSTONE.get(),
                            Component.translatable("advancement.aether.zanite"),
                            Component.translatable("advancement.aether.zanite.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("zanite", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.ZANITE_GEMSTONE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "zanite"), existingFileHelper);

            AdvancementHolder craftAltar = Advancement.Builder.advancement()
                    .parent(zanite)
                    .display(AetherBlocks.ALTAR.get(),
                            Component.translatable("advancement.aether.craft_altar"),
                            Component.translatable("advancement.aether.craft_altar.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_altar", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ALTAR.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "craft_altar"), existingFileHelper);

            AdvancementHolder icestone = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherBlocks.ICESTONE.get(),
                            Component.translatable("advancement.aether.icestone"),
                            Component.translatable("advancement.aether.icestone.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("icestone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ICESTONE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "icestone"), existingFileHelper);

            AdvancementHolder iceAccessory = Advancement.Builder.advancement()
                    .parent(icestone)
                    .display(AetherItems.ICE_PENDANT.get(),
                            Component.translatable("advancement.aether.ice_accessory"),
                            Component.translatable("advancement.aether.ice_accessory.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("ice_pendant", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.ICE_PENDANT.get()))
                    .addCriterion("ice_ring", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.ICE_RING.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "ice_accessory"), existingFileHelper);

            AdvancementHolder blueAercloud = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherBlocks.BLUE_AERCLOUD.get(),
                            Component.translatable("advancement.aether.blue_aercloud"),
                            Component.translatable("advancement.aether.blue_aercloud.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("blue_aercloud", EnterBlockTrigger.TriggerInstance.entersBlock(AetherBlocks.BLUE_AERCLOUD.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "blue_aercloud"), existingFileHelper);

            AdvancementHolder obtainEgg = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(AetherItems.BLUE_MOA_EGG.get(),
                            Component.translatable("advancement.aether.obtain_egg"),
                            Component.translatable("advancement.aether.obtain_egg.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("moa_egg", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(AetherTags.Items.MOA_EGGS).build()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "obtain_egg"), existingFileHelper);

            AdvancementHolder obtainPetal = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherItems.AECHOR_PETAL.get(),
                            Component.translatable("advancement.aether.obtain_petal"),
                            Component.translatable("advancement.aether.obtain_petal.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("aechor_petal", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.AECHOR_PETAL.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "obtain_petal"), existingFileHelper);

            AdvancementHolder preventAechorPlantSpawning = Advancement.Builder.advancement()
                .parent(obtainPetal)
                .display(AetherBlocks.PURPLE_FLOWER.get(),
                    Component.translatable("advancement.aether.prevent_aechor_petal_spawning"),
                    Component.translatable("advancement.aether.prevent_aechor_petal_spawning.desc"),
                    null,
                    AdvancementType.TASK, true, true, false)
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("place_flower", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(AetherTags.Blocks.ENCHANTED_GRASS)), ItemPredicate.Builder.item().of(AetherTags.Items.AECHOR_PLANT_SPAWNABLE_DETERRENT)))
                .addCriterion("enchant_grass", itemUsedOnBlockCheckAbove(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(AetherTags.Blocks.ENCHANTED_GRASS)), LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(AetherTags.Blocks.AECHOR_PLANT_SPAWNABLE_DETERRENT)), ItemPredicate.Builder.item().of(AetherItems.AMBROSIUM_SHARD)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "prevent_aechor_petal_spawning"), existingFileHelper);

            AdvancementHolder preventSwetSpawning = Advancement.Builder.advancement()
                .parent(preventAechorPlantSpawning)
                .display(AetherItems.createSwetBannerItemStack(provider.lookupOrThrow(Registries.BANNER_PATTERN)),
                    Component.translatable("advancement.aether.prevent_swet_spawning"),
                    Component.translatable("advancement.aether.prevent_swet_spawning.desc"),
                    null,
                    AdvancementType.TASK, true, true, false)
                .addCriterion("place_banner", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location(), ItemPredicate.Builder.item().of(Items.BLACK_BANNER).hasComponents(DataComponentPredicate.allOf(AetherItems.createSwetBannerItemStack(provider.lookupOrThrow(Registries.BANNER_PATTERN)).getComponents()))))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "prevent_swet_spawning"), existingFileHelper);

            AdvancementHolder incubateMoa = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherBlocks.INCUBATOR.get(),
                            Component.translatable("advancement.aether.incubate_moa"),
                            Component.translatable("advancement.aether.incubate_moa.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("incubate_moa", IncubationTrigger.Instance.forItem(ItemPredicate.Builder.item().of(AetherTags.Items.MOA_EGGS).build()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "incubate_moa"), existingFileHelper);

            CompoundTag moaTag = new CompoundTag();
            moaTag.putString("MoaType", AetherMoaTypes.BLACK.location().toString());

            AdvancementHolder blackMoa = Advancement.Builder.advancement()
                    .parent(incubateMoa)
                    .display(Items.FEATHER,
                            Component.translatable("advancement.aether.black_moa"),
                            Component.translatable("advancement.aether.black_moa.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("black_moa", StartRidingTrigger.TriggerInstance.playerStartsRiding(EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity().of(AetherEntityTypes.MOA.get()).nbt(new NbtPredicate(moaTag)))))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "black_moa"), existingFileHelper);

            AdvancementHolder mountPhyg = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(Items.SADDLE,
                            Component.translatable("advancement.aether.mount_phyg"),
                            Component.translatable("advancement.aether.mount_phyg.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("mount_phyg", StartRidingTrigger.TriggerInstance.playerStartsRiding(EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity().of(AetherEntityTypes.PHYG.get()))))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "mount_phyg"), existingFileHelper);

            AdvancementHolder enchantedGravitite = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherBlocks.ENCHANTED_GRAVITITE.get(),
                            Component.translatable("advancement.aether.enchanted_gravitite"),
                            Component.translatable("advancement.aether.enchanted_gravitite.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("enchanted_gravitite", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ENCHANTED_GRAVITITE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "enchanted_gravitite"), existingFileHelper);

            AdvancementHolder gravititeArmor = Advancement.Builder.advancement()
                    .parent(enchantedGravitite)
                    .display(AetherItems.GRAVITITE_CHESTPLATE.get(),
                            Component.translatable("advancement.aether.gravitite_armor"),
                            Component.translatable("advancement.aether.gravitite_armor.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("gravitite_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_HELMET.get()))
                    .addCriterion("gravitite_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_CHESTPLATE.get()))
                    .addCriterion("gravitite_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_LEGGINGS.get()))
                    .addCriterion("gravitite_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_BOOTS.get()))
                    .addCriterion("gravitite_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_GLOVES.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gravitite_armor"), existingFileHelper);

            AdvancementHolder bronzeDungeon = Advancement.Builder.advancement()
                    .parent(enchantedGravitite)
                    .display(AetherItems.BRONZE_DUNGEON_KEY.get(),
                            Component.translatable("advancement.aether.bronze_dungeon"),
                            Component.translatable("advancement.aether.bronze_dungeon.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("kill_slider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.SLIDER.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "bronze_dungeon"), existingFileHelper);

            AdvancementHolder hammerLoot = Advancement.Builder.advancement()
                    .parent(bronzeDungeon)
                    .display(AetherItems.HAMMER_OF_KINGBDOGZ.get(),
                            Component.translatable("advancement.aether.hammer_loot"),
                            Component.translatable("advancement.aether.hammer_loot.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("hammer_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.HAMMER_OF_KINGBDOGZ.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hammer_loot"), existingFileHelper);

            AdvancementHolder zephyrHammer = Advancement.Builder.advancement()
                    .parent(hammerLoot)
                    .display(Items.SNOWBALL,
                            Component.translatable("advancement.aether.zephyr_hammer"),
                            Component.translatable("advancement.aether.zephyr_hammer.desc"),
                            null,
                            AdvancementType.CHALLENGE, true, true, true)
                    .addCriterion("zephyr_hammer", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.ZEPHYR.get()), DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(AetherEntityTypes.HAMMER_PROJECTILE.get()))))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "zephyr_hammer"), existingFileHelper);

            AdvancementHolder lanceLoot = Advancement.Builder.advancement()
                    .parent(bronzeDungeon)
                    .display(AetherItems.VALKYRIE_LANCE.get(),
                            Component.translatable("advancement.aether.lance_loot"),
                            Component.translatable("advancement.aether.lance_loot.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("lance_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_LANCE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "lance_loot"), existingFileHelper);

            AdvancementHolder silverDungeon = Advancement.Builder.advancement()
                    .parent(lanceLoot)
                    .display(AetherItems.SILVER_DUNGEON_KEY.get(),
                            Component.translatable("advancement.aether.silver_dungeon"),
                            Component.translatable("advancement.aether.silver_dungeon.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("kill_valkyrie_queen", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.VALKYRIE_QUEEN.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "silver_dungeon"), existingFileHelper);

            AdvancementHolder valkyrieLoot = Advancement.Builder.advancement()
                    .parent(silverDungeon)
                    .display(AetherItems.VALKYRIE_HELMET.get(),
                            Component.translatable("advancement.aether.valkyrie_loot"),
                            Component.translatable("advancement.aether.valkyrie_loot.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("valkyrie_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_PICKAXE.get()))
                    .addCriterion("valkyrie_hoe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_HOE.get()))
                    .addCriterion("valkyrie_axe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_AXE.get()))
                    .addCriterion("valkyrie_shovel", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_SHOVEL.get()))
                    .addCriterion("valkyrie_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_HELMET.get()))
                    .addCriterion("valkyrie_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_CHESTPLATE.get()))
                    .addCriterion("valkyrie_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_LEGGINGS.get()))
                    .addCriterion("valkyrie_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_BOOTS.get()))
                    .addCriterion("valkyrie_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_GLOVES.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "valkyrie_loot"), existingFileHelper);

            AdvancementHolder valkyrieHoe = Advancement.Builder.advancement()
                    .parent(valkyrieLoot)
                    .display(AetherBlocks.AETHER_FARMLAND.get(),
                            Component.translatable("advancement.aether.valkyrie_hoe"),
                            Component.translatable("advancement.aether.valkyrie_hoe.desc"),
                            null,
                            AdvancementType.CHALLENGE, true, true, true)
                    .addCriterion("valkyrie_hoe", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(Stream.concat(AbilityHooks.ToolHooks.TILLABLES.keySet().stream(), HoeItemAccessor.aether$getTillables().keySet().stream().sorted(Comparator.comparing(Block::getDescriptionId))).toList())), ItemPredicate.Builder.item().of(AetherItems.VALKYRIE_HOE.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "valkyrie_hoe"), existingFileHelper);

            AdvancementHolder regenStone = Advancement.Builder.advancement()
                    .parent(silverDungeon)
                    .display(AetherItems.REGENERATION_STONE.get(),
                            Component.translatable("advancement.aether.regen_stone"),
                            Component.translatable("advancement.aether.regen_stone.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("regen_stone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.REGENERATION_STONE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "regen_stone"), existingFileHelper);

            AdvancementHolder goldDungeon = Advancement.Builder.advancement()
                    .parent(regenStone)
                    .display(AetherItems.GOLD_DUNGEON_KEY.get(),
                            Component.translatable("advancement.aether.gold_dungeon"),
                            Component.translatable("advancement.aether.gold_dungeon.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("kill_sun_spirit", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.SUN_SPIRIT.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gold_dungeon"), existingFileHelper);

            AdvancementHolder phoenixArmor = Advancement.Builder.advancement()
                    .parent(goldDungeon)
                    .display(AetherItems.PHOENIX_HELMET.get(),
                            Component.translatable("advancement.aether.phoenix_armor"),
                            Component.translatable("advancement.aether.phoenix_armor.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("phoenix_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_HELMET.get()))
                    .addCriterion("phoenix_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_CHESTPLATE.get()))
                    .addCriterion("phoenix_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_LEGGINGS.get()))
                    .addCriterion("phoenix_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_BOOTS.get()))
                    .addCriterion("phoenix_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_GLOVES.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "phoenix_armor"), existingFileHelper);

            AdvancementHolder obsidianArmor = Advancement.Builder.advancement()
                    .parent(phoenixArmor)
                    .display(AetherItems.OBSIDIAN_CHESTPLATE.get(),
                            Component.translatable("advancement.aether.obsidian_armor"),
                            Component.translatable("advancement.aether.obsidian_armor.desc"),
                            null,
                            AdvancementType.CHALLENGE, true, true, true)
                    .addCriterion("obsidian_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_HELMET.get()))
                    .addCriterion("obsidian_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_CHESTPLATE.get()))
                    .addCriterion("obsidian_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_LEGGINGS.get()))
                    .addCriterion("obsidian_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_BOOTS.get()))
                    .addCriterion("obsidian_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_GLOVES.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "obsidian_armor"), existingFileHelper);

            AdvancementHolder aetherSleep = Advancement.Builder.advancement()
                    .parent(goldDungeon)
                    .display(AetherBlocks.SKYROOT_BED.get(),
                            Component.translatable("advancement.aether.aether_sleep"),
                            Component.translatable("advancement.aether.aether_sleep.desc"),
                            null,
                            AdvancementType.CHALLENGE, true, true, true)
                    .addCriterion("aether_sleep", CriteriaTriggers.SLEPT_IN_BED.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.inDimension(AetherDimensions.AETHER_LEVEL)))))))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "aether_sleep"), existingFileHelper);
        }
    }

    private static ItemUsedOnLocationTrigger.TriggerInstance itemUsedOnLocationCheckAbove(LocationPredicate.Builder location, LocationPredicate.Builder above, ItemPredicate.Builder item) {
        ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.create(LocationCheck.checkLocation(location).build(), LocationCheck.checkLocation(above, BlockPos.ZERO.above()).build(), MatchTool.toolMatches(item).build());
        return new ItemUsedOnLocationTrigger.TriggerInstance(Optional.empty(), Optional.of(contextawarepredicate));
    }

    public static Criterion<ItemUsedOnLocationTrigger.TriggerInstance> itemUsedOnBlockCheckAbove(LocationPredicate.Builder location, LocationPredicate.Builder above, ItemPredicate.Builder item) {
        return CriteriaTriggers.ITEM_USED_ON_BLOCK.createCriterion(itemUsedOnLocationCheckAbove(location, above, item));
    }
}
