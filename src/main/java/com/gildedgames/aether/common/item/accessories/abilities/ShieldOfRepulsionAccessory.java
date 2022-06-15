package com.gildedgames.aether.common.item.accessories.abilities;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.util.ConstantsUtil;
import net.minecraft.world.entity.Entity;
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
    static void deflectProjectile(ProjectileImpactEvent event, HitResult hitResult, Projectile projectile) {
        if (hitResult.getType() == HitResult.Type.ENTITY && hitResult instanceof EntityHitResult entityHitResult) {
            Entity impactedEntity = entityHitResult.getEntity();
            if (impactedEntity instanceof LivingEntity impactedLiving) {
                if (projectile.getType().is(AetherTags.Entities.DEFLECTABLE_PROJECTILES)) {
                    CuriosApi.getCuriosHelper().findFirstCurio(impactedLiving, AetherItems.SHIELD_OF_REPULSION.get()).ifPresent((slotResult) -> {
                        Vec3 motion = impactedLiving.getDeltaMovement();
                        if (impactedLiving instanceof Player player) {
                            AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                                if (!aetherPlayer.isMoving()) {
                                    if (!player.level.isClientSide()) {
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
                    });
                }
            }
        }
    }

    private static void handleDeflection(ProjectileImpactEvent event, Projectile projectile, LivingEntity impactedLiving, SlotResult slotResult) {
        event.setCanceled(true);
        projectile.setDeltaMovement(projectile.getDeltaMovement().scale(-0.25D));
        if (projectile instanceof AbstractHurtingProjectile damagingProjectileEntity) {
            damagingProjectileEntity.xPower *= -0.25D;
            damagingProjectileEntity.yPower *= -0.25D;
            damagingProjectileEntity.zPower *= -0.25D;
        }
        slotResult.stack().hurtAndBreak(1, impactedLiving, (entity) -> CuriosApi.getCuriosHelper().onBrokenCurio(slotResult.slotContext()));
    }
}
