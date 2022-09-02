package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.advancement.LoreTrigger;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.data.resources.AetherDimensions;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.commands.CommandFunction;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class AetherAdvancementData extends AdvancementProvider
{
    public final List<Consumer<Consumer<Advancement>>> advancements = ImmutableList.of(new AetherAdvancements());

    public AetherAdvancementData(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Aether Advancements";
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        for (Consumer<Consumer<Advancement>> consumer1 : this.advancements) {
            consumer1.accept(consumer);
        }
    }

    public static class AetherAdvancements implements Consumer<Consumer<Advancement>>
    {
        @Override
        public void accept(Consumer<Advancement> consumer) {
            Advancement enterAether = Advancement.Builder.advancement()
                    .display(Blocks.GLOWSTONE,
                            Component.translatable("advancement.aether.enter_aether"),
                            Component.translatable("advancement.aether.enter_aether.desc"),
                            new ResourceLocation(Aether.MODID, "textures/block/dungeon/carved_stone.png"),
                            FrameType.TASK, true, true, false)
                    .addCriterion("enter_aether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherDimensions.AETHER_LEVEL))
                    .rewards(new AdvancementRewards(0, new ResourceLocation[]{AetherLoot.ENTER_AETHER}, new ResourceLocation[0], CommandFunction.CacheableFunction.NONE))
                    .save(consumer, "aether:enter_aether");

            Advancement moreYouKnow = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherItems.BOOK_OF_LORE.get(),
                            Component.translatable("advancement.aether.read_lore"),
                            Component.translatable("advancement.aether.read_lore.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("lore_book_entry", LoreTrigger.Instance.forAny())
                    .save(consumer, "aether:read_lore");
            Advancement loreception = Advancement.Builder.advancement()
                    .parent(moreYouKnow)
                    .display(AetherItems.BOOK_OF_LORE.get(),
                            Component.translatable("advancement.aether.loreception"),
                            Component.translatable("advancement.aether.loreception.desc"),
                            null,
                            FrameType.TASK, true, true, true)
                    .addCriterion("lore_book_entry", LoreTrigger.Instance.forItem(AetherItems.BOOK_OF_LORE.get()))
                    .save(consumer, "aether:loreception");

            Advancement toInfinityAndBeyond = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherBlocks.BLUE_AERCLOUD.get(),
                            Component.translatable("advancement.aether.blue_aercloud"),
                            Component.translatable("advancement.aether.blue_aercloud.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("to_infinity_and_beyond", EnterBlockTrigger.TriggerInstance.entersBlock(AetherBlocks.BLUE_AERCLOUD.get()))
                    .save(consumer, "aether:to_infinity_and_beyond");
            Advancement mountPhyg = Advancement.Builder.advancement()
                    .parent(toInfinityAndBeyond)
                    .display(Items.SADDLE,
                            Component.translatable("advancement.aether.mount_phyg"),
                            Component.translatable("advancement.aether.mount_phyg.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("mount_phyg", StartRidingTrigger.TriggerInstance.playerStartsRiding(EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity().of(AetherEntityTypes.PHYG.get()).build())))
                    .save(consumer, "aether:mount_phyg");

            Advancement craftIncubator = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherBlocks.INCUBATOR.get(),
                            Component.translatable("advancement.aether.incubator"),
                            Component.translatable("advancement.aether.incubator.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("craft_incubator", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.INCUBATOR.get()))
                    .save(consumer, "aether:craft_incubator");

            Advancement craftAltar = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherBlocks.ALTAR.get(),
                            Component.translatable("advancement.aether.altar"),
                            Component.translatable("advancement.aether.altar.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("craft_altar", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ALTAR.get()))
                    .save(consumer, "aether:craft_altar");
            Advancement gravititeTools = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherItems.GRAVITITE_PICKAXE.get(),
                            Component.translatable("advancement.aether.gravitite_tools"),
                            Component.translatable("advancement.aether.gravitite_tools.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .requirements(RequirementsStrategy.OR)
                    .addCriterion("gravitite_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_PICKAXE.get()))
                    .addCriterion("gravitite_sword", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_SWORD.get()))
                    .addCriterion("gravitite_axe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_AXE.get()))
                    .addCriterion("gravitite_shovel", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_SHOVEL.get()))
                    .save(consumer, "aether:gravitite_tools");

            Advancement bronzeDungeon = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherItems.BRONZE_DUNGEON_KEY.get(),
                            Component.translatable("advancement.aether.bronze_dungeon"),
                            Component.translatable("advancement.aether.bronze_dungeon.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion("kill_slider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.SLIDER.get())))
                    .save(consumer, "aether:bronze_dungeon");
            Advancement silverDungeon = Advancement.Builder.advancement()
                    .parent(bronzeDungeon)
                    .display(AetherItems.SILVER_DUNGEON_KEY.get(),
                            Component.translatable("advancement.aether.silver_dungeon"),
                            Component.translatable("advancement.aether.silver_dungeon.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion("kill_valkyrie_queen", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.VALKYRIE_QUEEN.get())))
                    .save(consumer, "aether:silver_dungeon");
            Advancement goldDungeon = Advancement.Builder.advancement()
                    .parent(silverDungeon)
                    .display(AetherItems.GOLD_DUNGEON_KEY.get(),
                            Component.translatable("advancement.aether.gold_dungeon"),
                            Component.translatable("advancement.aether.gold_dungeon.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion("kill_sun_spirit", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.SUN_SPIRIT.get())))
                    .save(consumer, "aether:gold_dungeon");
        }
    }
}
