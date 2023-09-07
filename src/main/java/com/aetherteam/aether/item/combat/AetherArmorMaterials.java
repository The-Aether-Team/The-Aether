package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum AetherArmorMaterials implements ArmorMaterial {
	ZANITE("zanite", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 2);
		map.put(ArmorItem.Type.LEGGINGS, 5);
		map.put(ArmorItem.Type.CHESTPLATE, 6);
		map.put(ArmorItem.Type.HELMET, 2);
	}), 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_ZANITE, 0.0F, () -> Ingredient.of(AetherTags.Items.ZANITE_REPAIRING)),
	GRAVITITE("gravitite", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 3);
		map.put(ArmorItem.Type.LEGGINGS, 6);
		map.put(ArmorItem.Type.CHESTPLATE, 8);
		map.put(ArmorItem.Type.HELMET, 3);
	}), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, 2.0F, () -> Ingredient.of(AetherTags.Items.GRAVITITE_REPAIRING)),
	NEPTUNE("neptune", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 2);
		map.put(ArmorItem.Type.LEGGINGS, 5);
		map.put(ArmorItem.Type.CHESTPLATE, 6);
		map.put(ArmorItem.Type.HELMET, 2);
	}), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, 1.0F, () -> Ingredient.of(AetherTags.Items.NEPTUNE_REPAIRING)),
	VALKYRIE("valkyrie", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 3);
		map.put(ArmorItem.Type.LEGGINGS, 6);
		map.put(ArmorItem.Type.CHESTPLATE, 8);
		map.put(ArmorItem.Type.HELMET, 3);
	}), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, 2.0F, () -> Ingredient.of(AetherTags.Items.VALKYRIE_REPAIRING)),
	PHOENIX("phoenix", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 3);
		map.put(ArmorItem.Type.LEGGINGS, 6);
		map.put(ArmorItem.Type.CHESTPLATE, 8);
		map.put(ArmorItem.Type.HELMET, 3);
	}), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, 2.0F, () -> Ingredient.of(AetherTags.Items.PHOENIX_REPAIRING)),
	OBSIDIAN("obsidian", 37, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 3);
		map.put(ArmorItem.Type.LEGGINGS, 6);
		map.put(ArmorItem.Type.CHESTPLATE, 8);
		map.put(ArmorItem.Type.HELMET, 3);
	}), 15, AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, 3.0F, () -> Ingredient.of(AetherTags.Items.OBSIDIAN_REPAIRING)),
	SENTRY("sentry", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 2);
		map.put(ArmorItem.Type.LEGGINGS, 5);
		map.put(ArmorItem.Type.CHESTPLATE, 6);
		map.put(ArmorItem.Type.HELMET, 2);
	}), 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_SENTRY, 0.0F, () -> Ingredient.of(AetherTags.Items.SENTRY_REPAIRING));

	private static final EnumMap<ArmorItem.Type, Integer> DURABILITY_MAP = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(ArmorItem.Type.BOOTS, 13);
		map.put(ArmorItem.Type.LEGGINGS, 15);
		map.put(ArmorItem.Type.CHESTPLATE, 16);
		map.put(ArmorItem.Type.HELMET, 11);
	});
	private final String name;
	private final int maxDamageFactor;
	private final EnumMap<ArmorItem.Type, Integer> protectionAmountMap;
	private final int enchantability;
	private final Supplier<SoundEvent> soundEvent;
	private final float toughness;
	private final Supplier<Ingredient> repairMaterial;

	AetherArmorMaterials(String name, int maxDamageFactor, EnumMap<ArmorItem.Type, Integer> protectionAmountMap, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.protectionAmountMap = protectionAmountMap;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = repairMaterial;
	}

	@Override
	public int getDurabilityForType(ArmorItem.Type type) {
		return DURABILITY_MAP.get(type) * this.maxDamageFactor;
	}

	@Override
	public int getDefenseForType(ArmorItem.Type type) {
		return this.protectionAmountMap.get(type);
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