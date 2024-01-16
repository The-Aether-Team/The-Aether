package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.abilities.weapon.SkyrootWeapon;
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

public class DoubleDropsModifier extends LootModifier {
    public static final Codec<DoubleDropsModifier> CODEC = RecordCodecBuilder.create((instance) -> LootModifier.codecStart(instance).apply(instance, DoubleDropsModifier::new));

    public DoubleDropsModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    /**
     * Doubles mob drops if a mob is attacked with full strength with an item that implements {@link SkyrootWeapon} if the mob isn't tagged with {@link AetherTags.Entities#NO_SKYROOT_DOUBLE_DROPS} and the item isn't tagged with {@link AetherTags.Items#NO_SKYROOT_DOUBLE_DROPS}.
     * @param lootStacks Result items from a loot table as an {@link ObjectArrayList} of {@link ItemStack}s.
     * @param context The {@link LootContext}.
     * @return A new {@link ObjectArrayList} of {@link ItemStack}s that a loot table will give.
     */
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.DIRECT_KILLER_ENTITY);
        Entity target = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        ObjectArrayList<ItemStack> newStacks = new ObjectArrayList<>(lootStacks);
        if (entity instanceof LivingEntity livingEntity && target != null) {
            if (EquipmentUtil.isFullStrength(livingEntity) && livingEntity.getMainHandItem().getItem() instanceof SkyrootWeapon && !target.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
                for (ItemStack stack : lootStacks) {
                    if (!stack.is(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS)) {
                        newStacks.add(stack);
                    }
                }
            }
        }
        return newStacks;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return DoubleDropsModifier.CODEC;
    }
}
