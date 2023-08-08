package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.loot.modifiers.*;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class AetherLootModifierData extends GlobalLootModifierProvider {
    public AetherLootModifierData(PackOutput output) {
        super(output, Aether.MODID);
    }

    @Override
    protected void start() {
        this.add("remove_seeds", new RemoveSeedsModifier(
                new LootItemCondition[] {
                        AnyOfCondition.anyOf(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS), LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS)).build(), //TODO
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build()
                })
        );
        this.add("enchanted_grass_berry_bush", new EnchantedGrassModifier(
                new LootItemCondition[] {
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetherBlocks.BERRY_BUSH.get()).build()
                }, new ItemStack(AetherItems.BLUE_BERRY.get())));
        this.add("double_drops", new DoubleDropsModifier(new LootItemCondition[]{ }));
        this.add("pig_drops", new PigDropsModifier(new LootItemCondition[]{ }));
        this.add("gloves_loot_leather", new GlovesLootModifier(new LootItemCondition[]{ }, new ItemStack(AetherItems.LEATHER_GLOVES.get()), ArmorMaterials.LEATHER));
        this.add("gloves_loot_chain", new GlovesLootModifier(new LootItemCondition[]{
                InvertedLootItemCondition.invert(LootTableIdCondition.builder(AetherLoot.GOLD_DUNGEON_REWARD)).build()
        }, new ItemStack(AetherItems.CHAINMAIL_GLOVES.get()), ArmorMaterials.CHAIN));
        this.add("gloves_loot_iron", new GlovesLootModifier(new LootItemCondition[]{
                InvertedLootItemCondition.invert(LootTableIdCondition.builder(AetherLoot.RUINED_PORTAL)).build()
        }, new ItemStack(AetherItems.IRON_GLOVES.get()), ArmorMaterials.IRON));
        this.add("gloves_loot_gold", new GlovesLootModifier(new LootItemCondition[]{ }, new ItemStack(AetherItems.GOLDEN_GLOVES.get()), ArmorMaterials.GOLD));
        this.add("gloves_loot_diamond", new GlovesLootModifier(new LootItemCondition[]{ }, new ItemStack(AetherItems.DIAMOND_GLOVES.get()), ArmorMaterials.DIAMOND));
        this.add("gloves_loot_netherite", new GlovesLootModifier(new LootItemCondition[]{ }, new ItemStack(AetherItems.NETHERITE_GLOVES.get()), ArmorMaterials.NETHERITE));
    }
}