package com.gildedgames.aether.common.loot.functions;

import com.gildedgames.aether.common.registry.AetherLoot;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

public class SpawnXP extends LootItemConditionalFunction
{
    protected SpawnXP(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        ServerLevel world = context.getLevel();
        Vec3 vector3d = context.getParamOrNull(LootContextParams.ORIGIN);
        if (vector3d != null) {
            int randomNumber = (int) ((4 * world.random.nextDouble()) + 6);
            while(randomNumber > 0) {
                int i = ExperienceOrb.getExperienceValue(randomNumber);
                randomNumber -= i;
                world.addFreshEntity(new ExperienceOrb(world, vector3d.x(), vector3d.y(), vector3d.z(), i));
            }
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder() {
        return LootItemConditionalFunction.simpleBuilder(SpawnXP::new);
    }

    @Override
    public LootItemFunctionType getType() {
        return AetherLoot.SPAWN_XP.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SpawnXP>
    {
        @Override
        public void serialize(JsonObject object, SpawnXP functionClazz, JsonSerializationContext serializationContext) {
            super.serialize(object, functionClazz, serializationContext);
        }

        @Override
        public SpawnXP deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
            return new SpawnXP(conditionsIn);
        }
    }
}
