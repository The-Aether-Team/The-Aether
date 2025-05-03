package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.HaloModel;
import com.aetherteam.aether.client.renderer.entity.state.PhygRenderState;
import com.aetherteam.aether.entity.passive.Phyg;
import com.aetherteam.aether.mixin.mixins.client.accessor.QuadrupedModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PhygHaloLayer extends RenderLayer<PhygRenderState, PigModel> {
    private static final ResourceLocation HALO_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/perks/halo.png");
    private final HaloModel<PhygRenderState> phygHalo;

    public PhygHaloLayer(RenderLayerParent<PhygRenderState, PigModel> entityRenderer, HaloModel<PhygRenderState> haloModel) {
        super(entityRenderer);
        this.phygHalo = haloModel;
    }

    /**
     * If the Phyg is named "KingPhygieBoo", a Halo is rendered on top of the Phyg's head.
     *
     * @param poseStack       The rendering {@link PoseStack}.
     * @param buffer          The rendering {@link MultiBufferSource}.
     * @param packedLight     The {@link Integer} for the packed lighting for rendering.
     * @param phyg            The {@link Phyg} entity.
     * @param netHeadYaw      The {@link Float} for the head yaw rotation.
     * @param headPitch       The {@link Float} for the head pitch rotation.
     */
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, PhygRenderState phyg, float netHeadYaw, float headPitch) {
        if (phyg.customName != null && phyg.customName.getString().equals("KingPhygieBoo")) {
            QuadrupedModelAccessor quadrupedModelAccessor = (QuadrupedModelAccessor) this.getParentModel();
            this.phygHalo.halo.yRot = quadrupedModelAccessor.aether$getHead().yRot;
            this.phygHalo.halo.xRot = quadrupedModelAccessor.aether$getHead().xRot;
            this.phygHalo.setupAnim(phyg);
            VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(HALO_LOCATION));
            this.phygHalo.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1073741823);
        }
    }
}
