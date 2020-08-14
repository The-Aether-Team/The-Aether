package com.aether.item;

import com.aether.block.AetherBlocks;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.function.Supplier;

public enum AetherArmorMaterial implements IArmorMaterial {
	ZANITE("zanite", 15, new int[] { 2, 5, 6, 2 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F,
			() -> Ingredient.fromItems(AetherItems.ZANITE_GEMSTONE)),
	GRAVITITE("gravitite", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F,
			() -> Ingredient.fromItems(AetherBlocks.ENCHANTED_GRAVITITE)),
	VALKYRIE("valkyrie", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f,
			() -> Ingredient.EMPTY),
	NEPTUNE("neptune", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f,
			() -> Ingredient.EMPTY),
	PHOENIX("phoenix", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0f,
			() -> Ingredient.EMPTY),
	OBSIDIAN("obsidian", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f,
			() -> Ingredient.fromItems(Blocks.OBSIDIAN)),
	SENTRY("sentry", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f,
			() -> Ingredient.EMPTY);

	private static final int[] durability_arr = new int[]{13, 15, 16, 11};
	private final String armorName;
	private final int durabilityFactor;
	private final int[] damageReduction;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float armorToughness;
	private final Supplier<Ingredient> repairMaterial;

	AetherArmorMaterial(String name, int durability, int[] reduction, int enchant, SoundEvent sound, float toughness, Supplier<Ingredient> material) {
		armorName = name;
		durabilityFactor = durability;
		damageReduction = reduction;
		enchantability = enchant;
		equipSound = sound;
		armorToughness = toughness;
		repairMaterial = material;
	}

	@Override
	public int getDurability(EquipmentSlotType slotIn) {
		return durability_arr[slotIn.getIndex()] * durabilityFactor;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return damageReduction[slotIn.getIndex()];
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public SoundEvent getSoundEvent() {
		return equipSound;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return repairMaterial.get();
	}

	@Override
	public String getName() {
		return armorName;
	}

	@Override
	public float getToughness() {
		return armorToughness;
	}

	@Override
	public float func_230304_f_() { //knockback resistance (like netherite), we probably dont need to use it so its set to 0 all the time
		return 0;
	}
}