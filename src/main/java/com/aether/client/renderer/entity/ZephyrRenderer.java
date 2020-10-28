package com.aether.client.renderer.entity;

import com.aether.Aether;
import com.aether.client.renderer.entity.layers.ZephyrTransparencyLayer;
import com.aether.client.renderer.entity.model.ZephyrModel;
import com.aether.entity.monster.ZephyrEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ZephyrRenderer extends MobRenderer<ZephyrEntity, ZephyrModel> {
    private static final ResourceLocation ZEPHYR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/zephyr/zephyr_main.png");
    public ZephyrRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ZephyrModel(), 0.5F);
        addLayer(new ZephyrTransparencyLayer(this));
    }

    @Override
    protected void preRenderCallback(ZephyrEntity zephyr, MatrixStack matrixStackIn, float partialTickTime) {
        float f1 = ((float) zephyr.getAttackCharge() + partialTickTime) / 20.0F;
        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        f1 = 1.0F / (f1 * f1 * f1 * f1 * f1 * 2.0F + 1.0F);

        float f2 = (8.0F + f1) / 2.0F;
        float f3 = (8.0F + 1.0F / f1) / 2.0F;
        matrixStackIn.scale(f3, f2, f3);
        matrixStackIn.translate(0, 0.5D, 0);

        /*if (AetherConfig.visual_options.legacy_models) TODO: Re-enable this when the config is put back in.
        {
            matrixStackIn.scale(0.8F, 0.8F, 0.8F);
            matrixStackIn.translate(0, -0.1D, 0);
        }*/

    }

    @Override
    public ResourceLocation getEntityTexture(ZephyrEntity entity) {
        return ZEPHYR_TEXTURE;
    }
}
