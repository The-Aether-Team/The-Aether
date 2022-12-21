package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.loot.modifiers.RemoveSeedsModifier;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class AetherLootModifierData extends GlobalLootModifierProvider {
    public AetherLootModifierData(PackOutput output) {
        super(output, Aether.MODID);
    }

    @Override
    protected void start() {
        add("remove_seeds", new RemoveSeedsModifier(
                new LootItemCondition[] {
                        AlternativeLootItemCondition.alternative(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS), LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS)).build(),
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build()
                })
        );
    }
}
