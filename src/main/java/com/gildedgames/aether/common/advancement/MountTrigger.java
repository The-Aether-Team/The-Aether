package com.gildedgames.aether.common.advancement;

import com.gildedgames.aether.Aether;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class MountTrigger extends AbstractCriterionTrigger<MountTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "mount_entity");
    public static final MountTrigger INSTANCE = new MountTrigger();

    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected MountTrigger.Instance createInstance(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        EntityPredicate.AndPredicate predicate = EntityPredicate.AndPredicate.fromJson(json, "entity", conditionsParser);
        return new MountTrigger.Instance(entityPredicate, predicate);
    }

    public void trigger(ServerPlayerEntity player, Entity entity) {
        LootContext lootcontext = EntityPredicate.createContext(player, entity);
        this.trigger(player, (instance) -> instance.test(lootcontext));
    }

    public static class Instance extends CriterionInstance
    {
        private final EntityPredicate.AndPredicate entity;

        public Instance(EntityPredicate.AndPredicate playerPredicate, EntityPredicate.AndPredicate entity) {
            super(MountTrigger.ID, playerPredicate);
            this.entity = entity;
        }

        public static MountTrigger.Instance forEntity(EntityPredicate.Builder entity) {
            return new MountTrigger.Instance(EntityPredicate.AndPredicate.ANY, EntityPredicate.AndPredicate.wrap(entity.build()));
        }

        public boolean test(LootContext context) {
            return this.entity.matches(context);
        }

        @Override
        public JsonObject serializeToJson(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serializeToJson(conditions);
            jsonobject.add("entity", this.entity.toJson(conditions));
            return jsonobject;
        }
    }
}
