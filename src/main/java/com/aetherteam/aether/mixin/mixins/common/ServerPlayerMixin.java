package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    /**
     * @see AetherPlayerAttachment#removeAerbunny()
     */
    @Inject(at = @At(value = "HEAD"), method = "disconnect()V")
    private void disconnect(CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        serverPlayer.getData(AetherDataAttachments.AETHER_PLAYER).removeAerbunny();
    }
}
