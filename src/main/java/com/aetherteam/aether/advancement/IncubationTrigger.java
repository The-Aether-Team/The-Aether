package com.aetherteam.aether.advancement;

import com.aetherteam.aether.Aether;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/**
 * Criterion trigger used for checking when an item has finished incubating.
 */
public class IncubationTrigger extends SimpleCriterionTrigger<IncubationTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "incubation_trigger");
    public static final IncubationTrigger INSTANCE = new IncubationTrigger();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected IncubationTrigger.Instance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("item"));
        return new IncubationTrigger.Instance(predicate, itemPredicate);
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public Instance(ContextAwarePredicate predicate, ItemPredicate item) {
            super(IncubationTrigger.ID, predicate);
            this.item = item;
        }

        public static IncubationTrigger.Instance forItem(ItemPredicate item) {
            return new IncubationTrigger.Instance(ContextAwarePredicate.ANY, item);
        }

        public boolean test(ItemStack stack) {
            return this.item.matches(stack);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            jsonObject.add("item", this.item.serializeToJson());
            return jsonObject;
        }
    }
}
