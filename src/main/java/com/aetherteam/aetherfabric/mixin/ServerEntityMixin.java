package com.aetherteam.aetherfabric.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ServerEntity.class)
public abstract class ServerEntityMixin {
    @Shadow
    @Final
    private Entity entity;

    @Definition(id = "consumer", local = @Local(argsOnly = true, type = Consumer.class))
    @Definition(id = "accept", method = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V")
    @Definition(id = "packet", local = @Local(type = Packet.class))
    @Expression("consumer.accept(packet)")
    @Inject(method = "sendPairingData", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private void aetherFabric$sendExtraSpawnData(ServerPlayer player, Consumer<Packet<ClientGamePacketListener>> consumer, CallbackInfo ci){
        this.entity.aetherFabric$sendPairingData(player, customPacketPayload -> consumer.accept((Packet) ServerPlayNetworking.createS2CPacket(customPacketPayload)));
    }
}
