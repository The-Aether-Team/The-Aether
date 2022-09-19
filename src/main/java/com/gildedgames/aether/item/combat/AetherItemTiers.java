package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.AetherTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum AetherItemTiers implements Tier {
	SKYROOT(0, 59, 2.0F, 0.0F, 15, () -> Ingredient.of(AetherTags.Items.SKYROOT_REPAIRABLE)),
	HOLYSTONE(1, 131, 4.0F, 1.0F, 5, () -> Ingredient.of(AetherTags.Items.HOLYSTONE_REPAIRABLE)),
	ZANITE(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(AetherTags.Items.ZANITE_REPAIRABLE)),
	GRAVITITE(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherTags.Items.GRAVITITE_REPAIRABLE)),
	VALKYRIE(3, 2031, 9.0F, 4.0F, 15, () -> Ingredient.of(AetherTags.Items.VALKYRIE_REPAIRABLE)),
	FLAMING(3, 502, 9.0F, 4.0F, 15, () -> Ingredient.of(AetherTags.Items.FLAMING_REPAIRABLE)),
	LIGHTNING(3, 502, 9.0F, 4.0F, 15, () -> Ingredient.of(AetherTags.Items.LIGHTNING_REPAIRABLE)),
	HOLY(3, 502, 9.0F, 4.0F, 15, () -> Ingredient.of(AetherTags.Items.HOLY_REPAIRABLE)),
	VAMPIRE(3, 2031, 9.0F, 4.0F, 15, () -> Ingredient.of(AetherTags.Items.VAMPIRE_REPAIRABLE)),
	PIG_SLAYER(2, 200, 6.0F, 2.0F, 15, () -> Ingredient.of(AetherTags.Items.PIG_SLAYER_REPAIRABLE)),
	HAMMER_OF_NOTCH(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(AetherTags.Items.HAMMER_OF_NOTCH_REPAIRABLE)),
	CANDY_CANE(0, 32, 12.0F, 0.0F, 22, () -> Ingredient.of(AetherTags.Items.CANDY_CANE_REPAIRABLE));

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

	@Nonnull
	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}
}
