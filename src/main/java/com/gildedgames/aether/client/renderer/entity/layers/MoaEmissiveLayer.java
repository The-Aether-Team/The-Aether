package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.gildedgames.aether.client.renderer.entity.model.BipedBirdModel;
import com.gildedgames.aether.entity.passive.Moa;
import com.gildedgames.aether.perk.data.ClientMoaSkinPerkData;
import com.gildedgames.aether.perk.types.MoaData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.UUID;

public class MoaEmissiveLayer<T extends Moa, M extends BipedBirdModel<T>> extends RenderLayer<T, M> {
    public MoaEmissiveLayer(RenderLayerParent<T, M> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation moaSkin = this.getMoaSkinLocation(moa);
        if (moaSkin != null) {
            RenderType renderType = RenderType.eyes(moaSkin);
            VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private ResourceLocation getMoaSkinLocation(Moa moa) {
        UUID lastRiderUUID = moa.getLastRider();
        UUID moaUUID = moa.getMoaUUID();
        Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
        if (Minecraft.getInstance().screen instanceof MoaSkinsScreen moaSkinsScreen && moaSkinsScreen.getSelectedSkin() != null && moaSkinsScreen.getPreviewMoa() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID().equals(moaUUID)) {
            return moaSkinsScreen.getSelectedSkin().getEmissiveLocation();
        } else if (userSkinsData.containsKey(lastRiderUUID) && userSkinsData.get(lastRiderUUID).moaUUID() != null && userSkinsData.get(lastRiderUUID).moaUUID().equals(moaUUID)) {
            return userSkinsData.get(lastRiderUUID).moaSkin().getEmissiveLocation();
        }
        return null;
    }
}
