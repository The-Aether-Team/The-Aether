package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    /**
     * Stop the player from rubber banding when riding a floating block
     */
    @Inject(method = "isPlayerCollidingWithAnythingNew", at = @At("RETURN"), cancellable = true)
    void isPlayerNotCollidingWithBlocks(LevelReader worldView, AABB box, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            List<Entity> list = player.level.getEntities(player, player.getBoundingBox());
            for (Entity entity : list) {
                if (entity instanceof FloatingBlockEntity) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
