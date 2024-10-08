package com.aetherteam.aether.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {
    @Accessor("starBuffer")
    VertexBuffer aether$getStarBuffer();

    @Accessor("skyBuffer")
    VertexBuffer aether$getSkyBuffer();

    @Accessor("cloudBuffer")
    VertexBuffer aether$getCloudBuffer();

    @Accessor("cloudBuffer")
    void aether$setCloudBuffer(VertexBuffer cloudBuffer);

    @Accessor("prevCloudsType")
    CloudStatus aether$getPrevCloudsType();

    @Accessor("prevCloudsType")
    void aether$setPrevCloudsType(CloudStatus prevCloudsType);

    @Accessor("generateClouds")
    boolean aether$isGenerateClouds();

    @Accessor("generateClouds")
    void aether$setGenerateClouds(boolean generateClouds);

    @Invoker
    MeshData callBuildClouds(Tesselator tesselator, double x, double y, double z, Vec3 cloudColor);
}
