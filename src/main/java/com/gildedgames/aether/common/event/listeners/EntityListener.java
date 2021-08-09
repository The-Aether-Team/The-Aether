package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.common.advancement.MountTrigger;
import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;
import com.gildedgames.aether.common.registry.AetherAdvancements;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityListener
{
    @SubscribeEvent
    public static void onMountEntity(EntityMountEvent event) {
        Entity rider = event.getEntityMounting();
        Entity mount = event.getEntityBeingMounted();
        if (event.getEntityBeingMounted() != null && rider instanceof ServerPlayerEntity) {
            MountTrigger.INSTANCE.trigger((ServerPlayerEntity) rider, mount);
        }
    }

    @SubscribeEvent
    public static void onInteractWithEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity target = event.getTarget();
        if ((target instanceof CowEntity || target instanceof FlyingCowEntity) && !((AnimalEntity) target).isBaby()) {
            PlayerEntity player = event.getPlayer();
            Hand hand = event.getHand();
            ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.getItem() == AetherItems.SKYROOT_BUCKET.get()) {
                player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                ItemStack filledBucket = DrinkHelper.createFilledResult(heldStack, player, AetherItems.SKYROOT_MILK_BUCKET.get().getDefaultInstance());
                player.swing(hand);
                player.setItemInHand(hand, filledBucket);
            }
        }
    }
}
