package com.gildedgames.aether.core.util;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class EquipmentUtil
{
    public static boolean hasFullGravititeSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.GRAVITITE_HELMET.get(), AetherItems.GRAVITITE_CHESTPLATE.get(), AetherItems.GRAVITITE_LEGGINGS.get(), AetherItems.GRAVITITE_BOOTS.get(), AetherItems.GRAVITITE_GLOVES.get());
    }

    public static boolean hasFullValkyrieSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.VALKYRIE_HELMET.get(), AetherItems.VALKYRIE_CHESTPLATE.get(), AetherItems.VALKYRIE_LEGGINGS.get(), AetherItems.VALKYRIE_BOOTS.get(), AetherItems.VALKYRIE_GLOVES.get());
    }

    public static boolean hasFullNeptuneSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.NEPTUNE_HELMET.get(), AetherItems.NEPTUNE_CHESTPLATE.get(), AetherItems.NEPTUNE_LEGGINGS.get(), AetherItems.NEPTUNE_BOOTS.get(), AetherItems.NEPTUNE_GLOVES.get());
    }

    public static boolean hasFullPhoenixSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.PHOENIX_HELMET.get(), AetherItems.PHOENIX_CHESTPLATE.get(), AetherItems.PHOENIX_LEGGINGS.get(), AetherItems.PHOENIX_BOOTS.get(), AetherItems.PHOENIX_GLOVES.get());
    }

    public static boolean hasFullObsidianSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.OBSIDIAN_HELMET.get(), AetherItems.OBSIDIAN_CHESTPLATE.get(), AetherItems.OBSIDIAN_LEGGINGS.get(), AetherItems.OBSIDIAN_BOOTS.get(), AetherItems.OBSIDIAN_GLOVES.get());
    }

    private static boolean hasArmorSet(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots, Item gloves) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == helmet
                && entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == chestplate
                && entity.getItemBySlot(EquipmentSlot.LEGS).getItem() == leggings
                && entity.getItemBySlot(EquipmentSlot.FEET).getItem() == boots
                && CuriosApi.getCuriosHelper().findFirstCurio(entity, gloves).isPresent();
    }
}
