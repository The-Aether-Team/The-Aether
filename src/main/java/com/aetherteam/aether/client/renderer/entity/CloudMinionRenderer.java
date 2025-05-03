package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.CloudMinionModel;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class CloudMinionRenderer extends MobRenderer<CloudMinion, LivingEntityRenderState, CloudMinionModel> {
    private static final ResourceLocation CLOUD_MINION_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/miscellaneous/cloud_minion/cloud_minion.png");

    public CloudMinionRenderer(EntityRendererProvider.Context context) {
        super(context, new CloudMinionModel(context.bakeLayer(AetherModelLayers.CLOUD_MINION)), 0.25F);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState cloudMinion) {
        return CLOUD_MINION_TEXTURE;
    }
}
