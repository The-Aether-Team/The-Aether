package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffWoolModel;
import com.aetherteam.aether.client.renderer.entity.state.SheepuffRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.SheepWoolLayer}.
 */
public class SheepuffWoolLayer extends RenderLayer<SheepuffRenderState, SheepuffModel> {
    private static final ResourceLocation SHEEPUFF_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff_wool.png");
    private final EntityModel<SheepuffRenderState> adultModel;
    private final EntityModel<SheepuffRenderState> babyModel;
    private final EntityModel<SheepuffRenderState> adultPuffModel;
    private final EntityModel<SheepuffRenderState> babyPuffModel;

    public SheepuffWoolLayer(RenderLayerParent<SheepuffRenderState, SheepuffModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.adultModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_WOOL));
        this.babyModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_BABY_WOOL));
        this.adultPuffModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_WOOL));
        this.babyPuffModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_BABY_WOOL));
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, SheepuffRenderState renderState, float yRot, float xRot) {
        if (!renderState.isSheared) {
            EntityModel<SheepuffRenderState> entitymodel = renderState.isBaby ? this.babyModel : this.adultModel;
            if (renderState.puff) {
                entitymodel = renderState.isBaby ? this.babyPuffModel : this.adultPuffModel;
            }
            if (renderState.isInvisible) {
                if (renderState.appearsGlowing) {
                    entitymodel.setupAnim(renderState);
                    VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.outline(SHEEPUFF_WOOL_TEXTURE));
                    entitymodel.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(renderState, 0.0F), -16777216);
                }
            } else {
                int i;
                if (renderState.customName != null && "jeb_".equals(renderState.customName.getString())) {
                    int j = 25;
                    int k = Mth.floor(renderState.ageInTicks);
                    int l = k / 25 + renderState.id;
                    int i1 = DyeColor.values().length;
                    int j1 = l % i1;
                    int k1 = (l + 1) % i1;
                    float f = ((float) (k % 25) + Mth.frac(renderState.ageInTicks)) / 25.0F;
                    int l1 = Sheep.getColor(DyeColor.byId(j1));
                    int i2 = Sheep.getColor(DyeColor.byId(k1));
                    i = ARGB.lerp(f, l1, i2);
                } else {
                    i = Sheep.getColor(renderState.woolColor);
                }

                coloredCutoutModelCopyLayerRender(entitymodel, SHEEPUFF_WOOL_TEXTURE, poseStack, bufferSource, packedLight, renderState, i);
            }
        }
    }
}
