package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aetherfabric.pond.EntityExtension;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements EntityExtension {
    /**
     * @see AetherPlayerAttachment#removeAerbunny()
     */
    @Inject(at = @At(value = "HEAD"), method = "disconnect()V")
    private void disconnect(CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        serverPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).removeAerbunny();
    }
}
