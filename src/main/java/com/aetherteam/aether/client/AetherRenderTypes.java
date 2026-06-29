package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterRenderBuffersEvent;

public class AetherRenderTypes {
    public static final ResourceLocation ENCHANTED_GLINT_MOA = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/misc/enchanted_glint_moa.png");

    public static final RenderType POTTERY_MOA = RenderType.create("aether:pottery_moa",
        DefaultVertexFormat.POSITION_TEX,
        VertexFormat.Mode.QUADS,
        1536,
        RenderType.CompositeState.builder()
            .setShaderState(new RenderStateShard.ShaderStateShard(AetherShaders::getPotteryMoaShader))
            .setTextureState(new RenderStateShard.TextureStateShard(ENCHANTED_GLINT_MOA, true, false))
            .setWriteMaskState(RenderType.COLOR_WRITE)
            .setCullState(RenderType.NO_CULL)
            .setDepthTestState(RenderType.EQUAL_DEPTH_TEST)
            .setTransparencyState(RenderType.GLINT_TRANSPARENCY)
            .setOutputState(RenderType.ITEM_ENTITY_TARGET)
            .setTexturingState(RenderType.GLINT_TEXTURING)
            .createCompositeState(false)
    );
    public static final RenderType VOID_MOA = RenderType.create("aether:void_moa",
        DefaultVertexFormat.NEW_ENTITY,
        VertexFormat.Mode.QUADS,
        1536,
        false,
        false,
        RenderType.CompositeState.builder()
            .setShaderState(new RenderStateShard.ShaderStateShard(AetherShaders::getVoidMoaShader))
            .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                .add(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/moa/skins/void_moa/void_moa_mask.png"), false, false)
                .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                .build())
            .setCullState(RenderType.NO_CULL)
            .createCompositeState(false)
    );
    public static final RenderType GALAXIAN_MOA = RenderType.create("aether:galaxian_moa",
        DefaultVertexFormat.NEW_ENTITY,
        VertexFormat.Mode.QUADS,
        1536,
        false,
        false,
        RenderType.CompositeState.builder()
            .setShaderState(new RenderStateShard.ShaderStateShard(AetherShaders::getGalaxianMoaShader))
            .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                .add(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/moa/skins/galaxian_moa/galaxian_moa_mask.png"), false, false)
                .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                .build())
            .setCullState(RenderType.NO_CULL)
            .createCompositeState(false)
    );

    public static RenderType potteryMoa() {
        return POTTERY_MOA;
    }

    public static RenderType voidMoa() {
        return VOID_MOA;
    }

    public static RenderType galaxianMoa() {
        return GALAXIAN_MOA;
    }

    public static void registerRenderBuffers(RegisterRenderBuffersEvent event) {
        event.registerRenderBuffer(potteryMoa());
        event.registerRenderBuffer(voidMoa());
        event.registerRenderBuffer(galaxianMoa());
    }
}
