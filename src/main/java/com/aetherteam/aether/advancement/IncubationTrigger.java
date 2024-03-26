package com.aetherteam.aether.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * Criterion trigger used for checking when an item has finished incubating.
 */
public class IncubationTrigger extends SimpleCriterionTrigger<IncubationTrigger.Instance> {

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public record Instance(Optional<ContextAwarePredicate> player,
                           Optional<ItemPredicate> item) implements SimpleInstance {

        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(IncubationTrigger.Instance::player),
                        ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item").forGetter(IncubationTrigger.Instance::item))
                .apply(instance, IncubationTrigger.Instance::new));

        public static Criterion<Instance> forItem(ItemPredicate item) {
            return AetherAdvancementTriggers.INCUBATION_TRIGGER.get().createCriterion(new IncubationTrigger.Instance(Optional.empty(), Optional.of(item)));
        }

        public boolean test(ItemStack stack) {
            return this.item.isEmpty() || this.item.get().matches(stack);
        }
    }
}
