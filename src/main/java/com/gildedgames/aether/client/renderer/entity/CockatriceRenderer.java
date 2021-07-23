package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.layers.CockatriceLayer;
import com.gildedgames.aether.client.renderer.entity.model.CockatriceModel;
import com.gildedgames.aether.common.entity.monster.CockatriceEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class CockatriceRenderer extends MobRenderer<CockatriceEntity, CockatriceModel>{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice.png");

    public CockatriceRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new CockatriceModel(), 0.7F);
        this.addLayer(new CockatriceLayer<>(this));
    }

    @Override
	protected void scale(CockatriceEntity cockatrice, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(1.8F, 1.8F, 1.8F);
    }

    @Override
    protected float getBob(CockatriceEntity cockatrice, float partialTicks) {
        float f1 = cockatrice.prevWingRotation + (cockatrice.wingRotation - cockatrice.prevWingRotation) * partialTicks;
        float f2 = cockatrice.prevDestPos + (cockatrice.destPos - cockatrice.prevDestPos) * partialTicks;
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    @Override
    public ResourceLocation getTextureLocation(CockatriceEntity entity) {
        return TEXTURE;
    }

}
