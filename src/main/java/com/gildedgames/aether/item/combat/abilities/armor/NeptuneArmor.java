package com.gildedgames.aether.item.combat.abilities.armor;

import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public interface NeptuneArmor {
    /**
     * Boosts the entity's movement in water or bubble columns if wearing a full set of Neptune Armor. The default boost is a multiplier of 1.55, but an extra 0.15 is added for every Depth Strider level up to Depth Strider 3.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.gildedgames.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(LivingEvent.LivingTickEvent)
     */
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
