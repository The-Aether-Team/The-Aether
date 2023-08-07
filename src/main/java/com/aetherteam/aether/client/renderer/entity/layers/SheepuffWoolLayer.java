package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffWoolModel;
import com.aetherteam.aether.entity.passive.Sheepuff;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.SheepFurLayer}.
 */
public class SheepuffWoolLayer extends RenderLayer<Sheepuff, SheepuffModel> {
    private static final ResourceLocation SHEEPUFF_WOOL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff_wool.png");
    private final SheepuffWoolModel wool;
    private final SheepuffWoolModel puffed;

    public SheepuffWoolLayer(RenderLayerParent<Sheepuff, SheepuffModel> entityRenderer, SheepuffWoolModel woolModel, SheepuffWoolModel puffedModel) {
        super(entityRenderer);
        this.wool = woolModel;
        this.puffed = puffedModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!sheepuff.isSheared()) {
            SheepuffWoolModel woolModel = sheepuff.getPuffed() ? this.puffed : this.wool;
            if (sheepuff.isInvisible()) {
                Minecraft minecraft = Minecraft.getInstance();
                boolean flag = minecraft.shouldEntityAppearGlowing(sheepuff);
                if (flag) {
                    this.getParentModel().copyPropertiesTo(woolModel);
                    woolModel.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTicks);
                    woolModel.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    VertexConsumer consumer = buffer.getBuffer(RenderType.outline(SHEEPUFF_WOOL_TEXTURE));
                    woolModel.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheepuff, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
                }
            } else {
                float f;
                float f1;
                float f2;
                if (sheepuff.hasCustomName() && sheepuff.getName().getString().equals("jeb_")) {
                    int i1 = 25;
                    int i = sheepuff.tickCount / i1 + sheepuff.getId();
                    int j = DyeColor.values().length;
                    int k = i % j;
                    int l = (i + 1) % j;
                    float f3 = ((float) (sheepuff.tickCount % i1) + partialTicks) / (float) i1;
                    float[] afloat1 = Sheepuff.getColorArray(DyeColor.byId(k));
                    float[] afloat2 = Sheepuff.getColorArray(DyeColor.byId(l));
                    f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                    f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                    f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
                } else {
                    float[] afloat = Sheepuff.getColorArray(sheepuff.getColor());
                    f = afloat[0];
                    f1 = afloat[1];
                    f2 = afloat[2];
                }
                coloredCutoutModelCopyLayerRender(this.getParentModel(), woolModel, SHEEPUFF_WOOL_TEXTURE, poseStack, buffer, packedLight, sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f, f1, f2);
            }
        }
    }
}
