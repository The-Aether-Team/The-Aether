package com.gildedgames.aether.common.advancement;

import com.gildedgames.aether.Aether;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.resources.ResourceLocation;

public class MountTrigger extends SimpleCriterionTrigger<MountTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "mount_entity");
    public static final MountTrigger INSTANCE = new MountTrigger();

    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected MountTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext conditionsParser) {
        EntityPredicate.Composite predicate = EntityPredicate.Composite.fromJson(json, "entity", conditionsParser);
        return new MountTrigger.Instance(entityPredicate, predicate);
    }

    public void trigger(ServerPlayer player, Entity entity) {
        LootContext lootcontext = EntityPredicate.createContext(player, entity);
        this.trigger(player, (instance) -> instance.test(lootcontext));
    }

    public static class Instance extends AbstractCriterionTriggerInstance
    {
        private final EntityPredicate.Composite entity;

        public Instance(EntityPredicate.Composite playerPredicate, EntityPredicate.Composite entity) {
            super(MountTrigger.ID, playerPredicate);
            this.entity = entity;
        }

        public static MountTrigger.Instance forEntity(EntityPredicate.Builder entity) {
            return new MountTrigger.Instance(EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(entity.build()));
        }

        public boolean test(LootContext context) {
            return this.entity.matches(context);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonobject = super.serializeToJson(conditions);
            jsonobject.add("entity", this.entity.toJson(conditions));
            return jsonobject;
        }
    }
}
