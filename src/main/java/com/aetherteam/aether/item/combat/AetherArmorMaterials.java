package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum AetherArmorMaterials implements ArmorMaterial {
	ZANITE("zanite", 15, new int[]{2, 5, 6, 2}, 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_ZANITE, 0.0F, () -> Ingredient.of(AetherTags.Items.ZANITE_REPAIRING)),
	GRAVITITE("gravitite", 33, new int[]{3, 6, 8, 3}, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, 2.0F, () -> Ingredient.of(AetherTags.Items.GRAVITITE_REPAIRING)),
	NEPTUNE("neptune", 15, new int[]{2, 5, 6, 2}, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, 1.0F, () -> Ingredient.of(AetherTags.Items.NEPTUNE_REPAIRING)),
	VALKYRIE("valkyrie", 33, new int[]{3, 6, 8, 3}, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, 2.0F, () -> Ingredient.of(AetherTags.Items.VALKYRIE_REPAIRING)),
	PHOENIX("phoenix", 33, new int[]{3, 6, 8, 3}, 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, 2.0F, () -> Ingredient.of(AetherTags.Items.PHOENIX_REPAIRING)),
	OBSIDIAN("obsidian", 37, new int[]{3, 6, 8, 3}, 15, AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, 3.0F, () -> Ingredient.of(AetherTags.Items.OBSIDIAN_REPAIRING)),
	SENTRY("sentry", 15,  new int[]{2, 5, 6, 2}, 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_SENTRY, 0.0F, () -> Ingredient.of(AetherTags.Items.SENTRY_REPAIRING));

	private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] slotProtections;
	private final int enchantability;
	private final Supplier<SoundEvent> soundEvent;
	private final float toughness;
	private final Supplier<Ingredient> repairMaterial;

	AetherArmorMaterials(String name, int maxDamageFactor, int[] slotProtections, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.slotProtections = slotProtections;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = repairMaterial;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slot) {
		return HEALTH_PER_SLOT[slot.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slot) {
		return this.slotProtections[slot.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.soundEvent.get();
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}

	@Override
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