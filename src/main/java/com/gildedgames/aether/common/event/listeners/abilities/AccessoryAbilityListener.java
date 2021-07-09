package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.item.accessories.abilities.IZaniteAccessory;
import com.gildedgames.aether.common.item.accessories.gloves.GlovesItem;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber
public class AccessoryAbilityListener
{
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        if (!player.level.isClientSide() && target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            if (livingTarget.isAttackable() && !livingTarget.skipAttackInteraction(player)) {
                CuriosApi.getCuriosHelper().findEquippedCurio((stack) -> stack.getItem() instanceof GlovesItem, player).ifPresent((triple) -> triple.getRight().hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND)));
            }
        }
    }

    @SubscribeEvent
    public static void onMiningSpeed(PlayerEvent.BreakSpeed event) {
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.ZANITE_RING.get(), event.getPlayer()).ifPresent((triple) -> IZaniteAccessory.handleMiningSpeed(event, triple));
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.ZANITE_PENDANT.get(), event.getPlayer()).ifPresent((triple) -> IZaniteAccessory.handleMiningSpeed(event, triple));
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.AGILITY_CAPE.get(), livingEntity).isPresent()) {
            livingEntity.maxUpStep = !livingEntity.isCrouching() ? 1.0F : 0.6F;
        } else {
            livingEntity.maxUpStep = 0.6F;
        }
    }

    @SubscribeEvent
    public static void onTargetSet(LivingEvent.LivingVisibilityEvent event) {
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.INVISIBILITY_CLOAK.get(), event.getEntityLiving()).ifPresent((triple) -> event.modifyVisibility(0.0D));
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
            Entity impactedEntity = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();
            if (impactedEntity instanceof LivingEntity && event.getEntity() instanceof ProjectileEntity) {
                LivingEntity impactedLiving = (LivingEntity) impactedEntity;
                ProjectileEntity projectile = (ProjectileEntity) event.getEntity();
                if (projectile.getType().is(AetherTags.Entities.DEFLECTABLE_PROJECTILES)) {
                    CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.REPULSION_SHIELD.get(), impactedLiving).ifPresent((triple) -> {
                        event.setCanceled(true);
                        if (impactedEntity instanceof PlayerEntity) {
                            IAetherPlayer.get((PlayerEntity) impactedLiving).ifPresent(aetherPlayer -> {
                                aetherPlayer.setProjectileImpactedMaximum(150);
                                aetherPlayer.setProjectileImpactedTimer(150);
                            });
                        }
                        projectile.setDeltaMovement(projectile.getDeltaMovement().scale(-0.25D));
                        triple.getRight().hurtAndBreak(1, impactedLiving, (entity) -> CuriosApi.getCuriosHelper().onBrokenCurio(triple.getLeft(), triple.getMiddle(), entity));
                    });
                }
            }
        }
    }
}
