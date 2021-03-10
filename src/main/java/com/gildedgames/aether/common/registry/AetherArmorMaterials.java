package com.gildedgames.aether.common.registry;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum AetherArmorMaterials implements IArmorMaterial
{
	ZANITE("zanite", 15, new int[] { 2, 5, 6, 2 }, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F,
			() -> Ingredient.of(AetherItems.ZANITE_GEMSTONE.get())),
	GRAVITITE("gravitite", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F,
			() -> Ingredient.of(AetherBlocks.ENCHANTED_GRAVITITE.get())),
	VALKYRIE("valkyrie", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0F,
			() -> Ingredient.EMPTY),
	NEPTUNE("neptune", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0F,
			() -> Ingredient.EMPTY),
	PHOENIX("phoenix", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0F,
			() -> Ingredient.EMPTY),
	OBSIDIAN("obsidian", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0F,
			() -> Ingredient.of(Blocks.OBSIDIAN)),
	SENTRY("sentry", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0F,
			() -> Ingredient.EMPTY);

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyValue<Ingredient> repairMaterial;

	AetherArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = new LazyValue<>(repairMaterial);
	}

	public int getDurabilityForSlot(EquipmentSlotType slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	public int getDefenseForSlot(EquipmentSlotType slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	public int getEnchantmentValue() {
		return this.enchantability;
	}

	public SoundEvent getEquipSound() {
		return this.soundEvent;
	}

	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}

	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return this.name;
	}

	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return 0;
	}
}