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
public class TippableArrowRendererMixin {
    @Unique
    private static final ResourceLocation FLAMING_ARROW_LOCATION = new ResourceLocation(Aether.MODID, "textures/entity/projectile/flaming_arrow.png");

    /**
     * Changes the texture for any arrows if they are marked as Phoenix Arrows by {@link com.aetherteam.aether.capability.arrow.PhoenixArrowCapability}.
     * @param entity The {@link Arrow} entity.
     * @param cir The {@link ResourceLocation} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/projectile/Arrow;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
    private void getTextureLocation(Arrow entity, CallbackInfoReturnable<ResourceLocation> cir) {
        PhoenixArrow.get(entity).ifPresent((phoenixArrow) -> {
            if (phoenixArrow.isPhoenixArrow()) {
                Arrow arrow = (Arrow) phoenixArrow.getArrow();
                if (arrow.getColor() <= 0) {
                    cir.setReturnValue(FLAMING_ARROW_LOCATION);
                }
            }
        });
    }
}
