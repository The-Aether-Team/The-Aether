package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.api.registers.MoaType;
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

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class MoaSaddleLayer extends RenderLayer<Moa, MoaModel> {
	private final MoaModel saddle;
	
	public MoaSaddleLayer(RenderLayerParent<Moa, MoaModel> entityRenderer, MoaModel saddleModel) {
		super(entityRenderer);
		this.saddle = saddleModel;
	}

	/**
	 * Renders a saddle layer on a Moa if there is one from the {@link MoaType} or one from a {@link com.aetherteam.aether.perk.types.MoaSkins.MoaSkin}.
	 * @param poseStack The rendering {@link PoseStack}.
	 * @param buffer The rendering {@link MultiBufferSource}.
	 * @param packedLight The {@link Integer} for the packed lighting for rendering.
	 * @param moa The {@link Moa} entity.
	 * @param limbSwing The {@link Float} for the limb swing rotation.
	 * @param limbSwingAmount The {@link Float} for the limb swing amount.
	 * @param partialTicks The {@link Float} for the game's partial ticks.
	 * @param ageInTicks The {@link Float} for the entity's age in ticks.
	 * @param netHeadYaw The {@link Float} for the head yaw rotation.
	 * @param headPitch The {@link Float} for the head pitch rotation.
	 */
	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (moa.isSaddled()) {
			ResourceLocation texture = moa.getMoaType() != null ? moa.getMoaType().getSaddleTexture() : AetherMoaTypes.BLUE.get().getSaddleTexture();
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

	/**
	 * Retrieves the saddle texture for the player's {@link com.aetherteam.aether.perk.types.MoaSkins.MoaSkin}, if there is one and the player has a Moa Skin.
	 * @param moa The {@link Moa} to retrieve the skin from.
	 * @return The {@link ResourceLocation} for the emissive texture.
	 */
	@Nullable
	private ResourceLocation getMoaSkinLocation(Moa moa) {
		UUID lastRiderUUID = moa.getLastRider();
		UUID moaUUID = moa.getMoaUUID();
		Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
		if (Minecraft.getInstance().screen instanceof MoaSkinsScreen moaSkinsScreen && moaSkinsScreen.getSelectedSkin() != null && moaSkinsScreen.getPreviewMoa() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID().equals(moaUUID)) {
			return moaSkinsScreen.getSelectedSkin().getSaddleLocation();
		} else if (userSkinsData.containsKey(lastRiderUUID) && userSkinsData.get(lastRiderUUID).moaUUID() != null && userSkinsData.get(lastRiderUUID).moaUUID().equals(moaUUID)) {
			return userSkinsData.get(lastRiderUUID).moaSkin().getSaddleLocation();
		}
		return null;
	}
}
