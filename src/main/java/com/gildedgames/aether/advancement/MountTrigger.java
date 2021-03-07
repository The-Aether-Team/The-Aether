package com.gildedgames.aether.advancement;

import com.gildedgames.aether.Aether;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SummonedEntityTrigger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MountTrigger extends AbstractCriterionTrigger<MountTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "mount_entity");

    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected MountTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        EntityPredicate.AndPredicate predicate = EntityPredicate.AndPredicate.deserializeJSONObject(json, "entity", conditionsParser);
        return new MountTrigger.Instance(entityPredicate, predicate);
    }

    public void trigger(ServerPlayerEntity player, Entity entity) {
        LootContext lootcontext = EntityPredicate.getLootContext(player, entity);
        this.triggerListeners(player, (instance) -> instance.test(lootcontext));
    }

    public static class Instance extends CriterionInstance
    {
        private final EntityPredicate.AndPredicate entity;

        public Instance(EntityPredicate.AndPredicate playerPredicate, EntityPredicate.AndPredicate entity) {
            super(MountTrigger.ID, playerPredicate);
            this.entity = entity;
        }

        public static MountTrigger.Instance forEntity(EntityPredicate.Builder entity) {
            return new MountTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.createAndFromEntityCondition(entity.build()));
        }

        public boolean test(LootContext context) {
            return this.entity.testContext(context);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serialize(conditions);
            jsonobject.add("entity", this.entity.serializeConditions(conditions));
            return jsonobject;
        }
    }
}
