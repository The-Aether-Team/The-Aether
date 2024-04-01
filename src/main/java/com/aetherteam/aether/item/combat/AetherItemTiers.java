package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.AetherTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum AetherItemTiers implements Tier {
    SKYROOT(0, 59, 2.0F, 0.0F, 15, () -> Ingredient.of(AetherTags.Items.SKYROOT_REPAIRING)),
    HOLYSTONE(1, 131, 4.0F, 1.0F, 5, () -> Ingredient.of(AetherTags.Items.HOLYSTONE_REPAIRING)),
    ZANITE(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(AetherTags.Items.ZANITE_REPAIRING)),
    GRAVITITE(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.GRAVITITE_REPAIRING)),
    VALKYRIE(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.VALKYRIE_REPAIRING)),
    FLAMING(3, 502, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.FLAMING_REPAIRING)),
    LIGHTNING(3, 502, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.LIGHTNING_REPAIRING)),
    HOLY(3, 502, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.HOLY_REPAIRING)),
    VAMPIRE(3, 2031, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.VAMPIRE_REPAIRING)),
    PIG_SLAYER(2, 200, 6.0F, 2.0F, 10, () -> Ingredient.of(AetherTags.Items.PIG_SLAYER_REPAIRING)),
    HAMMER_OF_KINGBDOGZ(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(AetherTags.Items.HAMMER_OF_KINGBDOGZ_REPAIRING)),
    CANDY_CANE(0, 32, 12.0F, 0.0F, 22, () -> Ingredient.of(AetherTags.Items.CANDY_CANE_REPAIRING));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    AetherItemTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
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
    public int getLevel() {
        return this.harvestLevel;
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
