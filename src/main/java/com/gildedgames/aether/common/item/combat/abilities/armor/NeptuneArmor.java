package com.gildedgames.aether.common.item.combat.abilities.armor;

import com.gildedgames.aether.core.util.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

public interface NeptuneArmor {
    static void boostWaterSwimming(LivingEntity entity) {
        if (EquipmentUtil.hasFullNeptuneSet(entity)) {
            if (entity.isInWaterOrBubble()) {
                float defaultBoost = 1.55F;
                float depthStriderModifier = Math.min(EnchantmentHelper.getDepthStrider(entity), 3.0F);
                if (depthStriderModifier > 0.0F) {
                    defaultBoost += depthStriderModifier * 0.15F;
                }
                Vec3 movement = entity.getDeltaMovement().multiply(defaultBoost, 1.0F, defaultBoost);
                entity.move(MoverType.SELF, movement);
            }
        }
    }
}
