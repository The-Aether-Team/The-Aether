package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.client.renderer.entity.model.CrystalModel;
import com.gildedgames.aether.common.entity.projectile.crystal.AbstractCrystalEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.vector.Vector3f;

public abstract class AbstractCrystalRenderer<T extends AbstractCrystalEntity> extends EntityRenderer<T>
{
    private final CrystalModel<AbstractCrystalEntity> model = new CrystalModel<>();

    public AbstractCrystalRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public void render(T p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        p_225623_4_.translate(0.0D, 0.25D, 0.0D);
        IVertexBuilder iVertexBuilder = p_225623_5_.getBuffer(this.model.renderType(this.getTextureLocation(p_225623_1_)));
        float f = (float) p_225623_1_.tickCount + p_225623_3_;
        p_225623_4_.mulPose(Vector3f.XP.rotationDegrees(f * 0.1F * 360.0F));
        this.model.main[0].render(p_225623_4_, iVertexBuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(f * 0.1F * 360.0F));
        this.model.main[1].render(p_225623_4_, iVertexBuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.mulPose(Vector3f.ZP.rotationDegrees(f * 0.1F * 360.0F));
        this.model.main[2].render(p_225623_4_, iVertexBuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.popPose();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }
}
