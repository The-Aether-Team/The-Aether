package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.SlotResult;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
    /**
     * Sets the player as having a loaded cape if they have a cape accessory equipped and visible.
     * @param cir The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At("HEAD"), method = "isCapeLoaded()Z", cancellable = true)
    private void isCapeLoaded(CallbackInfoReturnable<Boolean> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
        if (EquipmentUtil.hasCape(player) && AetherMixinHooks.isCapeVisible(player)) {
            cir.setReturnValue(true);
        }
    }

    /**
     * Sets the cape texture to use when a cape accessory is equipped and visible. This is also used by Elytra.
     * @param cir The {@link ResourceLocation} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At("HEAD"), method = "getCloakTextureLocation()Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
    private void getCloakTextureLocation(CallbackInfoReturnable<ResourceLocation> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
        SlotResult result = EquipmentUtil.getCape(player);
        if (result != null && AetherMixinHooks.isCapeVisible(player)) {
            ResourceLocation texture = AetherMixinHooks.getCapeTexture(result.stack());
            if (texture != null) {
                cir.setReturnValue(texture);
            }
        }
    }
}
