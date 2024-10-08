package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.AetherTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class RemoveSeedsModifier extends LootModifier {
    public static final MapCodec<RemoveSeedsModifier> CODEC = RecordCodecBuilder.mapCodec((instance) -> LootModifier.codecStart(instance).apply(instance, RemoveSeedsModifier::new));

    public RemoveSeedsModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    /**
     * Removes Wheat Seeds from loot tables within the Aether (determined by {@link AetherTags.Biomes#NO_WHEAT_SEEDS}).
     *
     * @param lootStacks Result items from a loot table as an {@link ObjectArrayList} of {@link ItemStack}s.
     * @param context    The {@link LootContext}.
     * @return A new {@link ObjectArrayList} of {@link ItemStack}s that a loot table will give.
     */
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        Vec3 originVec = context.getParamOrNull(LootContextParams.ORIGIN);
        if (originVec != null && context.getLevel().getBiome(BlockPos.containing(originVec)).is(AetherTags.Biomes.NO_WHEAT_SEEDS)) {
            lootStacks.removeIf((itemStack) -> itemStack.is(Items.WHEAT_SEEDS));
        }
        return lootStacks;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return RemoveSeedsModifier.CODEC;
    }
}
