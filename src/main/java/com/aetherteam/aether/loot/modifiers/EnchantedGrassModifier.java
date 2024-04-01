package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.AetherTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.Optional;

public class EnchantedGrassModifier extends LootModifier {
    public static final Codec<EnchantedGrassModifier> CODEC = RecordCodecBuilder.create((instance) -> LootModifier.codecStart(instance)
            .and(ItemStack.CODEC.fieldOf("item").forGetter((modifier) -> modifier.item))
            .apply(instance, EnchantedGrassModifier::new));

    public final ItemStack item;

    public EnchantedGrassModifier(LootItemCondition[] conditions, ItemStack item) {
        super(conditions);
        this.item = item;
    }

    /**
     * Randomly adds an extra item drop with a 50% chance if the modifier block target is on top of any blocks in the {@link AetherTags.Blocks#ENCHANTED_GRASS},
     * and if there's a loot item that matches the item that is able to have its drops increased.
     *
     * @param lootStacks Result items from a loot table as an {@link ObjectArrayList} of {@link ItemStack}s.
     * @param context    The {@link LootContext}.
     * @return A new {@link ObjectArrayList} of {@link ItemStack}s that a loot table will give.
     */
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        Vec3 originVec = context.getParamOrNull(LootContextParams.ORIGIN);
        if (originVec != null && context.getLevel().getBlockState(BlockPos.containing(originVec).below()).is(AetherTags.Blocks.ENCHANTED_GRASS)) {
            if (context.getRandom().nextBoolean()) {
                Optional<ItemStack> itemStack = lootStacks.stream().filter((stack) -> stack.is(this.item.getItem())).findFirst();
                itemStack.ifPresent(stack -> lootStacks.add(new ItemStack(stack.getItem(), 1)));
            }
        }
        return lootStacks;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return EnchantedGrassModifier.CODEC;
    }
}
