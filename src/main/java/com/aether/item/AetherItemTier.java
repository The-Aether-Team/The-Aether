package com.aether.item;

import java.util.function.Supplier;

import com.aether.block.AetherBlocks;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyLoadBase;

public enum AetherItemTier implements IItemTier {
	SKYROOT(0, 59, 2.0f, 0.0f, 15, () -> Ingredient.fromTag(ItemTags.PLANKS)),
	HOLYSTONE(1, 131, 4.0f, 1.0f, 5, () -> Ingredient.fromItems(AetherBlocks.HOLYSTONE)),
	ZANITE(2, 250, 6.0f, 2.0f, 14, () -> Ingredient.fromItems(AetherItems.ZANITE_GEMSTONE)),
	GRAVITITE(3, 1561, 8.0f, 3.0f, 10, () -> Ingredient.fromItems(AetherBlocks.ENCHANTED_GRAVITITE)),
	VALKYRIE(3, 1561, 8.0f, 3.0f, 22, () -> Ingredient.EMPTY);

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyLoadBase<Ingredient> repairMaterial;

	private AetherItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn,
		int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyLoadBase<>(repairMaterialIn);
	}

	@Override
	public int getMaxUses() {
		return this.maxUses;
	}

	@Override
	public float getEfficiency() {
		return this.efficiency;
	}

	@Override
	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}
}
