package com.gildedgames.aether.common.item.accessories.misc;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class GoldenFeatherItem extends AccessoryItem
{
    public GoldenFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        //Vector3d motion = livingEntity.getDeltaMovement();

        if (livingEntity.getDeltaMovement().y < 0.0 && !livingEntity.isOnGround() && !livingEntity.isInWater() && !livingEntity.isShiftKeyDown()) {
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }

        livingEntity.fallDistance = 0.0F;
    }
}
