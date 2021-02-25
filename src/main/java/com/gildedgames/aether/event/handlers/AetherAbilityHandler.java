package com.gildedgames.aether.event.handlers;

import com.gildedgames.aether.registry.AetherItems;
import com.gildedgames.aether.registry.AetherTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class AetherAbilityHandler
{
    @SubscribeEvent
    public static void doSkyrootDoubleDrops(LivingDropsEvent event) {
        if (!(event.getSource() instanceof EntityDamageSource)) {
            return;
        }

        LivingEntity entity = event.getEntityLiving();
        EntityDamageSource source = (EntityDamageSource) event.getSource();

        if (!(source.getImmediateSource() instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) source.getImmediateSource();
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
        Item item = stack.getItem();

        if (item == AetherItems.SKYROOT_SWORD.get() && !entity.getType().isContained(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
            ArrayList<ItemEntity> newDrops = new ArrayList<>(event.getDrops().size());
            for (ItemEntity drop : event.getDrops()) {
                ItemStack droppedStack = drop.getItem();
                if (!droppedStack.getItem().isIn(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS)) {
                    newDrops.add(new ItemEntity(entity.world, drop.getPosX(), drop.getPosY(), drop.getPosZ(), droppedStack.copy()));
                }
            }
            event.getDrops().addAll(newDrops);
        }
    }
}
