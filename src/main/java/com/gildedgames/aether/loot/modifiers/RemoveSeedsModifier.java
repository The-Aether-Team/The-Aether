package com.gildedgames.aether.loot.modifiers;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.api.DimensionTagTracking;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;

public class RemoveSeedsModifier extends LootModifier {
    public static final Codec<RemoveSeedsModifier> CODEC = RecordCodecBuilder.create(inst -> LootModifier.codecStart(inst).apply(inst, RemoveSeedsModifier::new));

    public RemoveSeedsModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (DimensionTagTracking.inTag(context.getLevel(), AetherTags.Dimensions.NO_WHEAT_SEEDS)) {
            generatedLoot.removeIf((itemStack) -> itemStack.is(Items.WHEAT_SEEDS));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return RemoveSeedsModifier.CODEC;
    }
}
