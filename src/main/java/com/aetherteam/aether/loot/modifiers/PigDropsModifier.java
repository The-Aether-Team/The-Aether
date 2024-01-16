package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import io.github.fabricators_of_create.porting_lib.loot.LootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class PigDropsModifier extends LootModifier {
    public static final Codec<PigDropsModifier> CODEC = RecordCodecBuilder.create((instance) -> LootModifier.codecStart(instance).apply(instance, PigDropsModifier::new));

    public PigDropsModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    /**
     * Doubles pig drops with a 1/4 chance if a mob is attacked with full strength with a Pig Slayer and the mob is tagged with {@link AetherTags.Entities#PIGS} and the item is tagged with {@link AetherTags.Items#PIG_DROPS}.
     * @param lootStacks Result items from a loot table as an {@link ObjectArrayList} of {@link ItemStack}s.
     * @param context The {@link LootContext}.
     * @return A new {@link ObjectArrayList} of {@link ItemStack}s that a loot table will give.
     */
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.DIRECT_KILLER_ENTITY);
        Entity target = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        ObjectArrayList<ItemStack> newStacks = new ObjectArrayList<>(lootStacks);
        if (entity instanceof LivingEntity livingEntity && target instanceof LivingEntity livingTarget) {
            if (EquipmentUtil.isFullStrength(livingEntity) && livingEntity.getMainHandItem().is(AetherItems.PIG_SLAYER.get()) && livingTarget.getType().is(AetherTags.Entities.PIGS) && livingTarget.getRandom().nextInt(4) == 0) {
                for (ItemStack stack : lootStacks) {
                    if (stack.is(AetherTags.Items.PIG_DROPS)) {
                        newStacks.add(stack);
                    }
                }
            }
        }


        return newStacks;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return PigDropsModifier.CODEC;
    }
}