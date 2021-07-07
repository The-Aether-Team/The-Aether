package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.Aether;
import net.minecraft.client.renderer.entity.TippedArrowRenderer;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TippedArrowRenderer.class)
public class TippedArrowRendererMixin
{
    @Unique
    private static final ResourceLocation FLAMING_ARROW_LOCATION = new ResourceLocation(Aether.MODID, "textures/entity/projectile/flaming_arrow.png");

    @Inject(at = @At("HEAD"), method = "getTextureLocation", cancellable = true)
    private void getTextureLocation(ArrowEntity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity.getColor() <= 0) {
            cir.setReturnValue(FLAMING_ARROW_LOCATION);
        }
    }
}
