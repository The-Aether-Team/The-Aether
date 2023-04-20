package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin<T extends LivingEntity>
{
    @Inject(at = @At("HEAD"), method = "getElytraTexture", cancellable = true, remap = false)
    private void getElytraTexture(ItemStack stack, T entity, CallbackInfoReturnable<ResourceLocation> cir) {
        ResourceLocation resourceLocation = AetherMixinHooks.elytraLayerMixin(stack, entity);
        if (resourceLocation != null) {
            cir.setReturnValue(resourceLocation);
        }
    }
}
