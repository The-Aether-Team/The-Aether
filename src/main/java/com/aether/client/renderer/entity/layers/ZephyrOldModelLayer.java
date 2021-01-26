package com.aether.client.renderer.entity.layers;

import com.aether.Aether;
import com.aether.AetherConfig;
import com.aether.client.renderer.entity.ZephyrRenderer;
import com.aether.client.renderer.entity.model.OldZephyrModel;
import com.aether.client.renderer.entity.model.ZephyrModel;
import com.aether.entity.monster.ZephyrEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class ZephyrOldModelLayer extends LayerRenderer<ZephyrEntity, ZephyrModel> {

    private final OldZephyrModel zephyrModel = new OldZephyrModel();

    private static final ResourceLocation OLD_ZEPHYR = new ResourceLocation(Aether.MODID, "textures/entity/zephyr/zephyr_old.png");

    public ZephyrOldModelLayer(IEntityRenderer<ZephyrEntity, ZephyrModel> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ZephyrEntity zephyr, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!zephyr.isInvisible() && AetherConfig.CLIENT.visual.legacyModels.get()) {
            IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(OLD_ZEPHYR));
//            vertexBuilder.color(1.0F, 1.0F, 1.0F, 1.0F);
            super.getEntityModel().copyModelAttributesTo(zephyrModel);
            this.zephyrModel.render(matrixStackIn, vertexBuilder, packedLightIn, LivingRenderer.getPackedOverlay(zephyr, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }


}
