package com.aetherteam.aether.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

/**
 * Criterion trigger used for checking an item placed by a player inside a Book of Lore.
 */
public class LoreTrigger extends SimpleCriterionTrigger<LoreTrigger.Instance> {
    public static final LoreTrigger INSTANCE = new LoreTrigger();

    @Override
    protected Instance createInstance(JsonObject json, Optional<ContextAwarePredicate> predicate, DeserializationContext context) {
        Optional<ItemPredicate> itemPredicate = ItemPredicate.fromJson(json.get("item"));
        return new LoreTrigger.Instance(predicate, itemPredicate);
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final Optional<ItemPredicate>  item;

        public Instance(Optional<ContextAwarePredicate> predicate, Optional<ItemPredicate>  item) {
            super(predicate);
            this.item = item;
        }

        public static Criterion<Instance> forItem(ItemPredicate item) {
            return INSTANCE.createCriterion(new LoreTrigger.Instance(Optional.empty(), Optional.of(item)));
        }

        public static Criterion<Instance> forItem(ItemLike item) {
            return forItem(ItemPredicate.Builder.item().of(item).build());
        }

        public static Criterion<Instance> forAny() {
            return INSTANCE.createCriterion(new LoreTrigger.Instance(Optional.empty(), Optional.empty()));
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
