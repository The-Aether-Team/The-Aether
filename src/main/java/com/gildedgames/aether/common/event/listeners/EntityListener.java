package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.monster.Swet;
import com.gildedgames.aether.common.entity.passive.MountableEntity;
import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvents;
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
        if(event.isDismounting() && rider.isShiftKeyDown()) {
            if ((mount instanceof MountableEntity && !mount.isOnGround()) || (mount instanceof Swet swet && !swet.isFriendly())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onInteractWithEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity target = event.getTarget();
        if ((target instanceof Cow || target instanceof FlyingCowEntity) && !((Animal) target).isBaby()) {
            Player player = event.getPlayer();
            InteractionHand hand = event.getHand();
            ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.getItem() == AetherItems.SKYROOT_BUCKET.get()) {
                if (target instanceof FlyingCowEntity) {
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
