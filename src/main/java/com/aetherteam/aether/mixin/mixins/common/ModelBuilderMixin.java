package com.aetherteam.aether.mixin.mixins.common;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

/**
 * Used only in runData to get around an incorrect missing texture issue with armor trim data generation.
 */
@Mixin(ModelBuilder.class)
public abstract class ModelBuilderMixin<T extends ModelBuilder<T>> {
    @Shadow(remap = false)
    @Final
    protected Map<String, String> textures;

    @Shadow(remap = false)
    protected abstract T self();

    @Inject(at = @At(value = "HEAD"), method = "texture(Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation;)Lnet/neoforged/neoforge/client/model/generators/ModelBuilder;", cancellable = true, remap = false)
    private void texture(String key, ResourceLocation texture, CallbackInfoReturnable<T> cir) {
        this.textures.put(key, texture.toString());
        cir.setReturnValue(this.self());
    }
}
