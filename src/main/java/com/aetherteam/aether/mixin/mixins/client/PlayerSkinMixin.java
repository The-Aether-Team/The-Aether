package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.SlotResult;

@Mixin(PlayerSkin.class)
public class PlayerSkinMixin {
    /**
     * Sets the cape texture to use when a cape accessory is equipped and visible. This is also used by Elytra.
     * @param cir The {@link ResourceLocation} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At("HEAD"), method = "capeTexture", cancellable = true)
    private void capeTexture(CallbackInfoReturnable<ResourceLocation> cir) {
        Player player = Minecraft.getInstance().player;
        SlotResult result = EquipmentUtil.getCape(player);
        if (result != null && AetherMixinHooks.isCapeVisible(player)) {
            ResourceLocation texture = AetherMixinHooks.getCapeTexture(result.stack());
            if (texture != null) {
                cir.setReturnValue(texture);
            }
        }
    }
}
