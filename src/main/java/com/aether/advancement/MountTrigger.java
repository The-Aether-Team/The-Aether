package com.aether.advancement;

import com.aether.Aether;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class MountTrigger implements ICriterionTrigger<MountTrigger.Instance> {
    private final Map<PlayerAdvancements, Listeners> listeners = Maps.newHashMap();
    private static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "mount_entity");

    public ResourceLocation getId() {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<MountTrigger.Instance> listener) {
        MountTrigger.Listeners mountListeners = this.listeners.get(playerAdvancementsIn);

        if (mountListeners == null) {
            mountListeners = new MountTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, mountListeners);
        }

        mountListeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<MountTrigger.Instance> listener) {
        MountTrigger.Listeners mountListeners = this.listeners.get(playerAdvancementsIn);

        if (mountListeners != null) {
            mountListeners.remove(listener);

            if (mountListeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public Instance deserialize(JsonObject json, ConditionArrayParser conditions) {
        EntityPredicate.AndPredicate playerPredicate = EntityPredicate.AndPredicate.deserializeJSONObject(json, "player", conditions);
        return new MountTrigger.Instance(playerPredicate, EntityPredicate.deserialize(json.get("entity")));
    }

    public void trigger(ServerPlayerEntity player, Entity entity) {
        MountTrigger.Listeners mountListener = this.listeners.get(player.getAdvancements());

        if (mountListener != null) {
            mountListener.trigger(player, entity);
        }
    }

    public static class Instance extends CriterionInstance {
        private final EntityPredicate entity;

        public Instance(EntityPredicate.AndPredicate playerPredicate, EntityPredicate entity) {
            super(ID, playerPredicate);

            this.entity = entity;
        }

        public boolean test(ServerPlayerEntity player, Entity entity) {
            return this.entity.test(player, entity);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<MountTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<MountTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(ServerPlayerEntity player, Entity entity) {
            List<Listener<Instance>> list = null;

            for (ICriterionTrigger.Listener<MountTrigger.Instance> listener : this.listeners) {
                if (listener.getCriterionInstance().test(player, entity)) {
                    if (list == null) {
                        list = Lists.newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (ICriterionTrigger.Listener<MountTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
