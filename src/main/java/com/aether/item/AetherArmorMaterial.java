package com.aether.item;

import com.aether.Aether;
import com.aether.block.AetherBlocks;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum AetherArmorMaterial implements IArmorMaterial {
	ZANITE("zanite", 15, new int[] { 2, 5, 6, 2 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F,
		Ingredient.fromItems(AetherItems.ZANITE_GEMSTONE)),
	GRAVITITE("gravitite", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F,
		Ingredient.fromItems(AetherBlocks.ENCHANTED_GRAVITITE)),
	VALKYRIE("valkyrie", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f,
		Ingredient.EMPTY),
	NEPTUNE("neptune", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f,
		Ingredient.EMPTY),
	PHOENIX("phoenix", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0f,
		Ingredient.EMPTY),
	OBSIDIAN("obsidian", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f,
		Ingredient.fromItems(Blocks.OBSIDIAN)),
	SENTRY("sentry", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f,
		Ingredient.EMPTY);

	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final Ingredient repairMaterial;

	private AetherArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn,
		int enchantabilityIn, SoundEvent equipSoundIn, float p_i48533_8_, Ingredient repairMaterialSupplier) {
		this.name = Aether.MODID + ':' + nameIn;
		this.maxDamageFactor = maxDamageFactorIn;
		this.damageReductionAmountArray = damageReductionAmountsIn;
		this.enchantability = enchantabilityIn;
		this.soundEvent = equipSoundIn;
		this.toughness = p_i48533_8_;
		this.repairMaterial = repairMaterialSupplier;
	}

	@Override
	public int getDurability(EquipmentSlotType slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}
}