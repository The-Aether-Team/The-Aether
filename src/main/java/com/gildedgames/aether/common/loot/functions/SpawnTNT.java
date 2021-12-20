package com.gildedgames.aether.common.loot.functions;

import com.gildedgames.aether.common.entity.block.TNTPresentEntity;
import com.gildedgames.aether.common.registry.AetherLoot;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

public class SpawnTNT extends LootItemConditionalFunction
{
    protected SpawnTNT(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        ServerLevel world = context.getLevel();
        Vec3 vector3d = context.getParamOrNull(LootContextParams.ORIGIN);
        if (vector3d != null) {
            TNTPresentEntity tnt = new TNTPresentEntity(world, vector3d.x(), vector3d.y(), vector3d.z());
            world.addFreshEntity(tnt);
            world.playSound(null, new BlockPos(vector3d), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder() {
        return LootItemConditionalFunction.simpleBuilder(SpawnTNT::new);
    }

    @Override
    public LootItemFunctionType getType() {
        return AetherLoot.SPAWN_TNT;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SpawnTNT>
    {
        @Override
        public void serialize(JsonObject object, SpawnTNT functionClazz, JsonSerializationContext serializationContext) {
            super.serialize(object, functionClazz, serializationContext);
        }

        @Override
        public SpawnTNT deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
            return new SpawnTNT(conditionsIn);
        }
    }
}
