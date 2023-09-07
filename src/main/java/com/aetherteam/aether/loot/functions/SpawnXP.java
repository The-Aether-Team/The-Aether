package com.aetherteam.aether.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class SpawnXP extends LootItemConditionalFunction {
    protected SpawnXP(LootItemCondition[] conditions) {
        super(conditions);
    }

    /**
     * Spawns experience orbs that give 6-9 experience.
     * @param stack The {@link ItemStack} for the loot pool.
     * @param context The {@link LootContext}.
     * @return The {@link ItemStack} for the loot pool.
     */
    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        ServerLevel serverLevel = context.getLevel();
        Vec3 originVec = context.getParamOrNull(LootContextParams.ORIGIN);
        if (originVec != null) {
            int randomNumber = (int) ((4 * serverLevel.getRandom().nextDouble()) + 6);
            while (randomNumber > 0) {
                int i = ExperienceOrb.getExperienceValue(randomNumber);
                randomNumber -= i;
                serverLevel.addFreshEntity(new ExperienceOrb(serverLevel, originVec.x(), originVec.y(), originVec.z(), i));
            }
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder() {
        return LootItemConditionalFunction.simpleBuilder(SpawnXP::new);
    }

    @Override
    public LootItemFunctionType getType() {
        return AetherLootFunctions.SPAWN_XP.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SpawnXP> {
        @Override
        public void serialize(JsonObject json, SpawnXP instance, JsonSerializationContext context) {
            super.serialize(json, instance, context);
        }

        @Override
        public SpawnXP deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
            return new SpawnXP(conditions);
        }
    }
}
