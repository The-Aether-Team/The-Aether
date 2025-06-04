package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aetherfabric.common.loot.LootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class PigDropsModifier extends LootModifier {
    public PigDropsModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    /**
     * Doubles pig drops with a 1/4 chance if a mob is attacked with full strength with a Pig Slayer
     * and the mob is tagged with {@link AetherTags.Entities#PIGS} and the item is tagged with
     * {@link AetherTags.Items#PIG_DROPS}.
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
        if (!(target instanceof LivingEntity livingTarget)) {
            return newStacks; // Return original loot if target is not a LivingEntity
        }

        // Get the attacking entity, which could be null or a DamageSource
        Object attacker = context.getParamOrNull(LootContextParams.DIRECT_ATTACKING_ENTITY);
        if (!(attacker instanceof LivingEntity livingEntity)) {
            return newStacks; // Return original loot if attacker is not a LivingEntity
        }

        // Check conditions for doubling drops
        if (EquipmentUtil.isFullStrength(livingEntity)
            && livingEntity.getMainHandItem().is(AetherItems.PIG_SLAYER.get())
            && livingTarget.getType().is(AetherTags.Entities.PIGS)
            && livingTarget.getRandom().nextInt(4) == 0) {
            for (ItemStack stack : lootStacks) {
                if (stack.is(AetherTags.Items.PIG_DROPS)) {
                    newStacks.add(stack.copy()); // Add a copy to avoid modifying the original stack
                }
            }
        }

        return newStacks;
    }
}
