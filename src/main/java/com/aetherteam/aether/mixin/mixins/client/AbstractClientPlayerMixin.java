package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.SlotResult;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
    @Unique
    private static final ResourceLocation SWUFF_CAPE_LOCATION = new ResourceLocation(Aether.MODID, "textures/models/accessory/capes/swuff_accessory.png");

    @Inject(at = @At("HEAD"), method = "isCapeLoaded", cancellable = true)
    private void isCapeLoaded(CallbackInfoReturnable<Boolean> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
        if (EquipmentUtil.hasCape(player) && AetherMixinHooks.isCapeVisible(player)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getCloakTextureLocation", cancellable = true)
    private void getCloakTextureLocation(CallbackInfoReturnable<ResourceLocation> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
        SlotResult result = EquipmentUtil.getCape(player);
        if (AetherMixinHooks.isCapeVisible(player) && result != null && result.stack().getItem() instanceof CapeItem capeItem) {
            if (result.stack().getHoverName().getString().equalsIgnoreCase("swuff_'s cape")) {
                cir.setReturnValue(SWUFF_CAPE_LOCATION);
            } else {
                cir.setReturnValue(capeItem.getCapeTexture());
            }
        }
    }
}
