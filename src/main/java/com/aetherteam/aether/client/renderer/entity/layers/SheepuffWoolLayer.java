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
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.SheepFurLayer}.
 */
public class SheepuffWoolLayer extends RenderLayer<Sheepuff, SheepuffModel> {
    private static final ResourceLocation SHEEPUFF_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff_wool.png");
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
                    woolModel.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheepuff, 0.0F), -16777216);
                }
            } else {
                int i;
                if (sheepuff.hasCustomName() && sheepuff.getName().getString().equals("jeb_")) {
                    int j = 25;
                    int k = sheepuff.tickCount / 25 + sheepuff.getId();
                    int l = DyeColor.values().length;
                    int i1 = k % l;
                    int j1 = (k + 1) % l;
                    float f = ((float) (sheepuff.tickCount % 25) + partialTicks) / 25.0F;
                    int k1 = Sheep.getColor(DyeColor.byId(i1));
                    int l1 = Sheep.getColor(DyeColor.byId(j1));
                    i = FastColor.ARGB32.lerp(f, k1, l1);
                } else {
                    i = Sheep.getColor(sheepuff.getColor());
                }
                coloredCutoutModelCopyLayerRender(this.getParentModel(), woolModel, SHEEPUFF_WOOL_TEXTURE, poseStack, buffer, packedLight, sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, i);
            }
        }
    }
}
