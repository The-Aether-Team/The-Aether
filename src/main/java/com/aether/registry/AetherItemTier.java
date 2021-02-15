package com.aether.registry;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum AetherItemTier implements IItemTier
{
	SKYROOT(0, 59, 2.0F, 0.0F, 15, () -> Ingredient.fromTag(ItemTags.PLANKS)),
	HOLYSTONE(1, 131, 4.0F, 1.0F, 5, () -> Ingredient.fromItems(AetherBlocks.HOLYSTONE.get())),
	ZANITE(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.fromItems(AetherItems.ZANITE_GEMSTONE.get())),
	GRAVITITE(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.fromItems(AetherBlocks.ENCHANTED_GRAVITITE.get())),
	VALKYRIE(3, 1561, 8.0F, 3.0F, 22, () -> Ingredient.EMPTY);

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairMaterial;

	AetherItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyValue<>(repairMaterialIn);
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
