package com.aetherteam.aether.item.accessories.abilities;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.ConstantsUtil;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public interface ShieldOfRepulsionAccessory {
    /**
     * Cancels {@link ProjectileImpactEvent} and deflects a projectile when it hits an entity wearing a Shield of Repulsion if the projectile can be deflected ({@link AetherTags.Entities#DEFLECTABLE_PROJECTILES}).<br><br>
     * Deflection also depends on the entity not moving. If the entity is a player, it checks this with {@link com.aetherteam.aether.capability.player.AetherPlayerCapability#isMoving()} (which checks if the player is pressing movement keys in {@link com.aetherteam.aether.client.event.hooks.CapabilityClientHooks.AetherPlayerHooks#movementInput(Player, Input)}).<br><br>
     * If the entity isn't a player, it checks for the entity's actual motion.<br><br>
     * For players, deflection also triggers the Shield of Repulsion's screen overlay.
     * @param hitResult The {@link HitResult} of the projectile.
     * @param projectile The impacting {@link Projectile}.
     * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onProjectileImpact(ProjectileImpactEvent)
     */
    static void deflectProjectile(ProjectileImpactEvent event, HitResult hitResult, Projectile projectile) {
        if (hitResult.getType() == HitResult.Type.ENTITY && hitResult instanceof EntityHitResult entityHitResult) {
            if (entityHitResult.getEntity() instanceof LivingEntity impactedLiving) {
                if (projectile.getType().is(AetherTags.Entities.DEFLECTABLE_PROJECTILES)) {
                    SlotResult slotResult = EquipmentUtil.getCurio(impactedLiving, AetherItems.SHIELD_OF_REPULSION.get());
                    if (slotResult != null) {
                        Vec3 motion = impactedLiving.getDeltaMovement();
                        if (impactedLiving instanceof Player player) {
                            AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                                if (!aetherPlayer.isMoving()) {
                                    if (player.getLevel().isClientSide()) { // Values used by the Shield of Repulsion screen overlay vignette.
                                        aetherPlayer.setProjectileImpactedMaximum(150);
                                        aetherPlayer.setProjectileImpactedTimer(150);
                                    }
                                    handleDeflection(event, projectile, impactedLiving, slotResult);
                                }
                            });
                        } else {
                            if (motion.x() == 0.0 && (motion.y() == ConstantsUtil.DEFAULT_DELTA_MOVEMENT_Y || motion.y() == 0.0) && motion.z() == 0.0) {
                                handleDeflection(event, projectile, impactedLiving, slotResult);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles the event cancellation and the projectile deflection by scaling the projectile's motion by 0.25, and reversing its direction with negative scaling. This scaling is also applied to the projectile's power if its class has power values (which is necessary for certain projectiles like Ghast Fireballs).<br><br>
     * Each deflection takes 1 durability off of the Shield of Repulsion.
     * @param projectile The impacting {@link Projectile}.
     * @param impactedLiving The impacted {@link LivingEntity}.
     * @param slotResult The {@link SlotResult} of the Shield of Repulsion.
     */
    private static void handleDeflection(ProjectileImpactEvent event, Projectile projectile, LivingEntity impactedLiving, SlotResult slotResult) {
        event.setCanceled(true);
        if (!impactedLiving.equals(projectile.getOwner())) {
            projectile.setDeltaMovement(projectile.getDeltaMovement().scale(-0.25D));
            if (projectile instanceof AbstractHurtingProjectile damagingProjectileEntity) {
                damagingProjectileEntity.xPower *= -0.25D;
                damagingProjectileEntity.yPower *= -0.25D;
                damagingProjectileEntity.zPower *= -0.25D;
            }
            slotResult.stack().hurtAndBreak(1, impactedLiving, (entity) -> CuriosApi.getCuriosHelper().onBrokenCurio(slotResult.slotContext()));
        }
    }
}
