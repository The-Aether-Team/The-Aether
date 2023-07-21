package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.ValkyrieWingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.ValkyrieModel;
import com.aetherteam.aether.client.renderer.entity.model.ValkyrieWingsModel;
import com.aetherteam.aether.entity.monster.dungeon.Valkyrie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ValkyrieRenderer extends MobRenderer<Valkyrie, ValkyrieModel<Valkyrie>> {
    private static final ResourceLocation VALKYRIE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/valkyrie/valkyrie.png");

    public ValkyrieRenderer(EntityRendererProvider.Context context) {
        super(context, new ValkyrieModel<>(context.bakeLayer(AetherModelLayers.VALKYRIE)), 0.3F);
        this.addLayer(new ValkyrieWingsLayer<>(this, VALKYRIE_TEXTURE, new ValkyrieWingsModel<>(context.bakeLayer(AetherModelLayers.VALKYRIE_WINGS))));
    }

   
    @Override
    public ResourceLocation getTextureLocation(Valkyrie valkyrie) {
        return VALKYRIE_TEXTURE;
    }
}
