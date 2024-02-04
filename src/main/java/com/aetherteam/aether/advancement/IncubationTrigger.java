package com.aetherteam.aether.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * Criterion trigger used for checking when an item has finished incubating.
 */
public class IncubationTrigger extends SimpleCriterionTrigger<IncubationTrigger.Instance> {
    public static final IncubationTrigger INSTANCE = new IncubationTrigger();

    @Override
    public IncubationTrigger.Instance createInstance(JsonObject json, Optional<ContextAwarePredicate> predicate, DeserializationContext context) {
        Optional<ItemPredicate> itemPredicate = ItemPredicate.fromJson(json.get("item"));
        return new IncubationTrigger.Instance(predicate, itemPredicate);
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final Optional<ItemPredicate> item;

        public Instance(Optional<ContextAwarePredicate> predicate, Optional<ItemPredicate> item) {
            super(predicate);
            this.item = item;
        }

        public static Criterion<Instance> forItem(ItemPredicate item) {
            return INSTANCE.createCriterion(new IncubationTrigger.Instance(Optional.empty(), Optional.of(item)));
        }

        public boolean test(ItemStack stack) {
            return this.item.isEmpty() || this.item.get().matches(stack);
        }

        @Override
        public JsonObject serializeToJson() {
            JsonObject jsonObject = super.serializeToJson();
            this.item.ifPresent(itemPredicate -> jsonObject.add("item", itemPredicate.serializeToJson()));
            return jsonObject;
        }
    }
}
