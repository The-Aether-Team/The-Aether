package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.abilities.weapon.SkyrootWeapon;
import com.aetherteam.aetherfabric.common.loot.LootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class DoubleDropsModifier extends LootModifier {
    public DoubleDropsModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    /**
     * Doubles mob drops if a mob is attacked with full strength with an item that implements {@link SkyrootWeapon}
     * if the mob isn't tagged with {@link AetherTags.Entities#NO_SKYROOT_DOUBLE_DROPS} and the item isn't tagged
     * with {@link AetherTags.Items#NO_SKYROOT_DOUBLE_DROPS}.
     *
     * @param lootStacks Result items from a loot table as an {@link ObjectArrayList} of {@link ItemStack}s.
     * @param context    The {@link LootContext}.
     * @return A new {@link ObjectArrayList} of {@link ItemStack}s that a loot table will give.
     */
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        ObjectArrayList<ItemStack> newStacks = new ObjectArrayList<>(lootStacks);

        // Get the entity that was killed
        Entity target = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (target == null) {
            return newStacks; // Return original loot if no target entity
        }

        // Get the attacking entity, which could be null or a DamageSource
        Object attacker = context.getParamOrNull(LootContextParams.DIRECT_ATTACKING_ENTITY);
        if (!(attacker instanceof LivingEntity livingEntity)) {
            return newStacks; // Return original loot if attacker is not a LivingEntity
        }

        // Check conditions for doubling drops
        if (EquipmentUtil.isFullStrength(livingEntity)
            && livingEntity.getMainHandItem().getItem() instanceof SkyrootWeapon
            && !target.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
            for (ItemStack stack : lootStacks) {
                if (!stack.is(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS)) {
                    newStacks.add(stack.copy()); // Add a copy to avoid modifying the original stack
                }
            }
        }

        return newStacks;
    }
}
