package com.gildedgames.aether.common.item.accessories.miscellaneous;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber
public class RepulsionShieldItem extends AccessoryItem
{
    private static final ResourceLocation REPULSION_SHIELD = new ResourceLocation(Aether.MODID, "textures/models/accessory/repulsion_shield_accessory.png");
    private static final ResourceLocation REPULSION_SHIELD_INACTIVE = new ResourceLocation(Aether.MODID, "textures/models/accessory/repulsion_shield_inactive_accessory.png");

    public RepulsionShieldItem(Properties properties) {
        super(properties);
    }

    public ResourceLocation getRepulsionShieldTexture() {
        return REPULSION_SHIELD;
    }

    public ResourceLocation getRepulsionShieldInactiveTexture() {
        return REPULSION_SHIELD_INACTIVE;
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
