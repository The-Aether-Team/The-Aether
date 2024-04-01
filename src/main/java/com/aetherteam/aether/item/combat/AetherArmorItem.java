package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.Aether;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class AetherArmorItem extends ArmorItem {
    public AetherArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

    /**
     * Formats the resource path of the armor texture with the mod id replacing the first %s, the material name replacing the next %s, and whether the slot is legs or not replacing the last %s with a number 1 or 2.
     *
     * @param stack  The armor {@link ItemStack}.
     * @param entity The {@link Entity} wearing the armor.
     * @param slot   The {@link EquipmentSlot} the armor is in.
     * @param type   A {@link String} type, either null or "overlay" if this is called to render an armor overlay, like for colored textures.
     * @return The resource path of the armor texture as a {@link String}.
     */
    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return String.format("%s:textures/models/armor/%s_layer_%s.png", Aether.MODID, this.getMaterial().getName(), slot == EquipmentSlot.LEGS ? 2 : 1);
    }
}
