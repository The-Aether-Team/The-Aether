package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

public class MoaSaddleLayer extends RenderLayer<Moa, MoaModel> {
	private final MoaModel saddle;
	
	public MoaSaddleLayer(RenderLayerParent<Moa, MoaModel> entityRenderer, MoaModel saddleModel) {
		super(entityRenderer);
		this.saddle = saddleModel;
	}

	@Override
	public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (moa.isSaddled()) {
			ResourceLocation texture = moa.getMoaType().getSaddleTexture();
			ResourceLocation moaSkin = this.getMoaSkinLocation(moa);
			if (moaSkin != null) {
				texture = moaSkin;
			}
			this.getParentModel().copyPropertiesTo(this.saddle);
			this.saddle.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
			this.saddle.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
			this.saddle.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	private ResourceLocation getMoaSkinLocation(Moa moa) {
		UUID lastRiderUUID = moa.getLastRider();
		UUID moaUUID = moa.getMoaUUID();
		Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
		if (Minecraft.getInstance().screen instanceof MoaSkinsScreen moaSkinsScreen && moaSkinsScreen.getSelectedSkin() != null && moaSkinsScreen.getPreviewMoa() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID().equals(moaUUID)) {
			return moaSkinsScreen.getSelectedSkin().getSaddleLocation();
		} else if (userSkinsData.containsKey(lastRiderUUID) && userSkinsData.get(lastRiderUUID).moaUUID() != null && userSkinsData.get(lastRiderUUID).moaUUID().equals(moaUUID)) {
			return userSkinsData.get(lastRiderUUID).moaSkin().getSaddleLocation();
		}
		return null;
	}
}
