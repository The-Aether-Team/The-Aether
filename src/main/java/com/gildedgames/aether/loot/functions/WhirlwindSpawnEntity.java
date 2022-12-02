package com.gildedgames.aether.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class WhirlwindSpawnEntity extends LootItemConditionalFunction {
    private final EntityType<?> entityType;
    private final int count;

    protected WhirlwindSpawnEntity(LootItemCondition[] conditions, EntityType<?> entityType, int count) {
        super(conditions);
        this.entityType = entityType;
        this.count = count;
    }

    /**
     * Spawns an entity from a Whirlwind.
     * @param stack The {@link ItemStack} for the loot pool.
     * @param context The {@link LootContext}.
     * @return The {@link ItemStack} for the loot pool.
     */
    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        ServerLevel serverLevel = context.getLevel();
        Vec3 originVec = context.getParamOrNull(LootContextParams.ORIGIN);
        if (originVec != null) {
            for (int i = 0; i < this.count; i++) {
                Entity entity = this.entityType.create(serverLevel);
                if (entity != null) {
                    entity.moveTo(originVec.x(), originVec.y() + 0.5, originVec.z(), ((float) Math.random()) * 360.0F, 0.0F);
                    entity.setDeltaMovement((Math.random() - Math.random()) * 0.125, entity.getDeltaMovement().y(), (Math.random() - Math.random()) * 0.125);
                    serverLevel.addFreshEntity(entity);
                }
            }
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder(EntityType<?> entityType, int count) {
        return LootItemConditionalFunction.simpleBuilder((lootItemConditions) -> new WhirlwindSpawnEntity(lootItemConditions, entityType, count));
    }

    @Override
    public LootItemFunctionType getType() {
        return AetherLootFunctions.WHIRLWIND_SPAWN_ENTITY.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<WhirlwindSpawnEntity> {
        @Override
        public void serialize(JsonObject json, WhirlwindSpawnEntity instance, JsonSerializationContext context) {
            super.serialize(json, instance, context);
            json.addProperty("entity", EntityType.getKey(instance.entityType).toString());
            json.addProperty("count", instance.count);
        }

        @Override
        public WhirlwindSpawnEntity deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
            EntityType<?> entityType = EntityType.byString(GsonHelper.getAsString(json, "entity")).orElseThrow(() -> new JsonSyntaxException("No value present! Is the loot table entity entry incorrect?"));
            int count = GsonHelper.getAsInt(json, "count");
            return new WhirlwindSpawnEntity(conditions, entityType, count);
        }
    }
}
