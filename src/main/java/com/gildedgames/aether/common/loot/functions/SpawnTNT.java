package com.gildedgames.aether.common.loot.functions;

import com.gildedgames.aether.common.entity.block.TNTPresentEntity;
import com.gildedgames.aether.common.registry.AetherLoot;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class SpawnTNT extends LootFunction
{
    protected SpawnTNT(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        ServerWorld world = context.getLevel();
        Vector3d vector3d = context.getParamOrNull(LootParameters.ORIGIN);
        if (vector3d != null) {
            TNTPresentEntity tnt = new TNTPresentEntity(world, vector3d.x(), vector3d.y(), vector3d.z());
            world.addFreshEntity(tnt);
            world.playSound(null, new BlockPos(vector3d), SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return stack;
    }

    public static LootFunction.Builder<?> builder() {
        return LootFunction.simpleBuilder(SpawnTNT::new);
    }

    @Override
    public LootFunctionType getType() {
        return AetherLoot.SPAWN_TNT;
    }

    public static class Serializer extends LootFunction.Serializer<SpawnTNT>
    {
        @Override
        public void serialize(JsonObject object, SpawnTNT functionClazz, JsonSerializationContext serializationContext) {
            super.serialize(object, functionClazz, serializationContext);
        }

        @Override
        public SpawnTNT deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
            return new SpawnTNT(conditionsIn);
        }
    }
}
