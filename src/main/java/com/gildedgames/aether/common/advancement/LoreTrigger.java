package com.gildedgames.aether.common.advancement;

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

public class LoreTrigger extends SimpleCriterionTrigger<LoreTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "lore_entry");
    public static final LoreTrigger INSTANCE = new LoreTrigger();

    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public LoreTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext conditionsParser) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("item"));
        return new LoreTrigger.Instance(entityPredicate, itemPredicate);
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public static class Instance extends AbstractCriterionTriggerInstance
    {
        private final ItemPredicate item;

        public Instance(EntityPredicate.Composite player, ItemPredicate item) {
            super(LoreTrigger.ID, player);
            this.item = item;
        }

        public static LoreTrigger.Instance forItem(ItemPredicate itemConditions) {
            return new LoreTrigger.Instance(EntityPredicate.Composite.ANY, itemConditions);
        }

        public static LoreTrigger.Instance forItem(ItemLike item) {
            ItemPredicate predicate = new ItemPredicate(null, ImmutableSet.of(item.asItem()), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY);
            return forItem(predicate);
        }

        public static LoreTrigger.Instance forAny() {
            return forItem(ItemPredicate.ANY);
        }

        public boolean test(ItemStack stack) {
            return this.item.matches(stack);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonobject = super.serializeToJson(conditions);
            jsonobject.add("item", this.item.serializeToJson());
            return jsonobject;
        }
    }
}
