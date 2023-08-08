package com.aetherteam.aether.client.renderer.player.layer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.ValkyrieWingsModel;
import com.aetherteam.aether.item.EquipmentUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class PlayerWingsLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation VALKYRIE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/valkyrie/valkyrie.png");
    private final ValkyrieWingsModel<Player> wings;

    public PlayerWingsLayer(RenderLayerParent<T, M> entityRenderer, EntityModelSet modelSet) {
        super(entityRenderer);
        this.wings = new ValkyrieWingsModel<>(modelSet.bakeLayer(AetherModelLayers.VALKYRIE_ARMOR_WINGS));
    }

    /**
     * Renders wings for the player when wearing Valkyrie Armor.
     * @param poseStack The rendering {@link PoseStack}.
     * @param buffer The rendering {@link MultiBufferSource}.
     * @param packedLight The {@link Integer} for the packed lighting for rendering.
     * @param entity The entity.
     * @param limbSwing The {@link Float} for the limb swing rotation.
     * @param limbSwingAmount The {@link Float} for the limb swing amount.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     * @param ageInTicks The {@link Float} for the entity's age in ticks.
     * @param netHeadYaw The {@link Float} for the head yaw rotation.
     * @param headPitch The {@link Float} for the head pitch rotation.
     */
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (EquipmentUtil.hasFullValkyrieSet(entity)) {
            AetherPlayer.get(entity).ifPresent((aetherPlayer) -> {
                this.setupWingRotation(entity, Mth.lerp(partialTicks, aetherPlayer.getWingRotationO(), aetherPlayer.getWingRotation()));
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(VALKYRIE_TEXTURE));
                this.wings.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            });
        }
    }

    /**
     * Handles the wings' rotation.
     * @param entity The entity.
     * @param sinage The {@link Float} for the rotation value.
     */
    public void setupWingRotation(T entity, float sinage) {
        if (!entity.onGround() && !entity.isInFluidType() && (entity.getVehicle() != null && !entity.getVehicle().onGround())) {
            sinage *= 1.5F;
        } else {
            sinage *= 0.3F;
        }

        this.wings.rightWing.yRot = 0.4F;
        this.wings.rightWing.zRot = 0.125F;
        this.wings.leftWing.yRot = -0.4F;
        this.wings.leftWing.zRot = -0.125F;

        if (entity.isCrouching()) {
            this.wings.rightWing.xRot = 0.45F;
            this.wings.rightWing.y = 3.33F;
            this.wings.rightWing.z = 3.388F;
            this.wings.leftWing.xRot = 0.45F;
            this.wings.leftWing.y = 3.33F;
            this.wings.leftWing.z = 3.388F;
        } else {
            this.wings.rightWing.xRot = 0.0F;
            this.wings.leftWing.xRot = 0.0F;
        }

        this.wings.rightWing.yRot -= Mth.sin(sinage) / 6.0F;
        this.wings.rightWing.zRot -= Mth.cos(sinage) / (entity.onGround() || entity.isInFluidType() ? 8.0F : 3.0F);
        this.wings.leftWing.yRot += Mth.sin(sinage) / 6.0F;
        this.wings.leftWing.zRot += Mth.cos(sinage) / (entity.onGround() || entity.isInFluidType() ? 8.0F : 3.0F);
    }
}
