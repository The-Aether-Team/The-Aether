package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.CloudMinionModel;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class CloudMinionRenderer extends MobRenderer<CloudMinion, CloudMinionModel> {
    private static final ResourceLocation CLOUD_MINION_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/miscellaneous/cloud_minion/cloud_minion.png");

    public CloudMinionRenderer(EntityRendererProvider.Context context) {
        super(context, new CloudMinionModel(context.bakeLayer(AetherModelLayers.CLOUD_MINION)), 0.25F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull CloudMinion cloudMinion) {
        return CLOUD_MINION_TEXTURE;
    }
}
