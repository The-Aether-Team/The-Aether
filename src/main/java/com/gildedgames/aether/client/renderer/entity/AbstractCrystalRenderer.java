package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.client.renderer.entity.model.CrystalModel;
import com.gildedgames.aether.common.entity.projectile.crystal.AbstractCrystalEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractCrystalRenderer<T extends AbstractCrystalEntity> extends EntityRenderer<T>
{
    private final CrystalModel<AbstractCrystalEntity> model;

    public AbstractCrystalRenderer(EntityRendererProvider.Context renderer, CrystalModel<AbstractCrystalEntity> model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void render(T p_225623_1_, float p_225623_2_, float p_225623_3_, PoseStack p_225623_4_, MultiBufferSource p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        p_225623_4_.translate(0.0D, 0.25D, 0.0D);
        VertexConsumer iVertexBuilder = p_225623_5_.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(p_225623_1_)));
        float f = (float) p_225623_1_.tickCount + p_225623_3_;
        p_225623_4_.mulPose(Vector3f.XP.rotationDegrees(f * 0.1F * 360.0F));
        this.model.crystal[0].render(p_225623_4_, iVertexBuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(f * 0.1F * 360.0F));
        this.model.crystal[1].render(p_225623_4_, iVertexBuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.mulPose(Vector3f.ZP.rotationDegrees(f * 0.1F * 360.0F));
        this.model.crystal[2].render(p_225623_4_, iVertexBuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.popPose();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }
}
