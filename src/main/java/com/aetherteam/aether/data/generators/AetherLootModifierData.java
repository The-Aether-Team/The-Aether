package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.modifiers.DoubleDropsModifier;
import com.aetherteam.aether.loot.modifiers.EnchantedGrassModifier;
import com.aetherteam.aether.loot.modifiers.PigDropsModifier;
import com.aetherteam.aether.loot.modifiers.RemoveSeedsModifier;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
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
        this.add("remove_seeds", new RemoveSeedsModifier(
                new LootItemCondition[] {
                        AlternativeLootItemCondition.alternative(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS), LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS)).build(),
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build()
                })
        );
        this.add("enchanted_grass_berry_bush", new EnchantedGrassModifier(
                new LootItemCondition[] {
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetherBlocks.BERRY_BUSH.get()).build()
                }, new ItemStack(AetherItems.BLUE_BERRY.get())));
        this.add("double_drops", new DoubleDropsModifier(new LootItemCondition[]{ }));
        this.add("pig_drops", new PigDropsModifier(new LootItemCondition[]{ }));
    }
}
