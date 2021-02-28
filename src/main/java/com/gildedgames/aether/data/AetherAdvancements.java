package com.gildedgames.aether.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.advancement.LoreTrigger;
import com.gildedgames.aether.advancement.MountTrigger;
import com.gildedgames.aether.registry.AetherBlocks;
import com.gildedgames.aether.registry.AetherDimensions;
import com.gildedgames.aether.registry.AetherEntityTypes;
import com.gildedgames.aether.registry.AetherItems;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
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

public class AetherAdvancements extends AdvancementProvider
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;
    public final List<Consumer<Consumer<Advancement>>> advancements = ImmutableList.of(new RegisterAdvancements());

    public AetherAdvancements(DataGenerator generatorIn) {
        super(generatorIn);
        this.generator = generatorIn;
    }

    public String getName() {
        return "Aether Advancements";
    }

    public void act(DirectoryCache cache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path path1 = getPath(path, advancement);

                try {
                    IDataProvider.save(GSON, cache, advancement.copy().serialize(), path1);
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
            Advancement enterAether = Advancement.Builder.builder()
                    .withDisplay(Blocks.GLOWSTONE,
                            new TranslationTextComponent("advancement.aether.enter_aether"),
                            new TranslationTextComponent("advancement.aether.enter_aether.desc"),
                            new ResourceLocation(Aether.MODID, "textures/block/dungeon/carved_stone.png"),
                            FrameType.TASK, true, true, false)
                    .withCriterion("enter_aether", ChangeDimensionTrigger.Instance.toWorld(AetherDimensions.AETHER_WORLD))
                    .register(consumer, "aether:enter_aether");
            //TODO: add enter_aether loot.

            Advancement loreception = Advancement.Builder.builder()
                    .withParent(enterAether)
                    .withDisplay(AetherItems.BOOK_OF_LORE.get(),
                            new TranslationTextComponent("advancement.aether.loreception"),
                            new TranslationTextComponent("advancement.aether.loreception.desc"),
                            null,
                            FrameType.TASK, true, true, true)
                    .withCriterion("lore_book_entry", LoreTrigger.Instance.forItem(AetherItems.BOOK_OF_LORE.get()))
                    .register(consumer, "aether:loreception");

            Advancement toInfinityAndBeyond = Advancement.Builder.builder()
                    .withParent(enterAether)
                    .withDisplay(AetherBlocks.BLUE_AERCLOUD.get(),
                            new TranslationTextComponent("advancement.aether.blue_aercloud"),
                            new TranslationTextComponent("advancement.aether.blue_aercloud.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .withCriterion("to_infinity_and_beyond", EnterBlockTrigger.Instance.forBlock(AetherBlocks.BLUE_AERCLOUD.get()))
                    .register(consumer, "aether:to_infinity_and_beyond");
            Advancement mountPhyg = Advancement.Builder.builder()
                    .withParent(toInfinityAndBeyond)
                    .withDisplay(Items.SADDLE,
                            new TranslationTextComponent("advancement.aether.mount_phyg"),
                            new TranslationTextComponent("advancement.aether.mount_phyg.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .withCriterion("mount_phyg", MountTrigger.Instance.forEntity(EntityPredicate.Builder.create().type((AetherEntityTypes.PHYG.get()))))
                    .register(consumer, "aether:mount_phyg");

            Advancement craftIncubator = Advancement.Builder.builder()
                    .withParent(enterAether)
                    .withDisplay(AetherBlocks.INCUBATOR.get(),
                            new TranslationTextComponent("advancement.aether.incubator"),
                            new TranslationTextComponent("advancement.aether.incubator.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .withCriterion("craft_incubator", InventoryChangeTrigger.Instance.forItems(AetherBlocks.INCUBATOR.get()))
                    .register(consumer, "aether:craft_incubator");

            Advancement craftAltar = Advancement.Builder.builder()
                    .withParent(enterAether)
                    .withDisplay(AetherBlocks.ALTAR.get(),
                            new TranslationTextComponent("advancement.aether.altar"),
                            new TranslationTextComponent("advancement.aether.altar.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .withCriterion("craft_altar", InventoryChangeTrigger.Instance.forItems(AetherBlocks.ALTAR.get()))
                    .register(consumer, "aether:craft_altar");
            Advancement gravititeTools = Advancement.Builder.builder()
                    .withParent(craftAltar)
                    .withDisplay(AetherItems.GRAVITITE_PICKAXE.get(),
                            new TranslationTextComponent("advancement.aether.gravitite_tools"),
                            new TranslationTextComponent("advancement.aether.gravitite_tools.desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("gravitite_pickaxe", InventoryChangeTrigger.Instance.forItems(AetherItems.GRAVITITE_PICKAXE.get()))
                    .withCriterion("gravitite_sword", InventoryChangeTrigger.Instance.forItems(AetherItems.GRAVITITE_SWORD.get()))
                    .withCriterion("gravitite_axe", InventoryChangeTrigger.Instance.forItems(AetherItems.GRAVITITE_AXE.get()))
                    .withCriterion("gravitite_shovel", InventoryChangeTrigger.Instance.forItems(AetherItems.GRAVITITE_SHOVEL.get()))
                    .register(consumer, "aether:gravitite_tools");
        }
    }
}
