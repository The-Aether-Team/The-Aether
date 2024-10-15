package com.aetherteam.aether.client.renderer.player.layer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.HaloModel;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.aether.perk.data.ClientHaloPerkData;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.shedaniel.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.UUID;

public class PlayerHaloLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation PLAYER_HALO_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/perks/halo.png");
    private static final ResourceLocation PLAYER_HALO_GRAYSCALE_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/perks/halo_grayscale.png");
    private final HaloModel<Player> playerHalo;

    public PlayerHaloLayer(RenderLayerParent<T, M> entityRenderer, EntityModelSet modelSet) {
        super(entityRenderer);
        this.playerHalo = new HaloModel<>(modelSet.bakeLayer(AetherModelLayers.PLAYER_HALO));
    }

    /**
     * If the player has a Halo, this will render it in the {@link AetherCustomizationsScreen} or in the world, and color it based on the settings the player has defined.
     *
     * @param poseStack       The rendering {@link PoseStack}.
     * @param buffer          The rendering {@link MultiBufferSource}.
     * @param packedLight     The {@link Integer} for the packed lighting for rendering.
     * @param entity          The entity.
     * @param limbSwing       The {@link Float} for the limb swing rotation.
     * @param limbSwingAmount The {@link Float} for the limb swing amount.
     * @param partialTicks    The {@link Float} for the game's partial ticks.
     * @param ageInTicks      The {@link Float} for the entity's age in ticks.
     * @param netHeadYaw      The {@link Float} for the head yaw rotation.
     * @param headPitch       The {@link Float} for the head pitch rotation.
     */
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof AbstractClientPlayer abstractClientPlayer && !abstractClientPlayer.isInvisible()) {
            User user = UserData.Client.getClientUser();
            UUID playerUUID = abstractClientPlayer.getUUID();
            Map<UUID, Halo> halos = ClientHaloPerkData.INSTANCE.getClientPerkData();
            if ((Minecraft.getInstance().screen instanceof AetherCustomizationsScreen aetherCustomizationsScreen && aetherCustomizationsScreen.haloEnabled && Minecraft.getInstance().player != null && playerUUID.equals(Minecraft.getInstance().player.getUUID()) && user != null && PerkUtil.hasHalo().test(user))
                    || (!(Minecraft.getInstance().screen instanceof AetherCustomizationsScreen) && halos.containsKey(playerUUID))) {
                this.playerHalo.crouching = this.getParentModel().crouching;
                this.playerHalo.halo.yRot = this.getParentModel().head.yRot;
                this.playerHalo.halo.xRot = this.getParentModel().head.xRot;
                if (entity.isCrouching()) {
                    this.playerHalo.halo.y = 4.2F;
                } else {
                    this.playerHalo.halo.y = 0.0F;
                }
                this.playerHalo.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                Triple<Float, Float, Float> color;
                if (Minecraft.getInstance().screen instanceof AetherCustomizationsScreen aetherCustomizationsScreen) {
                    color = PerkUtil.getPerkColor(aetherCustomizationsScreen.haloColor);
                } else {
                    color = PerkUtil.getPerkColor(halos.get(playerUUID).hexColor());
                }
                if (color != null) {
                    VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(PLAYER_HALO_GRAYSCALE_LOCATION));
                    this.playerHalo.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, Color.ofRGBA(color.getLeft() * 255, color.getMiddle() * 255, color.getRight() * 255, 255).getColor());
                } else {
                    VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(PLAYER_HALO_LOCATION));
                    this.playerHalo.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
                }
            }
        }
    }
}
