package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class AetherArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = net.neoforged.neoforge.registries.DeferredRegister.create(Registries.ARMOR_MATERIAL, Aether.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ZANITE = ARMOR_MATERIALS.register("zanite", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_ZANITE, () -> Ingredient.of(AetherTags.Items.ZANITE_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "zanite"))), 0.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GRAVITITE = ARMOR_MATERIALS.register("gravitite", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, () -> Ingredient.of(AetherTags.Items.GRAVITITE_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gravitite"))), 2.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> NEPTUNE = ARMOR_MATERIALS.register("neptune", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, () -> Ingredient.of(AetherTags.Items.NEPTUNE_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "neptune"))), 1.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> VALKYRIE = ARMOR_MATERIALS.register("valkyrie", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, () -> Ingredient.of(AetherTags.Items.VALKYRIE_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "valkyrie"))), 2.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PHOENIX = ARMOR_MATERIALS.register("phoenix", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, () -> Ingredient.of(AetherTags.Items.PHOENIX_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "phoenix"))), 2.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> OBSIDIAN = ARMOR_MATERIALS.register("obsidian", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 15, AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, () -> Ingredient.of(AetherTags.Items.OBSIDIAN_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "obsidian"))), 3.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SENTRY = ARMOR_MATERIALS.register("sentry", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_SENTRY, () -> Ingredient.of(AetherTags.Items.SENTRY_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sentry"))), 0.0F, 0.0F));
}
