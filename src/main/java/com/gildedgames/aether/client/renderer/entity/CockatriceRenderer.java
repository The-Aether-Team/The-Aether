package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.CockatriceLayer;
import com.gildedgames.aether.client.renderer.entity.model.CockatriceModel;
import com.gildedgames.aether.common.entity.monster.CockatriceEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CockatriceRenderer extends MobRenderer<CockatriceEntity, CockatriceModel>{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice.png");

    public CockatriceRenderer(EntityRendererProvider.Context context) {
        super(context, new CockatriceModel(context.bakeLayer(AetherModelLayers.COCKATRICE)), 0.7F);
        this.addLayer(new CockatriceLayer<>(this));
    }

    @Override
	protected void scale(CockatriceEntity cockatrice, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(1.8F, 1.8F, 1.8F);
    }

    @Override
    protected float getBob(CockatriceEntity cockatrice, float partialTicks) {
        float f1 = cockatrice.prevWingRotation + (cockatrice.wingRotation - cockatrice.prevWingRotation) * partialTicks;
        float f2 = cockatrice.prevDestPos + (cockatrice.destPos - cockatrice.prevDestPos) * partialTicks;
        return (Mth.sin(f1) + 1.0F) * f2;
    }

    @Override
    public ResourceLocation getTextureLocation(CockatriceEntity entity) {
        return TEXTURE;
    }

}
