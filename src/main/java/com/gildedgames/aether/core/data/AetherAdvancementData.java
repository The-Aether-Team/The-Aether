package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.advancement.LoreTrigger;
import com.gildedgames.aether.common.advancement.MountTrigger;
import com.gildedgames.aether.common.registry.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.command.FunctionObject;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class AetherAdvancementData extends AdvancementProvider
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;
    public final List<Consumer<Consumer<Advancement>>> advancements = ImmutableList.of(new RegisterAdvancements());

    public AetherAdvancementData(DataGenerator generatorIn) {
        super(generatorIn);
        this.generator = generatorIn;
    }

    public String getName() {
        return "Aether Advancements";
    }

    public void run(DirectoryCache cache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path path1 = getPath(path, advancement);

                try {
                    IDataProvider.save(GSON, cache, advancement.deconstruct().serializeToJson(), path1);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", path1, ioexception);
                }

            }
        };

        for(Consumer<Consumer<Advancement>> consumer1 : this.advancements) {
            consumer1.accept(consumer);
        }
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/aether/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    public static class RegisterAdvancements implements Consumer<Consumer<Advancement>>
    {
        @Override
        public void accept(Consumer<Advancement> consumer) {
            Advancement enterAether = Advancement.Builder.advancement()
                    .display(Blocks.GLOWSTONE,
                            new TranslationTextComponent("advancement.aether.enter_aether"),
                            new TranslationTextComponent("advancement.aether.enter_aether.desc"),
                            new ResourceLocation(Aether.MODID, "textures/block/dungeon/carved_stone.png"),
                            FrameType.TASK, true, true, false)
                    .addCriterion("enter_aether", ChangeDimensionTrigger.Instance.changedDimensionTo(AetherDimensions.AETHER_WORLD))
                    .rewards(new AdvancementRewards(0, new ResourceLocation[]{AetherLoot.ENTER_AETHER}, new ResourceLocation[0], FunctionObject.CacheableFunction.NONE))
                    .save(consumer, "aether:enter_aether");

            Advancement moreYouKnow = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherItems.BOOK_OF_LORE.get(),
                            new TranslationTextComponent("advancement.aether.read_lore"),
                            new TranslationTextComponent("advancement.aether.read_lore.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("lore_book_entry", LoreTrigger.Instance.forAny())
                    .save(consumer, "aether:read_lore");

            Advancement loreception = Advancement.Builder.advancement()
                    .parent(moreYouKnow)
                    .display(AetherItems.BOOK_OF_LORE.get(),
                            new TranslationTextComponent("advancement.aether.loreception"),
                            new TranslationTextComponent("advancement.aether.loreception.desc"),
                            null,
                            FrameType.TASK, true, true, true)
                    .addCriterion("lore_book_entry", LoreTrigger.Instance.forItem(AetherItems.BOOK_OF_LORE.get()))
                    .save(consumer, "aether:loreception");

            Advancement toInfinityAndBeyond = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherBlocks.BLUE_AERCLOUD.get(),
                            new TranslationTextComponent("advancement.aether.blue_aercloud"),
                            new TranslationTextComponent("advancement.aether.blue_aercloud.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("to_infinity_and_beyond", EnterBlockTrigger.Instance.entersBlock(AetherBlocks.BLUE_AERCLOUD.get()))
                    .save(consumer, "aether:to_infinity_and_beyond");
            Advancement mountPhyg = Advancement.Builder.advancement()
                    .parent(toInfinityAndBeyond)
                    .display(Items.SADDLE,
                            new TranslationTextComponent("advancement.aether.mount_phyg"),
                            new TranslationTextComponent("advancement.aether.mount_phyg.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("mount_phyg", MountTrigger.Instance.forEntity(EntityPredicate.Builder.entity().of(AetherEntityTypes.PHYG.get())))
                    .save(consumer, "aether:mount_phyg");

            Advancement craftIncubator = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherBlocks.INCUBATOR.get(),
                            new TranslationTextComponent("advancement.aether.incubator"),
                            new TranslationTextComponent("advancement.aether.incubator.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("craft_incubator", InventoryChangeTrigger.Instance.hasItems(AetherBlocks.INCUBATOR.get()))
                    .save(consumer, "aether:craft_incubator");

            Advancement craftAltar = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherBlocks.ALTAR.get(),
                            new TranslationTextComponent("advancement.aether.altar"),
                            new TranslationTextComponent("advancement.aether.altar.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("craft_altar", InventoryChangeTrigger.Instance.hasItems(AetherBlocks.ALTAR.get()))
                    .save(consumer, "aether:craft_altar");
            Advancement gravititeTools = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherItems.GRAVITITE_PICKAXE.get(),
                            new TranslationTextComponent("advancement.aether.gravitite_tools"),
                            new TranslationTextComponent("advancement.aether.gravitite_tools.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("gravitite_pickaxe", InventoryChangeTrigger.Instance.hasItems(AetherItems.GRAVITITE_PICKAXE.get()))
                    .addCriterion("gravitite_sword", InventoryChangeTrigger.Instance.hasItems(AetherItems.GRAVITITE_SWORD.get()))
                    .addCriterion("gravitite_axe", InventoryChangeTrigger.Instance.hasItems(AetherItems.GRAVITITE_AXE.get()))
                    .addCriterion("gravitite_shovel", InventoryChangeTrigger.Instance.hasItems(AetherItems.GRAVITITE_SHOVEL.get()))
                    .save(consumer, "aether:gravitite_tools");
        }
    }
}
