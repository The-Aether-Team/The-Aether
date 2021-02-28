package com.gildedgames.aether.advancement;

import com.gildedgames.aether.Aether;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class LoreTrigger extends AbstractCriterionTrigger<LoreTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "lore_entry");

    public ResourceLocation getId() {
        return ID;
    }

    public LoreTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        ItemPredicate itemPredicate = ItemPredicate.deserialize(json.get("item"));
        return new LoreTrigger.Instance(entityPredicate, itemPredicate);
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack) {
        this.triggerListeners(player, (instance) -> instance.test(stack));
    }

    public static class Instance extends CriterionInstance
    {
        private final ItemPredicate item;

        public Instance(EntityPredicate.AndPredicate player, ItemPredicate item) {
            super(LoreTrigger.ID, player);
            this.item = item;
        }

        public static LoreTrigger.Instance create(EntityPredicate.AndPredicate player, ItemPredicate item) {
            return new LoreTrigger.Instance(player, item);
        }

        public static LoreTrigger.Instance forItem(ItemPredicate itemConditions) {
            return new LoreTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, itemConditions);
        }

        public static LoreTrigger.Instance forItem(IItemProvider item) {
            ItemPredicate predicate = new ItemPredicate(null, item.asItem(), MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, EnchantmentPredicate.enchantments, EnchantmentPredicate.enchantments, null, NBTPredicate.ANY);

            return forItem(predicate);
        }

        public boolean test(ItemStack stack) {
            return this.item.test(stack);
        }

        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serialize(conditions);
            jsonobject.add("item", this.item.serialize());
            return jsonobject;
        }
    }
}
