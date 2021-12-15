package com.gildedgames.aether.common.advancement;

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
    public static final LoreTrigger INSTANCE = new LoreTrigger();

    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public LoreTrigger.Instance createInstance(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("item"));
        return new LoreTrigger.Instance(entityPredicate, itemPredicate);
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public static class Instance extends CriterionInstance
    {
        private final ItemPredicate item;

        public Instance(EntityPredicate.AndPredicate player, ItemPredicate item) {
            super(LoreTrigger.ID, player);
            this.item = item;
        }

        public static LoreTrigger.Instance forItem(ItemPredicate itemConditions) {
            return new LoreTrigger.Instance(EntityPredicate.AndPredicate.ANY, itemConditions);
        }

        public static LoreTrigger.Instance forItem(IItemProvider item) {
            ItemPredicate predicate = new ItemPredicate(null, item.asItem(), MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NBTPredicate.ANY);
            return forItem(predicate);
        }

        public static LoreTrigger.Instance forAny() {
            return forItem(ItemPredicate.ANY);
        }

        public boolean test(ItemStack stack) {
            return this.item.matches(stack);
        }

        @Override
        public JsonObject serializeToJson(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serializeToJson(conditions);
            jsonobject.add("item", this.item.serializeToJson());
            return jsonobject;
        }
    }
}
