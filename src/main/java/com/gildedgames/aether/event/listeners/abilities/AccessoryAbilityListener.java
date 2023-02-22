package com.gildedgames.aether.event.listeners.abilities;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.event.hooks.AbilityHooks;
import com.gildedgames.aether.item.accessories.abilities.ShieldOfRepulsionAccessory;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class AccessoryAbilityListener {
    /**
     * @see AbilityHooks.AccessoryHooks#damageGloves(DamageSource)
     */
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource damageSource = event.getSource();
        AbilityHooks.AccessoryHooks.damageGloves(damageSource);
    }

    /**
     * @see AbilityHooks.AccessoryHooks#handleZaniteRingAbility(LivingEntity, float)
     * @see AbilityHooks.AccessoryHooks#handleZanitePendantAbility(LivingEntity, float)
     */
    @SubscribeEvent
    public static void onMiningSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (!event.isCanceled()) {
            event.setNewSpeed(AbilityHooks.AccessoryHooks.handleZaniteRingAbility(player, event.getNewSpeed()));
            event.setNewSpeed(AbilityHooks.AccessoryHooks.handleZanitePendantAbility(player, event.getNewSpeed()));
        }
    }

    /**
     * Makes the wearer invisible to other mobs' targeting if wearing an Invisibility Cloak.
     */
    @SubscribeEvent
    public static void onTargetSet(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (EquipmentUtil.hasInvisibilityCloak(livingEntity) && !livingEntity.getType().is(Tags.EntityTypes.BOSSES)) {
            event.modifyVisibility(0.0);
        }
    }

    /**
     * @see ShieldOfRepulsionAccessory#deflectProjectile(ProjectileImpactEvent, HitResult, Projectile)
     */
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        HitResult hitResult = event.getRayTraceResult();
        Projectile projectile = event.getProjectile();
        ShieldOfRepulsionAccessory.deflectProjectile(event, hitResult, projectile); // Has to take event due to the event being canceled within a lambda and also mid-behavior.
    }
}
