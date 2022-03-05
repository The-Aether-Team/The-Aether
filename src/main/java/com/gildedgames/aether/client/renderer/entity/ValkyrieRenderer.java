package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.ValkyrieWingsLayer;
import com.gildedgames.aether.client.renderer.entity.model.ValkyrieModel;
import com.gildedgames.aether.client.renderer.entity.model.ValkyrieWingsModel;
import com.gildedgames.aether.common.entity.monster.dungeon.Valkyrie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class ValkyrieRenderer extends MobRenderer<Valkyrie, ValkyrieModel> {
    private static final ResourceLocation VALKYRIE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/valkyrie/valkyrie.png");

    public ValkyrieRenderer(EntityRendererProvider.Context context) {
        super(context, new ValkyrieModel(context.bakeLayer(AetherModelLayers.VALKYRIE)), 0.3F);
        this.addLayer(new ValkyrieWingsLayer(this, new ValkyrieWingsModel<>(context.bakeLayer(AetherModelLayers.VALKYRIE_WINGS))));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Valkyrie valkyrie) {
        return VALKYRIE_TEXTURE;
    }
}
