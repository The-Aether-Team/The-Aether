package com.gildedgames.aether.common.loot.modifiers;

import com.gildedgames.aether.common.registry.AetherDimensions;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class RemoveSeedsModifier extends LootModifier {
    public RemoveSeedsModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (context.getLevel().dimension() == AetherDimensions.AETHER_WORLD) {
            generatedLoot.removeIf((itemStack) -> itemStack.is(Items.WHEAT_SEEDS));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<RemoveSeedsModifier> {
        @Override
        public RemoveSeedsModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn) {
            return new RemoveSeedsModifier(conditionsIn);
        }

        @Override
        public JsonObject write(RemoveSeedsModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
