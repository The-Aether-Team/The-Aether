/*
package com.aether.client.renderer.entity;

import com.aether.Aether;
import com.aether.client.renderer.entity.model.AerwhaleModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class AerwhaleRenderer extends MobRenderer<AerwhaleEntity, AerwhaleModel> {
    private static final ResourceLocation AERWHALE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/aerwhale/aerwhale.png");
    private static final ResourceLocation OLD_AERWHALE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/aerwhale/old_aerwhale.png");

    public AerwhaleRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new AerwhaleModel(), 0.5F);
    }

    @Override
    protected void preRenderCallback(AerwhaleEntity aerwhale, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.translate(0, 1.2D, 0);
        matrixStackIn.rotate(new Quaternion(-90.0F, 0.0F, 1.0F, 0.0F));
        matrixStackIn.scale(2.0F, 2.0F, 2.0F);

    }

    @Override   //TODO: Configurable old aerwhale model and texture
    public ResourceLocation getEntityTexture(AerwhaleEntity entity) {
        return AERWHALE_TEXTURE;
    }
}
*/
