package com.gildedgames.aether.client;

import com.gildedgames.aether.Aether;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class AetherRenderTypes {
    private static final Function<ResourceLocation, RenderType> DUNGEON_BLOCK_OVERLAY = Util.memoize((location) -> {
        RenderStateShard.TextureStateShard textureStateShard = new RenderStateShard.TextureStateShard(location, false, false);
        return RenderType.create(new ResourceLocation(Aether.MODID, "dungeon_block_overlay").toString(), DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder()
                .setLightmapState(RenderStateShard.LIGHTMAP)
                .setShaderState(RenderStateShard.RENDERTYPE_CUTOUT_SHADER)
                .setTextureState(textureStateShard)
                .setLayeringState(RenderStateShard.POLYGON_OFFSET_LAYERING)
                .createCompositeState(false));
    });
    private static final List<String> OVERLAY_NAMES = Stream.of("lock", "exclamation", "door", "treasure").toList();
    private static final List<ResourceLocation> OVERLAY_LOCATIONS = OVERLAY_NAMES.stream().map((string) -> new ResourceLocation(Aether.MODID, "textures/block/dungeon/" + string + ".png")).toList();
    private static final List<RenderType> DUNGEON_BLOCK_OVERLAYS = OVERLAY_LOCATIONS.stream().map(AetherRenderTypes::dungeonBlockOverlay).toList();

    private static RenderType dungeonBlockOverlay(ResourceLocation pLocation) {
        return DUNGEON_BLOCK_OVERLAY.apply(pLocation);
    }

    public static RenderType dungeonBlockOverlayType(int i) {
        return DUNGEON_BLOCK_OVERLAYS.get(i);
    }
}
