package com.gildedgames.aether.item.combat.abilities.weapon;

import com.gildedgames.aether.AetherTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface SkyrootWeapon {
    /**
     * Map corresponding an entity ID with a list of equipped items.
     */
    Map<Integer, NonNullList<Item>> doubleDropEntities = new HashMap<>();

    /**
     * Tracks a killed entity and adds any held or worn items corresponding with that entity to a list {@link SkyrootWeapon#doubleDropEntities}.
     * @param target The killed entity.
     */
    default void entityKilledBySkyroot(LivingEntity target) {
        NonNullList<Item> inventory = NonNullList.create();
        for (ItemStack handStack : target.getHandSlots()) {
            inventory.add(handStack.getItem());
        }
        for (ItemStack armorStack : target.getArmorSlots()) {
            inventory.add(armorStack.getItem());
        }
        inventory.removeIf((storedItem -> storedItem == Items.AIR));
        doubleDropEntities.put(target.getId(), inventory);
    }

    /**
     * Determines what drops should be doubled when a target is killed. Any items tagged as {@link AetherTags.Items#NO_SKYROOT_DOUBLE_DROPS} and any equipped items tracked by {@link SkyrootWeapon#doubleDropEntities} aren't doubled.
     * The items that are able to be doubled are tracked in newDrops and added into drops which {@link net.minecraftforge.event.entity.living.LivingDropsEvent} has access to.
     * @param target The killed entity.
     * @param drops The normal drops of the killed entity.
     */
    default void entityDropsBySkyroot(LivingEntity target, Collection<ItemEntity> drops) {
        ArrayList<ItemEntity> newDrops = new ArrayList<>(drops.size());
        for (ItemEntity drop : drops) {
            ItemStack droppedStack = drop.getItem();
            if (doubleDropEntities.containsKey(target.getId())) {
                if (!droppedStack.is(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS) && !doubleDropEntities.get(target.getId()).contains(droppedStack.getItem())) {
                    ItemEntity dropEntity = new ItemEntity(target.getLevel(), drop.getX(), drop.getY(), drop.getZ(), droppedStack.copy());
                    dropEntity.setDefaultPickUpDelay();
                    newDrops.add(dropEntity);
                }
            }
        }
        drops.addAll(newDrops);
        doubleDropEntities.remove(target.getId());
    }
}
