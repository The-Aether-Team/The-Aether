package com.aetherteam.aether.item.combat.abilities.armor;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public interface NeptuneArmor {
    /**
     * Boosts the entity's movement in water or bubble columns if wearing a full set of Neptune Armor. The default boost is modified based on duration in water and whether the boots have Depth Strider.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(LivingEvent.LivingTickEvent)
     */
    static void boostWaterSwimming(LivingEntity entity) {
        if (EquipmentUtil.hasFullNeptuneSet(entity)) {
            if (entity.isInWaterOrBubble()) {
                if (entity instanceof Player player) {
                    AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                        Player innerPlayer = aetherPlayer.getPlayer();
                        float defaultBoost = boostWithDepthStrider(innerPlayer);
                        aetherPlayer.setNeptuneSubmergeLength(Math.min(aetherPlayer.getNeptuneSubmergeLength() + 0.1, 1.0));
                        defaultBoost *= aetherPlayer.getNeptuneSubmergeLength();
                        innerPlayer.moveRelative(0.04F * defaultBoost, new Vec3(innerPlayer.xxa, innerPlayer.yya, innerPlayer.zza));
                        if (innerPlayer.isSwimming() || innerPlayer.getDeltaMovement().y() > 0 || innerPlayer.isCrouching()) {
                            innerPlayer.move(MoverType.SELF, innerPlayer.getDeltaMovement().multiply(0.0, defaultBoost, 0.0));
                        }
                    });
                } else {
                    float defaultBoost = boostWithDepthStrider(entity);
                    entity.moveRelative(0.04F * defaultBoost, new Vec3(entity.xxa, entity.yya, entity.zza));
                    if (entity.isSwimming() || entity.getDeltaMovement().y() > 0 || entity.isCrouching()) {
                        entity.move(MoverType.SELF, entity.getDeltaMovement().multiply(0.0, defaultBoost, 0.0));
                    }
                }
            }
        }
        if (!EquipmentUtil.hasFullNeptuneSet(entity) || !entity.isInWaterOrBubble()) {
            if (entity instanceof Player player) {
                AetherPlayer.get(player).ifPresent((aetherPlayer) -> aetherPlayer.setNeptuneSubmergeLength(0.0));
            }
        }
    }

    /**
     * Adds an extra 0.15 to the boost for every Depth Strider level up to Depth Strider 3.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @return The modified boost as a {@link Float}.
     */
    private static float boostWithDepthStrider(LivingEntity entity) {
        float defaultBoost = 0.4F;
        float depthStriderModifier = Math.min(EnchantmentHelper.getDepthStrider(entity), 3.0F);
        if (depthStriderModifier > 0.0F) {
            defaultBoost += depthStriderModifier * 0.4F;
        }
        if (entity.isSwimming() && entity.getDeltaMovement().y() < 0) {
            defaultBoost *= 0.5;
        }
        return defaultBoost;
    }
}
