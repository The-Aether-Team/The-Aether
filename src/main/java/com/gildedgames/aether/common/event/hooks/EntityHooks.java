package com.gildedgames.aether.common.event.hooks;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.monster.Swet;
import com.gildedgames.aether.common.entity.passive.FlyingCow;
import com.gildedgames.aether.common.entity.passive.MountableAnimal;
import com.gildedgames.aether.common.item.miscellaneous.bucket.SkyrootBucketItem;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.MilkCowPacket;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class EntityHooks {
    public static boolean dismountPrevention(Entity rider, Entity mount, boolean dismounting) {
        if (dismounting && rider.isShiftKeyDown()) {
            return (mount instanceof MountableAnimal && !mount.isOnGround()) || (mount instanceof Swet swet && !swet.isFriendly());
        }
        return false;
    }

    public static void skyrootBucketMilking(Entity target, Player player, InteractionHand hand) {
        if ((target instanceof Cow || target instanceof FlyingCow) && !((Animal) target).isBaby()) {
            ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.is(AetherItems.SKYROOT_BUCKET.get())) {
                if (target.level.isClientSide()) {
                    if (target instanceof FlyingCow) {
                        player.playSound(AetherSoundEvents.ENTITY_FLYING_COW_MILK.get(), 1.0F, 1.0F);
                    } else  {
                        player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                    }
                    AetherPacketHandler.sendToServer(new MilkCowPacket(player.getId(), heldStack, hand == InteractionHand.MAIN_HAND)); //necessary due to a Forge bug.
                    ItemStack filledBucket = ItemUtils.createFilledResult(heldStack, player, AetherItems.SKYROOT_MILK_BUCKET.get().getDefaultInstance());
                    player.swing(hand);
                    player.setItemInHand(hand, filledBucket);
                }
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
}
