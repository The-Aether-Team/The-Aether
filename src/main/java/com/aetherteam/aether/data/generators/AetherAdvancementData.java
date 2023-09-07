package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.advancement.IncubationTrigger;
import com.aetherteam.aether.advancement.LoreTrigger;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.AetherLoot;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AetherAdvancementData extends ForgeAdvancementProvider {
    public AetherAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new AetherAdvancements()));
    }

    public static class AetherAdvancements implements AdvancementGenerator {

        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
            Advancement theAether = Advancement.Builder.advancement()
                    .display(AetherItems.AETHER_PORTAL_FRAME.get(),
                            Component.translatable("advancement.aether.the_aether"),
                            Component.translatable("advancement.aether.the_aether.desc"),
                            new ResourceLocation(Aether.MODID, "textures/block/dungeon/carved_stone.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("the_aether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherDimensions.AETHER_LEVEL))
                    .save(consumer, new ResourceLocation(Aether.MODID, "the_aether"), existingFileHelper);

            Advancement enterAether = Advancement.Builder.advancement()
                    .parent(theAether)
                    .display(Blocks.GLOWSTONE,
                            Component.translatable("advancement.aether.enter_aether"),
                            Component.translatable("advancement.aether.enter_aether.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("enter_aether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherDimensions.AETHER_LEVEL))
                    .rewards(new AdvancementRewards(0, new ResourceLocation[]{AetherLoot.ENTER_AETHER}, new ResourceLocation[0], CommandFunction.CacheableFunction.NONE))
                    .save(consumer, new ResourceLocation(Aether.MODID, "enter_aether"), existingFileHelper);

            Advancement readLore = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherItems.BOOK_OF_LORE.get(),
                            Component.translatable("advancement.aether.read_lore"),
                            Component.translatable("advancement.aether.read_lore.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("lore_book_entry", LoreTrigger.Instance.forAny())
                    .save(consumer, new ResourceLocation(Aether.MODID, "read_lore"), existingFileHelper);

            Advancement loreception = Advancement.Builder.advancement()
                    .parent(readLore)
                    .display(AetherItems.BOOK_OF_LORE.get(),
                            Component.translatable("advancement.aether.loreception"),
                            Component.translatable("advancement.aether.loreception.desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion("lore_book_entry", LoreTrigger.Instance.forItem(AetherItems.BOOK_OF_LORE.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "loreception"), existingFileHelper);

            Advancement zanite = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherItems.ZANITE_GEMSTONE.get(),
                            Component.translatable("advancement.aether.zanite"),
                            Component.translatable("advancement.aether.zanite.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("zanite", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.ZANITE_GEMSTONE.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "zanite"), existingFileHelper);

            Advancement craftAltar = Advancement.Builder.advancement()
                    .parent(zanite)
                    .display(AetherBlocks.ALTAR.get(),
                            Component.translatable("advancement.aether.craft_altar"),
                            Component.translatable("advancement.aether.craft_altar.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("craft_altar", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ALTAR.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "craft_altar"), existingFileHelper);

            Advancement icestone = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherBlocks.ICESTONE.get(),
                            Component.translatable("advancement.aether.icestone"),
                            Component.translatable("advancement.aether.icestone.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("icestone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ICESTONE.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "icestone"), existingFileHelper);

            Advancement iceAccessory = Advancement.Builder.advancement()
                    .parent(icestone)
                    .display(AetherItems.ICE_PENDANT.get(),
                            Component.translatable("advancement.aether.ice_accessory"),
                            Component.translatable("advancement.aether.ice_accessory.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .requirements(RequirementsStrategy.OR)
                    .addCriterion("ice_pendant", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.ICE_PENDANT.get()))
                    .addCriterion("ice_ring", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.ICE_RING.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "ice_accessory"), existingFileHelper);

            Advancement blueAercloud = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherBlocks.BLUE_AERCLOUD.get(),
                            Component.translatable("advancement.aether.blue_aercloud"),
                            Component.translatable("advancement.aether.blue_aercloud.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("blue_aercloud", EnterBlockTrigger.TriggerInstance.entersBlock(AetherBlocks.BLUE_AERCLOUD.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "blue_aercloud"), existingFileHelper);

            Advancement obtainEgg = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(AetherItems.BLUE_MOA_EGG.get(),
                            Component.translatable("advancement.aether.obtain_egg"),
                            Component.translatable("advancement.aether.obtain_egg.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .requirements(RequirementsStrategy.OR)
                    .addCriterion( "blue_moa_egg", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.BLUE_MOA_EGG.get()))
                    .addCriterion( "white_moa_egg", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.WHITE_MOA_EGG.get()))
                    .addCriterion( "black_moa_egg", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.BLACK_MOA_EGG.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "obtain_egg"), existingFileHelper);

            Advancement obtainPetal = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherItems.AECHOR_PETAL.get(),
                            Component.translatable("advancement.aether.obtain_petal"),
                            Component.translatable("advancement.aether.obtain_petal.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("aechor_petal", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.AECHOR_PETAL.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID,"obtain_petal"), existingFileHelper);

            Advancement incubateMoa = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherBlocks.INCUBATOR.get(),
                            Component.translatable("advancement.aether.incubate_moa"),
                            Component.translatable("advancement.aether.incubate_moa.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("incubate_moa", IncubationTrigger.Instance.forItem(ItemPredicate.Builder.item().of(AetherTags.Items.MOA_EGGS).build()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "incubate_moa"), existingFileHelper);

            CompoundTag moaTag = new CompoundTag();
            moaTag.putString("MoaType", AetherMoaTypes.BLACK.get().getId().toString());

            Advancement blackMoa = Advancement.Builder.advancement()
                    .parent(incubateMoa)
                    .display(Items.FEATHER,
                            Component.translatable("advancement.aether.black_moa"),
                            Component.translatable("advancement.aether.black_moa.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion("black_moa", StartRidingTrigger.TriggerInstance.playerStartsRiding(EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity().of(AetherEntityTypes.MOA.get()).nbt(new NbtPredicate(moaTag)).build())))
                    .save(consumer, new ResourceLocation(Aether.MODID, "black_moa"), existingFileHelper);

            Advancement mountPhyg = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(Items.SADDLE,
                            Component.translatable("advancement.aether.mount_phyg"),
                            Component.translatable("advancement.aether.mount_phyg.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("mount_phyg", StartRidingTrigger.TriggerInstance.playerStartsRiding(EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity().of(AetherEntityTypes.PHYG.get()).build())))
                    .save(consumer, new ResourceLocation(Aether.MODID, "mount_phyg"), existingFileHelper);

            Advancement enchantedGravitite = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherBlocks.ENCHANTED_GRAVITITE.get(),
                            Component.translatable("advancement.aether.enchanted_gravitite"),
                            Component.translatable("advancement.aether.enchanted_gravitite.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("enchanted_gravitite", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ENCHANTED_GRAVITITE.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "enchanted_gravitite"), existingFileHelper);

            Advancement gravititeArmor = Advancement.Builder.advancement()
                    .parent(enchantedGravitite)
                    .display(AetherItems.GRAVITITE_CHESTPLATE.get(),
                            Component.translatable("advancement.aether.gravitite_armor"),
                            Component.translatable("advancement.aether.gravitite_armor.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion("gravitite_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_HELMET.get()))
                    .addCriterion("gravitite_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_CHESTPLATE.get()))
                    .addCriterion("gravitite_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_LEGGINGS.get()))
                    .addCriterion("gravitite_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_BOOTS.get()))
                    .addCriterion("gravitite_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_GLOVES.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "gravitite_armor"), existingFileHelper);

            Advancement bronzeDungeon = Advancement.Builder.advancement()
                    .parent(enchantedGravitite)
                    .display(AetherItems.BRONZE_DUNGEON_KEY.get(),
                            Component.translatable("advancement.aether.bronze_dungeon"),
                            Component.translatable("advancement.aether.bronze_dungeon.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion("kill_slider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.SLIDER.get())))
                    .save(consumer, new ResourceLocation(Aether.MODID, "bronze_dungeon"), existingFileHelper);

            Advancement hammerLoot = Advancement.Builder.advancement()
                    .parent(bronzeDungeon)
                    .display(AetherItems.HAMMER_OF_KINGBDOGZ.get(),
                            Component.translatable("advancement.aether.hammer_loot"),
                            Component.translatable("advancement.aether.hammer_loot.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion("hammer_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.HAMMER_OF_KINGBDOGZ.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "hammer_loot"), existingFileHelper);

            Advancement zephyrHammer = Advancement.Builder.advancement()
                    .parent(hammerLoot)
                    .display(Items.SNOWBALL,
                            Component.translatable("advancement.aether.zephyr_hammer"),
                            Component.translatable("advancement.aether.zephyr_hammer.desc"),
                            null,
                            FrameType.CHALLENGE, true, true, true)
                    .addCriterion("zephyr_hammer", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.ZEPHYR.get()), DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(AetherEntityTypes.HAMMER_PROJECTILE.get()))))
                    .save(consumer, new ResourceLocation(Aether.MODID, "zephyr_hammer"), existingFileHelper);

            Advancement lanceLoot = Advancement.Builder.advancement()
                    .parent(bronzeDungeon)
                    .display(AetherItems.VALKYRIE_LANCE.get(),
                            Component.translatable("advancement.aether.lance_loot"),
                            Component.translatable("advancement.aether.lance_loot.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("lance_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_LANCE.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "lance_loot"), existingFileHelper);

            Advancement silverDungeon = Advancement.Builder.advancement()
                    .parent(lanceLoot)
                    .display(AetherItems.SILVER_DUNGEON_KEY.get(),
                            Component.translatable("advancement.aether.silver_dungeon"),
                            Component.translatable("advancement.aether.silver_dungeon.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion("kill_valkyrie_queen", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.VALKYRIE_QUEEN.get())))
                    .save(consumer, new ResourceLocation(Aether.MODID, "silver_dungeon"), existingFileHelper);

            Advancement valkyrieLoot = Advancement.Builder.advancement()
                    .parent(silverDungeon)
                    .display(AetherItems.VALKYRIE_HELMET.get(),
                            Component.translatable("advancement.aether.valkyrie_loot"),
                            Component.translatable("advancement.aether.valkyrie_loot.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .requirements(RequirementsStrategy.OR)
                    .addCriterion("valkyrie_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_PICKAXE.get()))
                    .addCriterion("valkyrie_hoe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_HOE.get()))
                    .addCriterion("valkyrie_axe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_AXE.get()))
                    .addCriterion("valkyrie_shovel", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_SHOVEL.get()))
                    .addCriterion("valkyrie_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_HELMET.get()))
                    .addCriterion("valkyrie_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_CHESTPLATE.get()))
                    .addCriterion("valkyrie_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_LEGGINGS.get()))
                    .addCriterion("valkyrie_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_BOOTS.get()))
                    .addCriterion("valkyrie_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_GLOVES.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "valkyrie_loot"), existingFileHelper);

            Advancement valkyrieHoe = Advancement.Builder.advancement()
                    .parent(valkyrieLoot)
                    .display(AetherBlocks.AETHER_FARMLAND.get(),
                            Component.translatable("advancement.aether.valkyrie_hoe"),
                            Component.translatable("advancement.aether.valkyrie_hoe.desc"),
                            null,
                            FrameType.CHALLENGE, true, true, true)
                    .addCriterion("valkyrie_hoe", ItemInteractWithBlockTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(AbilityHooks.ToolHooks.TILLABLES.keySet()).build()), ItemPredicate.Builder.item().of(AetherItems.VALKYRIE_HOE.get())))
                    .save(consumer, new ResourceLocation(Aether.MODID, "valkyrie_hoe"), existingFileHelper);

            Advancement regenStone = Advancement.Builder.advancement()
                    .parent(silverDungeon)
                    .display(AetherItems.REGENERATION_STONE.get(),
                            Component.translatable("advancement.aether.regen_stone"),
                            Component.translatable("advancement.aether.regen_stone.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("regen_stone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.REGENERATION_STONE.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "regen_stone"), existingFileHelper);

            Advancement goldDungeon = Advancement.Builder.advancement()
                    .parent(regenStone)
                    .display(AetherItems.GOLD_DUNGEON_KEY.get(),
                            Component.translatable("advancement.aether.gold_dungeon"),
                            Component.translatable("advancement.aether.gold_dungeon.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion("kill_sun_spirit", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.SUN_SPIRIT.get())))
                    .save(consumer, new ResourceLocation(Aether.MODID, "gold_dungeon"), existingFileHelper);

            Advancement phoenixArmor = Advancement.Builder.advancement()
                    .parent(goldDungeon)
                    .display(AetherItems.PHOENIX_HELMET.get(),
                            Component.translatable("advancement.aether.phoenix_armor"),
                            Component.translatable("advancement.aether.phoenix_armor.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .requirements(RequirementsStrategy.OR)
                    .addCriterion("phoenix_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_HELMET.get()))
                    .addCriterion("phoenix_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_CHESTPLATE.get()))
                    .addCriterion("phoenix_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_LEGGINGS.get()))
                    .addCriterion("phoenix_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_BOOTS.get()))
                    .addCriterion("phoenix_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_GLOVES.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID, "phoenix_armor"), existingFileHelper);

            Advancement obsidianArmor = Advancement.Builder.advancement()
                    .parent(phoenixArmor)
                    .display(AetherItems.OBSIDIAN_CHESTPLATE.get(),
                            Component.translatable("advancement.aether.obsidian_armor"),
                            Component.translatable("advancement.aether.obsidian_armor.desc"),
                            null,
                            FrameType.CHALLENGE, true, true, true)
                    .addCriterion("obsidian_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_HELMET.get()))
                    .addCriterion("obsidian_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_CHESTPLATE.get()))
                    .addCriterion("obsidian_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_LEGGINGS.get()))
                    .addCriterion("obsidian_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_BOOTS.get()))
                    .addCriterion("obsidian_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_GLOVES.get()))
                    .save(consumer, new ResourceLocation(Aether.MODID,"obsidian_armor"), existingFileHelper);

            Advancement aetherSleep = Advancement.Builder.advancement()
                    .parent(goldDungeon)
                    .display(AetherBlocks.SKYROOT_BED.get(),
                            Component.translatable("advancement.aether.aether_sleep"),
                            Component.translatable("advancement.aether.aether_sleep.desc"),
                            null,
                            FrameType.CHALLENGE, true, true, true)
                    .addCriterion("aether_sleep", new PlayerTrigger.TriggerInstance(CriteriaTriggers.SLEPT_IN_BED.getId(),
                            EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().located(LocationPredicate.inDimension(AetherDimensions.AETHER_LEVEL)).build()
                            )))
                    .save(consumer, new ResourceLocation(Aether.MODID, "aether_sleep"), existingFileHelper);
        }
    }
}
