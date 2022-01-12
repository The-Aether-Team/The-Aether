package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.ZephyrRenderer;
import com.gildedgames.aether.client.renderer.entity.model.BaseZephyrModel;
import com.gildedgames.aether.client.renderer.entity.model.ZephyrModel;
import com.gildedgames.aether.common.entity.monster.Zephyr;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class ZephyrTransparencyLayer extends RenderLayer<Zephyr, BaseZephyrModel> {
    private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/zephyr/zephyr_layer.png");

    private final ZephyrRenderer zephyrRenderer;
    private final BaseZephyrModel zephyrModel;

    public ZephyrTransparencyLayer(RenderLayerParent<Zephyr, BaseZephyrModel> entityRendererIn, EntityModelSet modelSet) {
        super(entityRendererIn);
        zephyrRenderer = (ZephyrRenderer) entityRendererIn;
        zephyrModel= new ZephyrModel(modelSet.bakeLayer(AetherModelLayers.ZEPHYR_TRANSPARENCY));
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Zephyr zephyr, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!zephyr.isInvisible())
        {
//            RenderSystem.enableRescaleNormal();
//            RenderSystem.enableBlend();
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.zephyrRenderer.getModel().copyPropertiesTo(this.zephyrModel);
            this.zephyrModel.setupAnim(zephyr, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer vertexBuilder = bufferIn.getBuffer(RenderType.entityTranslucent(LAYER_TEXTURE));

            this.zephyrModel.renderToBuffer(matrixStackIn, vertexBuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(zephyr, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
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
