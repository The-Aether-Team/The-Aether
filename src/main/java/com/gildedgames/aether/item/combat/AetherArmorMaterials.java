package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.client.AetherSoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum AetherArmorMaterials implements ArmorMaterial {
	ZANITE("zanite", 15, new int[] { 2, 5, 6, 2 }, 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_ZANITE, 0.0F, () -> Ingredient.of(AetherTags.Items.ZANITE_REPAIRABLE)),
	GRAVITITE("gravitite", 33, new int[] { 3, 6, 8, 3 }, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, 2.0F, () -> Ingredient.of(AetherTags.Items.GRAVITITE_REPAIRABLE)),
	VALKYRIE("valkyrie", 37, new int[] { 3, 6, 8, 3 }, 15, AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, 3.0F, () -> Ingredient.of(AetherTags.Items.VALKYRIE_REPAIRABLE)),
	NEPTUNE("neptune", 37, new int[] { 3, 6, 8, 3 }, 15, AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, 3.0F, () -> Ingredient.of(AetherTags.Items.NEPTUNE_REPAIRABLE)),
	PHOENIX("phoenix", 37, new int[] { 3, 6, 8, 3 }, 15, AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, 3.0F, () -> Ingredient.of(AetherTags.Items.PHOENIX_REPAIRABLE)),
	OBSIDIAN("obsidian", 37, new int[] { 3, 6, 8, 3 }, 15, AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, 3.0F, () -> Ingredient.of(AetherTags.Items.OBSIDIAN_REPAIRABLE)),
	SENTRY("sentry", 37, new int[] { 3, 6, 8, 3 }, 15, AetherSoundEvents.ITEM_ARMOR_EQUIP_SENTRY, 3.0F, () -> Ingredient.of(AetherTags.Items.SENTRY_REPAIRABLE));

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final Supplier<SoundEvent> soundEvent;
	private final float toughness;
	private final Supplier<Ingredient> repairMaterial;

	AetherArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = repairMaterial;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slot) {
		return MAX_DAMAGE_ARRAY[slot.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slot) {
		return this.damageReductionAmountArray[slot.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Nonnull
	@Override
	public SoundEvent getEquipSound() {
		return this.soundEvent.get();
	}

	@Nonnull
	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}

	@Nonnull
	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return 0;
	}
}