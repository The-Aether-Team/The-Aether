package com.aether.loot.functions;

import com.aether.entity.block.TNTPresentEntity;
import com.aether.network.AetherPacketHandler;
import com.aether.registry.AetherLoot;
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
import net.minecraftforge.fml.network.PacketDistributor;

public class SpawnTNT extends LootFunction
{
    protected SpawnTNT(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ItemStack doApply(ItemStack stack, LootContext context) {
        ServerWorld world = context.getWorld();
        Vector3d vector3d = context.get(LootParameters.field_237457_g_);
        if (vector3d != null) {
            TNTPresentEntity tnt = new TNTPresentEntity(world, vector3d.getX(), vector3d.getY(), vector3d.getZ());
            world.addEntity(tnt);
            world.playSound(null, new BlockPos(vector3d), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return stack;
    }

    public static LootFunction.Builder<?> builder() {
        return LootFunction.builder(SpawnTNT::new);
    }

    @Override
    public LootFunctionType getFunctionType() {
        return AetherLoot.SPAWN_ENTITY;
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
