package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.PhoenixArrowAttachment;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TippableArrowRenderer.class)
public class TippableArrowRendererMixin {
    @Unique
    private static final ResourceLocation FLAMING_ARROW_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/projectile/flaming_arrow.png");

    /**
     * Changes the texture for any arrows if they are marked as Phoenix Arrows by {@link PhoenixArrowAttachment}.
     *
     * @param original The original {@link ResourceLocation} of the texture returned by the target method.
     * @param entity The {@link Arrow} entity.
     * @return The {@link ResourceLocation} for the texture,
     * either what it was before or the Phoenix Arrow one in the case that it is a Phoenix Arrow.
     */
    @ModifyReturnValue(at = @At("RETURN"), method = "getTextureLocation(Lnet/minecraft/world/entity/projectile/Arrow;)Lnet/minecraft/resources/ResourceLocation;")
    private ResourceLocation getTextureLocation(ResourceLocation original, @Local(ordinal = 0, argsOnly = true) Arrow entity) {
        if (entity.hasData(AetherDataAttachments.PHOENIX_ARROW) && entity.getData(AetherDataAttachments.PHOENIX_ARROW).isPhoenixArrow() && entity.getColor() <= 0)
            return FLAMING_ARROW_LOCATION;
        return original;
    }
}
