package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.Aether;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

import net.minecraft.item.Item.Properties;

public class AetherArmorItem extends ArmorItem
{
    public AetherArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
        super(materialIn, slot, builderIn);
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return String.format("%s:textures/models/armor/%s_layer_%d.png", Aether.MODID, this.getMaterial().getName(), slot == EquipmentSlotType.LEGS ? 2 : 1);
    }
}
