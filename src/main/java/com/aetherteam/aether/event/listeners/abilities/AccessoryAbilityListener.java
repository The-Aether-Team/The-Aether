package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.item.accessories.abilities.ShieldOfRepulsionAccessory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class AccessoryAbilityListener {
    /**
     * @see AbilityHooks.AccessoryHooks#damageZaniteRing(LivingEntity, LevelAccessor, BlockState, BlockPos)
     * @see AbilityHooks.AccessoryHooks#damageZanitePendant(LivingEntity, LevelAccessor, BlockState, BlockPos)
     */
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        LevelAccessor level = event.getLevel();
        BlockState state = event.getState();
        BlockPos pos = event.getPos();
        if (!event.isCanceled()) {
            AbilityHooks.AccessoryHooks.damageZaniteRing(player, level, state, pos);
            AbilityHooks.AccessoryHooks.damageZanitePendant(player, level, state, pos);
        }
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
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.AccessoryHooks#preventTargeting(LivingEntity, Entity)
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.AccessoryHooks#recentlyAttackedWithInvisibility(LivingEntity, Entity)
     */
    @SubscribeEvent
    public static void onTargetSet(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Entity lookingEntity = event.getLookingEntity();
        if (AbilityHooks.AccessoryHooks.preventTargeting(livingEntity, lookingEntity) && !AbilityHooks.AccessoryHooks.recentlyAttackedWithInvisibility(livingEntity, lookingEntity)) {
            event.modifyVisibility(0.0);
        }
        if (AbilityHooks.AccessoryHooks.recentlyAttackedWithInvisibility(livingEntity, lookingEntity)) {
            event.modifyVisibility(50);
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

    /**
     * @see AbilityHooks.AccessoryHooks#preventMagmaDamage(LivingEntity, DamageSource)
     */
    @SubscribeEvent
    public static void onEntityHurt(LivingAttackEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        AbilityHooks.AccessoryHooks.setAttack(event.getSource());
        if (AbilityHooks.AccessoryHooks.preventMagmaDamage(livingEntity, damageSource)) {
            event.setCanceled(true);
        }
    }
}
