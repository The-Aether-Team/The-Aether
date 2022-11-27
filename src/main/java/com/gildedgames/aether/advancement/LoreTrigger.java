package com.gildedgames.aether.advancement;

import com.gildedgames.aether.Aether;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

public class LoreTrigger extends SimpleCriterionTrigger<LoreTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "lore_entry");
    public static final LoreTrigger INSTANCE = new LoreTrigger();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public LoreTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite entity, DeserializationContext context) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("item"));
        return new LoreTrigger.Instance(entity, itemPredicate);
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public Instance(EntityPredicate.Composite entity, ItemPredicate item) {
            super(LoreTrigger.ID, entity);
            this.item = item;
        }

        public static LoreTrigger.Instance forItem(ItemPredicate item) {
            return new LoreTrigger.Instance(EntityPredicate.Composite.ANY, item);
        }

        public static LoreTrigger.Instance forItem(ItemLike item) {
            ItemPredicate itemPredicate = new ItemPredicate(null, ImmutableSet.of(item.asItem()), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY);
            return forItem(itemPredicate);
        }

        public static LoreTrigger.Instance forAny() {
            return forItem(ItemPredicate.ANY);
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
