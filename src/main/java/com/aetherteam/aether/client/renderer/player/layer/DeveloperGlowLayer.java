package com.aetherteam.aether.client.renderer.player.layer;

import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.aether.perk.data.ClientDeveloperGlowPerkData;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.UUID;

public class DeveloperGlowLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {
    public DeveloperGlowLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity instanceof AbstractClientPlayer abstractClientPlayer) {
            User user = UserData.Client.getClientUser();
            UUID playerUUID = abstractClientPlayer.getUUID();
            Map<UUID, DeveloperGlow> developerGlows = ClientDeveloperGlowPerkData.INSTANCE.getClientPerkData();
            if ((Minecraft.getInstance().screen instanceof AetherCustomizationsScreen aetherCustomizationsScreen && aetherCustomizationsScreen.developerGlowEnabled && Minecraft.getInstance().player != null && playerUUID.equals(Minecraft.getInstance().player.getUUID()) && user != null && PerkUtil.hasDeveloperGlow().test(user))
                    || (!(Minecraft.getInstance().screen instanceof AetherCustomizationsScreen) && developerGlows.containsKey(playerUUID))) {
                VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.eyes(abstractClientPlayer.getSkinTextureLocation()));
                Triple<Float, Float, Float> color;
                if (Minecraft.getInstance().screen instanceof AetherCustomizationsScreen aetherCustomizationsScreen) {
                    color = PerkUtil.getPerkColor(aetherCustomizationsScreen.developerGlowColor);
                } else {
                    color = PerkUtil.getPerkColor(developerGlows.get(playerUUID).hexColor());
                }
                if (color != null) {
                    this.getParentModel().renderToBuffer(pMatrixStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, color.getLeft(), color.getMiddle(), color.getRight(), 1.0F);
                } else {
                    this.getParentModel().renderToBuffer(pMatrixStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}
