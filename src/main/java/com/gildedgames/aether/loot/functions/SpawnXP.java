package com.gildedgames.aether.loot.functions;

import com.gildedgames.aether.registry.AetherLoot;
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
    protected ItemStack doApply(ItemStack stack, LootContext context) {
        ServerWorld world = context.getWorld();
        Vector3d vector3d = context.get(LootParameters.field_237457_g_);
        if (vector3d != null) {
            int randomNumber = (int) ((4 * world.rand.nextDouble()) + 6);
            while(randomNumber > 0) {
                int i = ExperienceOrbEntity.getXPSplit(randomNumber);
                randomNumber -= i;
                world.addEntity(new ExperienceOrbEntity(world, vector3d.getX() + 0.5D, vector3d.getY() + 0.5D, vector3d.getZ() + 0.5D, i));
            }
        }
        return stack;
    }

    public static LootFunction.Builder<?> builder() {
        return LootFunction.builder(SpawnXP::new);
    }

    @Override
    public LootFunctionType getFunctionType() {
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
