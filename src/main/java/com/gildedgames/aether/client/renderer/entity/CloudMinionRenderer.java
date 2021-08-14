package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.model.CloudMinionModel;
import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CloudMinionRenderer extends MobRenderer<CloudMinionEntity, CloudMinionModel>
{
    private static final ResourceLocation CLOUD_MINION_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/miscellaneous/cloud_minion/cloud_minion.png");

    public CloudMinionRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new CloudMinionModel(), 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(CloudMinionEntity p_110775_1_) {
        return CLOUD_MINION_TEXTURE;
    }
}
