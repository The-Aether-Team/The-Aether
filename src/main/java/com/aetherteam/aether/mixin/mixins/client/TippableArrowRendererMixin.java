package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.PhoenixArrowAttachment;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TippableArrowRenderer.class)
public class TippableArrowRendererMixin {
    @Unique
    private static final ResourceLocation FLAMING_ARROW_LOCATION = new ResourceLocation(Aether.MODID, "textures/entity/projectile/flaming_arrow.png");

    /**
     * Changes the texture for any arrows if they are marked as Phoenix Arrows by {@link PhoenixArrowAttachment}.
     *
     * @param entity The {@link Arrow} entity.
     * @param cir    The {@link ResourceLocation} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/projectile/Arrow;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
    private void getTextureLocation(Arrow entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity.hasData(AetherDataAttachments.PHOENIX_ARROW) && entity.getData(AetherDataAttachments.PHOENIX_ARROW).isPhoenixArrow()) {
            if (entity.getColor() <= 0) {
                cir.setReturnValue(FLAMING_ARROW_LOCATION);
            }
        }
    }
}
