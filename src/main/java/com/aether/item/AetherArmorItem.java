package com.aether.item;

import com.aether.Aether;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class AetherArmorItem extends ArmorItem {
    public AetherArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
        super(materialIn, slot, builderIn);
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return String.format("%s:textures/models/armor/%s_layer_%d.png", Aether.MODID, this.getArmorMaterial().getName(), slot == EquipmentSlotType.LEGS ? 2 : 1);
    }
}
