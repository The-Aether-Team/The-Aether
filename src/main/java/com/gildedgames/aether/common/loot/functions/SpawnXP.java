package com.gildedgames.aether.common.loot.functions;

import com.gildedgames.aether.common.registry.AetherLoot;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class SpawnXP extends LootFunction
{
    protected SpawnXP(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        ServerWorld world = context.getLevel();
        Vector3d vector3d = context.getParamOrNull(LootParameters.ORIGIN);
        if (vector3d != null) {
            int randomNumber = (int) ((4 * world.random.nextDouble()) + 6);
            while(randomNumber > 0) {
                int i = ExperienceOrbEntity.getExperienceValue(randomNumber);
                randomNumber -= i;
                world.addFreshEntity(new ExperienceOrbEntity(world, vector3d.x() + 0.5D, vector3d.y() + 0.5D, vector3d.z() + 0.5D, i));
            }
        }
        return stack;
    }

    public static LootFunction.Builder<?> builder() {
        return LootFunction.simpleBuilder(SpawnXP::new);
    }

    @Override
    public LootFunctionType getType() {
        return AetherLoot.SPAWN_XP;
    }

    public static class Serializer extends LootFunction.Serializer<SpawnXP>
    {
        @Override
        public void serialize(JsonObject object, SpawnXP functionClazz, JsonSerializationContext serializationContext) {
            super.serialize(object, functionClazz, serializationContext);
        }

        @Override
        public SpawnXP deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
            return new SpawnXP(conditionsIn);
        }
    }
}
