package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.entity.projectile.PoisonNeedleEntity;
import com.gildedgames.aether.common.entity.projectile.dart.EnchantedDartEntity;
import com.gildedgames.aether.common.entity.projectile.dart.GoldenDartEntity;
import com.gildedgames.aether.common.entity.projectile.dart.PoisonDartEntity;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
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
            if (source.getDirectEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) source.getDirectEntity();
                ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
                Item item = stack.getItem();
                if (item == AetherItems.SKYROOT_SWORD.get() && !entity.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
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
            if (source.getDirectEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) source.getDirectEntity();
                ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
                Item item = stack.getItem();
                if (item == AetherItems.SKYROOT_SWORD.get() && !entity.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
                    ArrayList<ItemEntity> newDrops = new ArrayList<>(event.getDrops().size());
                    for (ItemEntity drop : event.getDrops()) {
                        ItemStack droppedStack = drop.getItem();
                        if (!droppedStack.getItem().is(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS)) {
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
    public static void onArrowHit(ProjectileImpactEvent.Arrow event) {
        if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
            Entity impactedEntity = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();
            IPhoenixArrow.get(event.getArrow()).ifPresent(phoenixArrow -> {
                if (phoenixArrow.isPhoenixArrow() && phoenixArrow.getFireTime() > 0) {
                    impactedEntity.setSecondsOnFire(phoenixArrow.getFireTime());
                }
            });
        }
    }

    @SubscribeEvent
    public static void onDartHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (!playerEntity.level.isClientSide) {
                Entity source = event.getSource().getDirectEntity();
                if (source instanceof GoldenDartEntity) {
                    IAetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> aetherPlayer.setGoldenDartCount(aetherPlayer.getGoldenDartCount() + 1));
                } else if (source instanceof PoisonDartEntity || source instanceof PoisonNeedleEntity) {
                    IAetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> aetherPlayer.setPoisonDartCount(aetherPlayer.getPoisonDartCount() + 1));
                } else if (source instanceof EnchantedDartEntity) {
                    IAetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> aetherPlayer.setEnchantedDartCount(aetherPlayer.getEnchantedDartCount() + 1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            ILightningTracker.get(event.getLightning()).ifPresent(lightningTracker -> {
                if (lightningTracker.getOwner() != null) {
                    if (entity == lightningTracker.getOwner() || entity == lightningTracker.getOwner().getVehicle()) {
                        event.setCanceled(true);
                    }
                }
            });
        }
    }
}
