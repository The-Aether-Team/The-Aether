package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.projectile.PoisonNeedle;
import com.gildedgames.aether.common.entity.projectile.dart.EnchantedDart;
import com.gildedgames.aether.common.entity.projectile.dart.GoldenDart;
import com.gildedgames.aether.common.entity.projectile.dart.PoisonDart;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.capability.lightning.LightningTracker;
import com.gildedgames.aether.core.capability.arrow.PhoenixArrow;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.NonNullList;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class WeaponAbilityListener
{
    private static final Map<Integer, NonNullList<Item>> doubleDropEntities = new HashMap<>();

    @SubscribeEvent
    public static void trackEntityDeath(LivingDeathEvent event) {
        if (event.getSource() instanceof EntityDamageSource) {
            LivingEntity entity = event.getEntityLiving();
            EntityDamageSource source = (EntityDamageSource) event.getSource();
            if (source.getDirectEntity() instanceof Player) {
                Player player = (Player) source.getDirectEntity();
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (stack.is(AetherTags.Items.SKYROOT_WEAPONS) && !entity.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
                    NonNullList<Item> inventory = NonNullList.create();
                    for (ItemStack handStack : entity.getHandSlots()) {
                        inventory.add(handStack.getItem());
                    }
                    for (ItemStack armorStack : entity.getArmorSlots()) {
                        inventory.add(armorStack.getItem());
                    }
                    inventory.removeIf((storedItem -> storedItem == Items.AIR));
                    doubleDropEntities.put(entity.getId(), inventory);
                }
            }
        }
    }

    @SubscribeEvent
    public static void doSkyrootDoubleDrops(LivingDropsEvent event) {
        if (event.getSource() instanceof EntityDamageSource) {
            LivingEntity entity = event.getEntityLiving();
            EntityDamageSource source = (EntityDamageSource) event.getSource();
            if (source.getDirectEntity() instanceof Player) {
                Player player = (Player) source.getDirectEntity();
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (stack.is(AetherTags.Items.SKYROOT_WEAPONS) && !entity.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
                    ArrayList<ItemEntity> newDrops = new ArrayList<>(event.getDrops().size());
                    for (ItemEntity drop : event.getDrops()) {
                        ItemStack droppedStack = drop.getItem();
                        if (!droppedStack.is(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS)) {
                            ItemEntity dropEntity = new ItemEntity(entity.level, drop.getX(), drop.getY(), drop.getZ(), droppedStack.copy());
                            dropEntity.setDefaultPickUpDelay();
                            newDrops.add(dropEntity);
                        }
                    }
                    if (doubleDropEntities.containsKey(entity.getId())) {
                        NonNullList<Item> inventory = doubleDropEntities.get(entity.getId());
                        newDrops.removeIf(newDrop -> inventory.contains(newDrop.getItem().getItem()));
                        doubleDropEntities.remove(entity.getId());
                    }
                    event.getDrops().addAll(newDrops);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onArrowHit(ProjectileImpactEvent event) {
        if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY && event.getProjectile() instanceof AbstractArrow) {
            Entity impactedEntity = ((EntityHitResult) event.getRayTraceResult()).getEntity();
            PhoenixArrow.get((AbstractArrow) event.getProjectile()).ifPresent(phoenixArrow -> {
                if (phoenixArrow.isPhoenixArrow() && phoenixArrow.getFireTime() > 0) {
                    impactedEntity.setSecondsOnFire(phoenixArrow.getFireTime());
                }
            });
        }
    }

    @SubscribeEvent
    public static void onDartHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player playerEntity = (Player) event.getEntityLiving();
            Entity source = event.getSource().getDirectEntity();
            if (source instanceof GoldenDart) {
                AetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> aetherPlayer.setGoldenDartCount(aetherPlayer.getGoldenDartCount() + 1));
            } else if (source instanceof PoisonDart || source instanceof PoisonNeedle) {
                AetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> aetherPlayer.setPoisonDartCount(aetherPlayer.getPoisonDartCount() + 1));
            } else if (source instanceof EnchantedDart) {
                AetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> aetherPlayer.setEnchantedDartCount(aetherPlayer.getEnchantedDartCount() + 1));
            }
        }
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            LightningTracker.get(event.getLightning()).ifPresent(lightningTracker -> {
                if (lightningTracker.getOwner() != null) {
                    if (entity == lightningTracker.getOwner() || entity == lightningTracker.getOwner().getVehicle()) {
                        event.setCanceled(true);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onHolystoneWeaponAbility(LivingHurtEvent event) {
        LivingEntity target = event.getEntityLiving();
        if (event.getSource() != null && event.getSource().getEntity() instanceof LivingEntity sourceEntity) {
            ItemStack itemStack = sourceEntity.getMainHandItem();
            if (itemStack.is(AetherTags.Items.HOLYSTONE_WEAPONS)) {
                if (!sourceEntity.level.isClientSide && target.level.getRandom().nextInt(20) == 0) {
                    target.spawnAtLocation(AetherItems.AMBROSIUM_SHARD.get());
                }
            }
        }
    }
}
