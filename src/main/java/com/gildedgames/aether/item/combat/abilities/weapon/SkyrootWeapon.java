package com.gildedgames.aether.item.combat.abilities.weapon;

import com.gildedgames.aether.AetherTags;
import net.minecraft.core.NonNullList;
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

public interface SkyrootWeapon {
    /**
     * Map corresponding an entity ID with a list of equipped items.
     */
    Map<Integer, NonNullList<Item>> doubleDropEntities = new HashMap<>();

    /**
     * Tracks a killed entity and adds any held or worn items corresponding with that entity to a list {@link SkyrootWeapon#doubleDropEntities}.
     * @param target The killed entity.
     * @param source The attacking damage source.
     */
    static void entityKilledBySkyroot(LivingEntity target, DamageSource source) {
        if (performAbility(target, source)) {
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
    }

    /**
     * Determines what drops should be doubled when a target is killed. Any items tagged as {@link AetherTags.Items#NO_SKYROOT_DOUBLE_DROPS} and any equipped items tracked by {@link SkyrootWeapon#doubleDropEntities} aren't doubled.
     * @param target The killed entity.
     * @param source The attacking damage source.
     * @param drops The normal drops of the killed entity.
     */
    static void entityDropsBySkyroot(LivingEntity target, DamageSource source, Collection<ItemEntity> drops) {
        if (performAbility(target, source)) {
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

    /**
     * Basic checks to perform the ability if the source is living, the target can have their drops doubled, and if the attacker attacked with full attack strength if they're a player.
     * @param target The killed entity.
     * @param source The attacking damage source.
     */
    static boolean performAbility(LivingEntity target, DamageSource source) {
        if (source instanceof EntityDamageSource entityDamageSource && entityDamageSource.getDirectEntity() instanceof LivingEntity livingEntity) {
            if ((livingEntity instanceof Player player && player.getAttackStrengthScale(1.0F) == 1.0F) || !(livingEntity instanceof Player)) {
                return !target.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS);
            }
        }
        return false;
    }
}
