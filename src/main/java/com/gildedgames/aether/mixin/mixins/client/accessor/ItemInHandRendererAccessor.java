package com.gildedgames.aether.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemInHandRenderer.class)
public interface ItemInHandRendererAccessor {
    @Accessor("mainHandItem")
    ItemStack aether$getMainHandItem();

    @Accessor("offHandItem")
    ItemStack aether$getOffHandItem();

    @Invoker
    float callCalculateMapTilt(float pitch);

    @Invoker
    void callRenderMap(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, ItemStack stack);
}