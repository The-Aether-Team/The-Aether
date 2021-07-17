package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.client.event.listeners.GuiListener;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class ClientEntityMixin {

    @Shadow
    boolean isInsidePortal;

    @Inject(at = @At("HEAD"), method = "canChangeDimensions", cancellable = true)
    public void canChangeDimensions(CallbackInfoReturnable<Boolean> info) {
        /**
         * If you're in the menu you can't change dimensions.  Otherwise it would break the menu screen.
         */
        if (GuiListener.load_level) {
            info.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "changeDimension", cancellable = true)
    public void changeDimension(ServerWorld p_241206_1_, CallbackInfoReturnable<Entity> info) {
        if (GuiListener.load_level) {
            info.setReturnValue((Entity)(Object)this);
        }
    }

    @Inject(at = @At("HEAD"), method = "handleInsidePortal", cancellable = true)
    public void handleInsidePortal(BlockPos p_181015_1_, CallbackInfo info) {
        if (GuiListener.load_level) {
            this.isInsidePortal = false;
            info.cancel();
        }
    }
}
