package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.AetherTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum AetherItemTiers implements Tier {
    SKYROOT(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 2.0F, 0.0F, 15, () -> Ingredient.of(AetherTags.Items.SKYROOT_REPAIRING)),
    HOLYSTONE(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, () -> Ingredient.of(AetherTags.Items.HOLYSTONE_REPAIRING)),
    ZANITE(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(AetherTags.Items.ZANITE_REPAIRING)),
    GRAVITITE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.GRAVITITE_REPAIRING)),
    VALKYRIE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.VALKYRIE_REPAIRING)),
    FLAMING(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 502, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.FLAMING_REPAIRING)),
    LIGHTNING(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 502, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.LIGHTNING_REPAIRING)),
    HOLY(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 502, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.HOLY_REPAIRING)),
    VAMPIRE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.VAMPIRE_REPAIRING)),
    PIG_SLAYER(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 6.0F, 2.0F, 10, () -> Ingredient.of(AetherTags.Items.PIG_SLAYER_REPAIRING)),
    HAMMER_OF_KINGBDOGZ(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(AetherTags.Items.HAMMER_OF_KINGBDOGZ_REPAIRING)),
    CANDY_CANE(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 32, 12.0F, 0.0F, 22, () -> Ingredient.of(AetherTags.Items.CANDY_CANE_REPAIRING));

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    AetherItemTiers(TagKey<Block> incorrectBlocksForDrops, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
