package com.gildedgames.aether.common.registry;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum AetherItemTiers implements IItemTier
{
	SKYROOT(0, 59, 2.0F, 0.0F, 15, () -> Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get())),
	HOLYSTONE(1, 131, 4.0F, 1.0F, 5, () -> Ingredient.of(AetherBlocks.HOLYSTONE.get())),
	ZANITE(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(AetherItems.ZANITE_GEMSTONE.get())),
	GRAVITITE(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherBlocks.ENCHANTED_GRAVITITE.get())),
	VALKYRIE(3, 2031, 8.0F, 3.0F, 22, () -> Ingredient.EMPTY);

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairMaterial;

	AetherItemTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyValue<>(repairMaterialIn);
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
