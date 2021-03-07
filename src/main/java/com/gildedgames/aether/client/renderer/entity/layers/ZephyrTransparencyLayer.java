package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.ZephyrRenderer;
import com.gildedgames.aether.client.renderer.entity.model.ZephyrModel;
import com.gildedgames.aether.common.entity.monster.ZephyrEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class ZephyrTransparencyLayer extends LayerRenderer<ZephyrEntity, ZephyrModel> {
    private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/zephyr/zephyr_layer.png");

    private final ZephyrRenderer zephyrRenderer;
    private final ZephyrModel zephyrModel = new ZephyrModel();

    public ZephyrTransparencyLayer(IEntityRenderer<ZephyrEntity, ZephyrModel> entityRendererIn) {
        super(entityRendererIn);
        zephyrRenderer = (ZephyrRenderer) entityRendererIn;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ZephyrEntity zephyr, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!zephyr.isInvisible())
        {
//            RenderSystem.enableRescaleNormal();
//            RenderSystem.enableBlend();
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.zephyrRenderer.getEntityModel().copyModelAttributesTo(this.zephyrModel);
            this.zephyrModel.setRotationAngles(zephyr, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(LAYER_TEXTURE));

            this.zephyrModel.render(matrixStackIn, vertexBuilder, packedLightIn, LivingRenderer.getPackedOverlay(zephyr, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
//
//            RenderSystem.disableBlend();
//            RenderSystem.disableRescaleNormal();

            /*RenderManager renderManager = Minecraft.getInstance().getRenderManager();
            renderManager.renderEngine.bindTexture(TRANS);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.zephyrModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
            this.zephyrModel.setModelAttributes(this.zephyrRenderer.getEntityModel());
            this.zephyrModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();*/
        }
    }
}
