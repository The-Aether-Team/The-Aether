package com.aetherteam.aether.item.accessories.abilities;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.fabric.events.ProjectileEvents;
import com.aetherteam.nitrogen.ConstantsUtil;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.client.player.Input;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public interface ShieldOfRepulsionAccessory {
    /**
     * Cancels {@link ProjectileEvents.OnImpact} and deflects a projectile when it hits an entity wearing a Shield of Repulsion if the projectile can be deflected ({@link AetherTags.Entities#DEFLECTABLE_PROJECTILES}).<br><br>
     * Deflection also depends on the entity not moving. If the entity is a player, it checks this with {@link AetherPlayerAttachment#isMoving()} (which checks if the player is pressing movement keys in {@link com.aetherteam.aether.client.event.hooks.CapabilityClientHooks.AetherPlayerHooks#movementInput(Player, Input)}).<br><br>
     * If the entity isn't a player, it checks for the entity's actual motion.<br><br>
     * For players, deflection also triggers the Shield of Repulsion's screen overlay.
     *
     * @param hitResult  The {@link HitResult} of the projectile.
     * @param projectile The impacting {@link Projectile}.
     * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onProjectileImpact(Projectile, HitResult)
     */
    static boolean deflectProjectile(HitResult hitResult, Projectile projectile) {
        if (hitResult.getType() == HitResult.Type.ENTITY && hitResult instanceof EntityHitResult entityHitResult) {
            if (entityHitResult.getEntity() instanceof LivingEntity impactedLiving) {
                if (projectile.getType().is(AetherTags.Entities.DEFLECTABLE_PROJECTILES)) {
                    SlotEntryReference slotResult = EquipmentUtil.getAccessory(impactedLiving, AetherItems.SHIELD_OF_REPULSION.get());
                    if (slotResult != null) {
                        Vec3 motion = impactedLiving.getDeltaMovement();
                        if (impactedLiving instanceof Player player) {
                            var data = player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER);
                            if (!data.isMoving() || (data.isMoving() && motion.x() == 0.0 && (motion.y() == ConstantsUtil.DEFAULT_DELTA_MOVEMENT_Y || motion.y() == 0.0) && motion.z() == 0.0)) {
                                if (player.level().isClientSide()) { // Values used by the Shield of Repulsion screen overlay vignette.
                                    data.setProjectileImpactedMaximum(150);
                                    data.setProjectileImpactedTimer(150);
                                }
                                return handleDeflection(projectile, player, slotResult);
                            }
                        } else {
                            if (motion.x() == 0.0 && (motion.y() == ConstantsUtil.DEFAULT_DELTA_MOVEMENT_Y || motion.y() == 0.0) && motion.z() == 0.0) {
                                return handleDeflection(projectile, impactedLiving, slotResult);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Handles the event cancellation and the projectile deflection by scaling the projectile's motion by 0.25, and reversing its direction with negative scaling. This scaling is also applied to the projectile's power if its class has power values (which is necessary for certain projectiles like Ghast Fireballs).<br><br>
     * Each deflection takes 1 durability off of the Shield of Repulsion.
     *
     * @param projectile     The impacting {@link Projectile}.
     * @param impactedLiving The impacted {@link LivingEntity}.
     * @param slotResult     The {@link SlotEntryReference} of the Shield of Repulsion.
     */
    private static boolean handleDeflection(Projectile projectile, LivingEntity impactedLiving, SlotEntryReference slotResult) {
        if (!impactedLiving.equals(projectile.getOwner())) {
            projectile.deflect(ProjectileDeflection.REVERSE, impactedLiving, projectile.getOwner(), false);
            projectile.setDeltaMovement(projectile.getDeltaMovement().scale(0.25));
            if (impactedLiving.level() instanceof ServerLevel serverLevel) {
                slotResult.stack().hurtAndBreak(1, serverLevel, impactedLiving instanceof ServerPlayer player ? player : null, (item) -> AccessoriesAPI.breakStack(slotResult.reference()));
            }
        }

        return true;
    }
}
