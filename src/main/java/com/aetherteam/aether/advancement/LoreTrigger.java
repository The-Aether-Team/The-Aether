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
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

/**
 * Criterion trigger used for checking an item placed by a player inside a Book of Lore.
 */
public class LoreTrigger extends SimpleCriterionTrigger<LoreTrigger.Instance> {

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public record Instance(Optional<ContextAwarePredicate> player,
                           Optional<ItemPredicate> item) implements SimpleInstance {

        public static final Codec<LoreTrigger.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(LoreTrigger.Instance::player),
                        ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item").forGetter(LoreTrigger.Instance::item))
                .apply(instance, LoreTrigger.Instance::new));

        public static Criterion<Instance> forItem(ItemPredicate item) {
            return AetherAdvancementTriggers.LORE_ENTRY.get().createCriterion(new LoreTrigger.Instance(Optional.empty(), Optional.of(item)));
        }

        public static Criterion<Instance> forItem(ItemLike item) {
            return forItem(ItemPredicate.Builder.item().of(item).build());
        }

        public static Criterion<Instance> forAny() {
            return AetherAdvancementTriggers.LORE_ENTRY.get().createCriterion(new LoreTrigger.Instance(Optional.empty(), Optional.empty()));
        }

        public boolean test(ItemStack stack) {
            return this.item.isEmpty() || this.item.get().matches(stack);
        }
    }
}
