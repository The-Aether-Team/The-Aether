package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
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
	ZANITE("zanite", 15, new int[] { 2, 5, 6, 2 }, 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_ZANITE, 0.0F,
			() -> Ingredient.of(AetherItems.ZANITE_GEMSTONE.get())),
	GRAVITITE("gravitite", 33, new int[] { 3, 6, 8, 3 }, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, 2.0F,
			() -> Ingredient.of(AetherBlocks.ENCHANTED_GRAVITITE.get())),
	NEPTUNE("neptune", 33, new int[] { 3, 6, 8, 3 }, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, 0.0F,
			() -> Ingredient.EMPTY),
	PHOENIX("phoenix", 33, new int[] { 3, 6, 8, 3 }, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, 0.0F,
			() -> Ingredient.EMPTY),
	OBSIDIAN("obsidian", 33, new int[] { 3, 6, 8, 3 }, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, 0.0F,
			() -> Ingredient.of(Blocks.OBSIDIAN)),
	VALKYRIE("valkyrie", 33, new int[] { 3, 6, 8, 3 }, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, 0.0F,
			() -> Ingredient.EMPTY),
	SENTRY("sentry", 33, new int[] { 3, 6, 8, 3 }, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_SENTRY, 0.0F,
			() -> Ingredient.EMPTY);

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final Supplier<SoundEvent> soundEvent;
	private final float toughness;
	private final LazyValue<Ingredient> repairMaterial;

	AetherArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
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
		return this.soundEvent.get();
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