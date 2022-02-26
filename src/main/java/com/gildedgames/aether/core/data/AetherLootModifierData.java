package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.loot.modifiers.RemoveSeedsModifier;
import com.gildedgames.aether.common.registry.AetherLootModifiers;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

import javax.annotation.Nonnull;

public class AetherLootModifierData extends GlobalLootModifierProvider {
    public AetherLootModifierData(DataGenerator dataGenerator) {
        super(dataGenerator, Aether.MODID);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Aether Loot Modifiers";
    }

    @Override
    protected void start() {
        add("remove_seeds", AetherLootModifiers.REMOVE_SEEDS.get(), new RemoveSeedsModifier(
                new LootItemCondition[] {
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build()
                })
        );
    }
}
