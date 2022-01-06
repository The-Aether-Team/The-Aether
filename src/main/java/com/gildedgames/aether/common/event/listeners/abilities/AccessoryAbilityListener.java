package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.item.accessories.abilities.ZaniteAccessory;
import com.gildedgames.aether.common.item.accessories.gloves.GlovesItem;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber
public class AccessoryAbilityListener
{
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getTarget();
        if (!player.level.isClientSide() && target instanceof LivingEntity livingTarget) {
            if (livingTarget.isAttackable() && !livingTarget.skipAttackInteraction(player)) {
                CuriosApi.getCuriosHelper().findEquippedCurio((stack) -> stack.getItem() instanceof GlovesItem, player).ifPresent((triple) -> triple.getRight().hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND)));
            }
        }
    }

    @SubscribeEvent
    public static void onMiningSpeed(PlayerEvent.BreakSpeed event) {
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.ZANITE_RING.get(), event.getPlayer()).ifPresent((triple) -> ZaniteAccessory.handleMiningSpeed(event, triple));
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.ZANITE_PENDANT.get(), event.getPlayer()).ifPresent((triple) -> ZaniteAccessory.handleMiningSpeed(event, triple));
    }

    @SubscribeEvent
    public static void onTargetSet(LivingEvent.LivingVisibilityEvent event) {
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.INVISIBILITY_CLOAK.get(), event.getEntityLiving()).ifPresent((triple) -> event.modifyVisibility(0.0D));
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY) {
            Entity impactedEntity = ((EntityHitResult) event.getRayTraceResult()).getEntity();
            if (impactedEntity instanceof LivingEntity impactedLiving && event.getEntity() instanceof Projectile projectile) {
                if (projectile.getType().is(AetherTags.Entities.DEFLECTABLE_PROJECTILES)) {
                    CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.REPULSION_SHIELD.get(), impactedLiving).ifPresent((triple) -> {
                        Vec3 motion = impactedLiving.getDeltaMovement();
                        if (!impactedLiving.level.isClientSide) {
                            if (impactedLiving instanceof Player player) {
                                IAetherPlayer.get(player).ifPresent(aetherPlayer -> {
                                    if (!aetherPlayer.isMoving()) {
                                        aetherPlayer.setProjectileImpactedMaximum(150);
                                        aetherPlayer.setProjectileImpactedTimer(150);
                                        handleDeflection(event, projectile, impactedLiving, triple);
                                    }
                                });
                            } else {
                                if (motion.x() == 0.0 && (motion.y() == -0.0784000015258789 || motion.y() == 0.0) && motion.z() == 0.0) {
                                    handleDeflection(event, projectile, impactedLiving, triple);
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private static void handleDeflection(ProjectileImpactEvent event, Projectile projectile, LivingEntity impactedLiving, ImmutableTriple<String, Integer, ItemStack> triple) {
        event.setCanceled(true);
        projectile.setDeltaMovement(projectile.getDeltaMovement().scale(-0.25D));
        if (projectile instanceof AbstractHurtingProjectile damagingProjectileEntity) {
            damagingProjectileEntity.xPower *= -0.25D;
            damagingProjectileEntity.yPower *= -0.25D;
            damagingProjectileEntity.zPower *= -0.25D;
        }
        triple.getRight().hurtAndBreak(1, impactedLiving, (entity) -> CuriosApi.getCuriosHelper().onBrokenCurio(triple.getLeft(), triple.getMiddle(), entity));
    }
}
