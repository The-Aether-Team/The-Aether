package com.gildedgames.aether.common.event.hooks;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.monster.Swet;
import com.gildedgames.aether.common.entity.passive.FlyingCow;
import com.gildedgames.aether.common.entity.passive.MountableAnimal;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;

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
            if (heldStack.getItem() == AetherItems.SKYROOT_BUCKET.get()) {
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
}
