package com.gildedgames.aether.common.loot.functions;

import com.gildedgames.aether.common.registry.AetherLoot;
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

import javax.annotation.Nonnull;

public class WhirlwindSpawnEntity extends LootItemConditionalFunction {
    protected final EntityType<?> entityType;
    protected final int count;
    protected WhirlwindSpawnEntity(LootItemCondition[] pConditions, EntityType<?> pType, int pCount) {
        super(pConditions);
        this.entityType = pType;
        this.count = pCount;
    }

    @Override
    @Nonnull
    protected ItemStack run(@Nonnull ItemStack pStack, LootContext pContext) {
        ServerLevel level = pContext.getLevel();
        Vec3 pos = pContext.getParamOrNull(LootContextParams.ORIGIN);
        if(pos != null) {
            for(int i = 0; i < this.count; i++) {
                Entity entity = this.entityType.create(level);
                entity.moveTo(pos.x, pos.y + 0.5D, pos.z, ((float) Math.random()) * 360F, 0.0F);
                entity.setDeltaMovement((Math.random() - Math.random()) * 0.125D, entity.getDeltaMovement().y, (Math.random() - Math.random()) * 0.125D);
                level.addFreshEntity(entity);
            }
        }
        return pStack;
    }

    @Override
    @Nonnull
    public LootItemFunctionType getType() {
        return AetherLoot.WHIRLWIND_SPAWN_ENTITY.get();
    }

    public static LootItemConditionalFunction.Builder<?> builder(EntityType<?> pEntityType, int pCount) {
        return LootItemConditionalFunction.simpleBuilder((lootItemConditions -> new WhirlwindSpawnEntity(lootItemConditions, pEntityType, pCount)));
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<WhirlwindSpawnEntity> {
        @Override
        public void serialize(@Nonnull JsonObject pJson, @Nonnull WhirlwindSpawnEntity pValue, @Nonnull JsonSerializationContext pSerializationContext) {
            super.serialize(pJson, pValue, pSerializationContext);
            pJson.addProperty("entity", EntityType.getKey(pValue.entityType).toString());
            pJson.addProperty("count", pValue.count);
        }

        @Override
        @Nonnull
        public WhirlwindSpawnEntity deserialize(@Nonnull JsonObject object, @Nonnull JsonDeserializationContext deserializationContext, @Nonnull LootItemCondition[] conditionsIn) {
            try {
                EntityType<?> entityType = EntityType.byString(GsonHelper.getAsString(object, "entity")).orElseThrow(() -> new JsonSyntaxException("No value present! Is the loot table entity entry incorrect?"));
                int count = GsonHelper.getAsInt(object, "count");
                return new WhirlwindSpawnEntity(conditionsIn, entityType, count);
            } catch (JsonSyntaxException exception) {
                throw exception;
            }
        }
    }
}
