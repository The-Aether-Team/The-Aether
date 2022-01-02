package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.common.registry.AetherDimensions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nullable;

//TODO: Update this class to follow mapping conventions whenever it is fixed.
//@Mixin(LevelRenderer.class)
//public class WorldRendererMixin
//{
//    @Nullable
//    @Shadow
//    private ClientLevel level;
//
//    @ModifyVariable(at = @At(value = "STORE", ordinal = 0, args = { "log=true" }), method = "renderSky", ordinal = 1)
//    public double renderSky(double d0) {
//        if (this.level != null && this.level.dimension() == AetherDimensions.AETHER_WORLD) {
//            return 1.0D;
//        }
//        return d0;
//    }
//}