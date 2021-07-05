package com.gildedgames.aether.core.util;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class EquipmentUtil
{
    public static boolean hasFullGravititeSet(LivingEntity entity) {
        return hasArmorSet(entity, AetherItems.GRAVITITE_HELMET.get(), AetherItems.GRAVITITE_CHESTPLATE.get(), AetherItems.GRAVITITE_LEGGINGS.get(), AetherItems.GRAVITITE_BOOTS.get(), AetherItems.GRAVITITE_GLOVES.get());
    }

    private static boolean hasArmorSet(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots, Item gloves) {
        return entity.getItemBySlot(EquipmentSlotType.HEAD).getItem() == helmet
                && entity.getItemBySlot(EquipmentSlotType.CHEST).getItem() == chestplate
                && entity.getItemBySlot(EquipmentSlotType.LEGS).getItem() == leggings
                && entity.getItemBySlot(EquipmentSlotType.FEET).getItem() == boots
                && CuriosApi.getCuriosHelper().findEquippedCurio(gloves, entity).isPresent();
    }
}
