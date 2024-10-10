package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerSkin.class)
public class PlayerSkinMixin {
    /**
     * Sets the cape texture to use when a cape accessory is equipped and visible. This is also used by Elytra.
     *
     * @param original The original {@link ResourceLocation} of the texture from this method.
     * @return The texture of the cape, if equipped and visible, otherwise the original cape texture.
     */
    @ModifyReturnValue(at = @At("RETURN"), method = "capeTexture")
    private ResourceLocation capeTexture(ResourceLocation original) {
        Player player = Minecraft.getInstance().player;
        SlotEntryReference result = EquipmentUtil.getCape(player);
        if (result != null && AetherMixinHooks.isCapeVisible(player)) {
            ResourceLocation texture = AetherMixinHooks.getCapeTexture(result.stack());
            if (texture != null)
                return texture;
        }
        return original;
    }
}
