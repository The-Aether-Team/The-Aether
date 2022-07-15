package com.gildedgames.aether.event.listeners.abilities;

import com.gildedgames.aether.event.hooks.AbilityHooks;
import com.gildedgames.aether.item.accessories.abilities.ShieldOfRepulsionAccessory;
import com.gildedgames.aether.item.accessories.abilities.ZaniteAccessory;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber
public class AccessoryAbilityListener {
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        AbilityHooks.AccessoryHooks.damageGloves(player, target);
    }

    @SubscribeEvent
    public static void onMiningSpeed(PlayerEvent.BreakSpeed event) {
        CuriosApi.getCuriosHelper().findCurios(event.getEntity(), AetherItems.ZANITE_RING.get()).forEach((slotResult -> event.setNewSpeed(ZaniteAccessory.handleMiningSpeed(event.getNewSpeed(), slotResult))));
        CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), AetherItems.ZANITE_PENDANT.get()).ifPresent((slotResult) -> event.setNewSpeed(ZaniteAccessory.handleMiningSpeed(event.getNewSpeed(), slotResult)));
    }

    @SubscribeEvent
    public static void onTargetSet(LivingEvent.LivingVisibilityEvent event) {
        CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), AetherItems.INVISIBILITY_CLOAK.get()).ifPresent((slotResult) -> event.modifyVisibility(0.0D));
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        HitResult hitResult = event.getRayTraceResult();
        Projectile projectile = event.getProjectile();
        ShieldOfRepulsionAccessory.deflectProjectile(event, hitResult, projectile); //Has to take event due to the event being canceled within a lambda.
    }
}
