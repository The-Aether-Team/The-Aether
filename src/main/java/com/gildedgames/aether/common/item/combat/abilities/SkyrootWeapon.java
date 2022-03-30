package com.gildedgames.aether.common.item.combat.abilities;

import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface SkyrootWeapon { //todo: documentation.
    Map<Integer, NonNullList<Item>> doubleDropEntities = new HashMap<>();

    static void entityKilledBySkyroot(LivingEntity entity, DamageSource source) {
        if (source instanceof EntityDamageSource entityDamageSource && entityDamageSource.getDirectEntity() instanceof Player player) {
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

    static void entityDropsBySkyroot(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops) {
        if (source instanceof EntityDamageSource entityDamageSource && entityDamageSource.getDirectEntity() instanceof Player player) {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(AetherTags.Items.SKYROOT_WEAPONS) && !entity.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
                ArrayList<ItemEntity> newDrops = new ArrayList<>(drops.size());
                for (ItemEntity drop : drops) {
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
                drops.addAll(newDrops);
            }
        }
    }
}
