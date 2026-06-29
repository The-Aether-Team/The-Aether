package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

public class AetherShaders {
    private static ShaderInstance potteryMoaShader;
    private static ShaderInstance voidMoaShader;
    private static ShaderInstance galaxianMoaShader;

    public static void registerShaders(RegisterShadersEvent event) {
        ResourceProvider resourceProvider = event.getResourceProvider();
        try {
            event.registerShader(new ShaderInstance(resourceProvider, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "pottery_moa"), DefaultVertexFormat.NEW_ENTITY), instance -> potteryMoaShader = instance);
            event.registerShader(new ShaderInstance(resourceProvider, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "void_moa"), DefaultVertexFormat.NEW_ENTITY), instance -> voidMoaShader = instance);
            event.registerShader(new ShaderInstance(resourceProvider, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "galaxian_moa"), DefaultVertexFormat.NEW_ENTITY), instance -> galaxianMoaShader = instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ShaderInstance getPotteryMoaShader() {
        return potteryMoaShader;
    }

    public static ShaderInstance getVoidMoaShader() {
        return voidMoaShader;
    }

    public static ShaderInstance getGalaxianMoaShader() {
        return galaxianMoaShader;
    }
}
