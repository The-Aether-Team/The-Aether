package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.BlockLikeEntity;
import com.aetherteam.aether.api.BlockLikeSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.entity.EntityTickList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {

    @Shadow
    @Final
    EntityTickList entityTickList;
    @Shadow
    private int emptyTime;

    @Inject(method = "tick", at = @At(value = "RETURN"))
    void postEntityTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (this.emptyTime < 300) {
            entityTickList.forEach(entityObj -> {
                if (entityObj instanceof BlockLikeEntity entity) {
                    entity.postTick();
                } else if (entityObj == null) {
                    Aether.LOGGER.error("Started checking null entities in ServerWorldMixin::postEntityTick");
                }
            });
            BlockLikeSet.getAllSets().forEachRemaining(BlockLikeSet::postTick);
        }
    }
}
