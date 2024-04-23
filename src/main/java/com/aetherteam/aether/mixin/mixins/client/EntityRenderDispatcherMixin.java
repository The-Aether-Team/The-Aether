package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.AetherRenderers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Inject(method = "onResourceManagerReload", at = @At("TAIL"))
    private void registerTrinketModels(ResourceManager resourceManager, CallbackInfo ci) {
        AetherRenderers.registerCuriosRenderers();
    }
}
