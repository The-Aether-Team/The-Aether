package com.aetherteam.aether.mixin.mixins.common.fabric;

import com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/server/network/ServerGamePacketListenerImpl$1")
public class ServerGamePacketListenerImplMixin {
//    @Inject(method = "method_33898", at = @At("HEAD"))
//    private static void onEntityInteract(Vec3 vec3, ServerPlayer serverPlayer, Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
//        ToolAbilityListener.checkEntityTooFar(entityInteractSpecific, entityInteractSpecific.getTarget(), entityInteractSpecific.getEntity(), entityInteractSpecific.getHand());
//    }
}
