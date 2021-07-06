package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.util.EquipmentUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ArmorAbilityListener
{
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
//        else if (EquipmentUtil.hasFullNeptuneSet(entity)) {
//            if (entity.isInWater()) {
//                Vector3d movement = entity.getDeltaMovement();
//                float defaultBoost = 1.25F;
//                float depthStriderModifier = Math.min(EnchantmentHelper.getDepthStrider(entity), 3.0F);
//                if (depthStriderModifier > 0.0F) {
//                    defaultBoost += depthStriderModifier * 0.25F;
//                }
//                entity.moveRelative(defaultBoost, vector);
//
//
//
//                entity.setDeltaMovement(movement.multiply(defaultBoost, 1.0F, defaultBoost));
//            }
//        }
    }

    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (EquipmentUtil.hasFullGravititeSet(entity)) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (!player.isShiftKeyDown()) {
                    player.push(0.0, 1.0, 0.0);
                    if (player instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity) player).connection.send(new SEntityVelocityPacket(player));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getItemBySlot(EquipmentSlotType.FEET).getItem() == AetherItems.SENTRY_BOOTS.get() || EquipmentUtil.hasFullGravititeSet(entity) || EquipmentUtil.hasFullValkyrieSet(entity)) {
            event.setCanceled(true);
        }
    }
}
