package com.aetherteam.aether.loot.functions;

import com.aetherteam.aether.entity.block.TntPresent;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class SpawnTNT extends LootItemConditionalFunction {
    protected SpawnTNT(LootItemCondition[] conditions) {
        super(conditions);
    }

    /**
     * Spawns a TNT Present.
     * @param stack The {@link ItemStack} for the loot pool.
     * @param context The {@link LootContext}.
     * @return The {@link ItemStack} for the loot pool.
     */
    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        ServerLevel serverLevel = context.getLevel();
        Vec3 originVec = context.getParamOrNull(LootContextParams.ORIGIN);
        if (originVec != null) {
            TntPresent tnt = new TntPresent(serverLevel, originVec.x(), originVec.y(), originVec.z());
            serverLevel.addFreshEntity(tnt);
            serverLevel.playSound(null, BlockPos.containing(originVec), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder() {
        return LootItemConditionalFunction.simpleBuilder(SpawnTNT::new);
    }

    @Override
    public LootItemFunctionType getType() {
        return AetherLootFunctions.SPAWN_TNT.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SpawnTNT> {
        @Override
        public void serialize(JsonObject json, SpawnTNT instance, JsonSerializationContext context) {
            super.serialize(json, instance, context);
        }

        @Override
        public SpawnTNT deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
            return new SpawnTNT(conditions);
        }
    }
}
