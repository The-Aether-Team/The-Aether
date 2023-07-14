package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.capability.arrow.PhoenixArrow;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TippableArrowRenderer.class)
public class TippableArrowRendererMixin
{
    @Unique
    private static final ResourceLocation FLAMING_ARROW_LOCATION = new ResourceLocation(Aether.MODID, "textures/entity/projectile/flaming_arrow.png");

    @Inject(at = @At("HEAD"), method = "getTextureLocation*", cancellable = true)
    private void getTextureLocation(Arrow entity, CallbackInfoReturnable<ResourceLocation> cir) {
        PhoenixArrow.get(entity).ifPresent(phoenixArrow -> {
            if (phoenixArrow.isPhoenixArrow()) {
                if (entity.getColor() <= 0) {
                    cir.setReturnValue(FLAMING_ARROW_LOCATION);
                }
            }
        });
    }
}
