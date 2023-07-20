package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.capability.item.DroppedItem;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.ai.goal.BeeGrowBerryBushGoal;
import com.aetherteam.aether.entity.ai.goal.FoxEatBerryBushGoal;
import com.aetherteam.aether.entity.monster.dungeon.boss.slider.Slider;
import com.aetherteam.aether.entity.passive.FlyingCow;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.miscellaneous.bucket.SkyrootBucketItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Collection;
import java.util.Optional;

public class EntityHooks {
    public static void addGoals(Entity entity) {
        if (entity.getClass() == Bee.class) {
            Bee bee = (Bee) entity;
            bee.getGoalSelector().addGoal(7, new BeeGrowBerryBushGoal(bee));
        } else if (entity.getClass() == Fox.class) {
            Fox fox = (Fox) entity;
            fox.goalSelector.addGoal(10, new FoxEatBerryBushGoal(fox, 1.2F, 12, 1));
        }
    }

    public static void launchMount(Player player) {
        Entity mount = player.getVehicle();
        if (player.isPassenger() && mount != null) {
            if (mount.getLevel().getBlockStates(mount.getBoundingBox()).anyMatch((state) -> state.is(AetherBlocks.BLUE_AERCLOUD.get()))) {
                if (player.getLevel().isClientSide()) {
                    mount.setDeltaMovement(mount.getDeltaMovement().x(), 2.0, mount.getDeltaMovement().z());
                }
            }
        }
    }

    public static void skyrootBucketMilking(Entity target, Player player, InteractionHand hand) {
        if ((target instanceof Cow || target instanceof FlyingCow) && !((Animal) target).isBaby()) {
            ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.is(AetherItems.SKYROOT_BUCKET.get())) {
                if (target instanceof FlyingCow) {
                    player.playSound(AetherSoundEvents.ENTITY_FLYING_COW_MILK.get(), 1.0F, 1.0F);
                } else  {
                    player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                }
                ItemStack filledBucket = ItemUtils.createFilledResult(heldStack, player, AetherItems.SKYROOT_MILK_BUCKET.get().getDefaultInstance());
                player.swing(hand);
                player.setItemInHand(hand, filledBucket);
            }
        }
    }

    public static Optional<InteractionResult> pickupBucketable(Entity target, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        Optional<InteractionResult> interactionResult = Optional.empty();
        if (heldStack.is(AetherItems.SKYROOT_WATER_BUCKET.get())) {
            if (target instanceof Bucketable bucketable && target instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
                ItemStack bucketStack = bucketable.getBucketItemStack();
                bucketStack = SkyrootBucketItem.swapBucketType(bucketStack);
                if (!bucketStack.isEmpty()) {
                    target.playSound(bucketable.getPickupSound(), 1.0F, 1.0F);
                    bucketable.saveToBucketTag(bucketStack);
                    ItemStack filledStack = ItemUtils.createFilledResult(heldStack, player, bucketStack, false);
                    player.setItemInHand(hand, filledStack);
                    Level level = livingEntity.getLevel();
                    if (!level.isClientSide()) {
                        CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucketStack);
                    }
                    target.discard();
                    interactionResult = Optional.of(InteractionResult.sidedSuccess(level.isClientSide()));
                } else {
                    interactionResult = Optional.of(InteractionResult.FAIL);
                }
            }
        }
        return interactionResult;
    }

    public static boolean preventEntityHooked(Entity projectileEntity, HitResult rayTraceResult) {
        if (rayTraceResult instanceof EntityHitResult entityHitResult) {
            return entityHitResult.getEntity().getType().is(AetherTags.Entities.UNHOOKABLE) && projectileEntity instanceof FishingHook;
        }
        return false;
    }

    public static boolean preventSliderShieldBlock(DamageSource source) {
        return source.getEntity() instanceof Slider;
    }

    public static boolean lightningHitKeys(Entity entity) {
        if (entity instanceof ItemEntity itemEntity) {
            return itemEntity.getItem().is(AetherTags.Items.DUNGEON_KEYS);
        } else {
            return false;
        }
    }

    public static void trackDrops(LivingEntity entity, Collection<ItemEntity> itemDrops) {
        if (entity instanceof Player player) {
            itemDrops.forEach(itemEntity -> DroppedItem.get(itemEntity).ifPresent(droppedItem -> droppedItem.setOwner(player)));
        }
    }
}
