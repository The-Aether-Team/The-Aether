package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.MoaEmissiveLayer;
import com.aetherteam.aether.client.renderer.entity.layers.MoaSaddleEmissiveLayer;
import com.aetherteam.aether.client.renderer.entity.layers.MoaSaddleLayer;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class MoaRenderer extends MobRenderer<Moa, MoaModel> {
	private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/white_moa.png");
	private static final ResourceLocation MOS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/mos.png");
	private static final ResourceLocation RAPTOR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/raptor.png");

	public MoaRenderer(EntityRendererProvider.Context context) {
		super(context, new MoaModel(context.bakeLayer(AetherModelLayers.MOA)), 0.7F);
		this.addLayer(new MoaEmissiveLayer(this));
		this.addLayer(new MoaSaddleLayer(this, new MoaModel(context.bakeLayer(AetherModelLayers.MOA_SADDLE))));
		this.addLayer(new MoaSaddleEmissiveLayer(this, new MoaModel(context.bakeLayer(AetherModelLayers.MOA_SADDLE))));
	}

	/**
	 * Scales the Moa and also repositions it if it is sitting.
	 * @param moa The {@link Moa} entity.
	 * @param poseStack The rendering {@link PoseStack}.
	 * @param partialTicks The {@link Float} for the game's partial ticks.
	 */
	@Override
	protected void scale(Moa moa, PoseStack poseStack, float partialTicks) {
		float moaScale = moa.isBaby() ? 1.0F : 1.8F;
		poseStack.scale(moaScale, moaScale, moaScale);
		if (moa.isSitting()) {
			poseStack.translate(0.0, 0.5, 0.0);
		}
	}

	/**
	 * Passes the Moa's wing rotation to the model as the "ageInTicks" parameter.
	 * @param moa The {@link Moa} entity.
	 * @param partialTicks The {@link Float} for the game's partial ticks.
	 * @return The {@link Float} for the petal rotation.
	 */
	@Override
	protected float getBob(Moa moa, float partialTicks) {
		return this.model.setupWingsAnimation(moa, partialTicks);
	}

	/**
	 * Retrieves the texture for the Moa, whether it be from the {@link MoaType}, a player's {@link com.aetherteam.aether.perk.types.MoaSkins.MoaSkin}, or an Easter Egg skin.
	 * @param moa The {@link Moa} to retrieve the skin from.
	 * @return The {@link ResourceLocation} for the emissive texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(Moa moa) {
		ResourceLocation moaSkin = this.getMoaSkinLocation(moa);
		if (moaSkin != null) {
			return moaSkin;
		}
		if (moa.hasCustomName() && moa.getName().getString().equals("Mos")) {
			return MOS_TEXTURE;
		}
		if ((moa.hasCustomName() && moa.getName().getString().equals("Raptor__") && moa.getMoaType() == AetherMoaTypes.BLUE.get())
				|| (moa.getRider() != null && moa.getRider().equals(UUID.fromString("c3e6871e-8e60-490a-8a8d-2bbe35ad1604")))) { // Raptor__
			return RAPTOR_TEXTURE;
		}
		MoaType moaType = moa.getMoaType();
		return moaType == null ? DEFAULT_TEXTURE : moaType.getMoaTexture();
	}

	/**
	 * Retrieves the texture for the player's {@link com.aetherteam.aether.perk.types.MoaSkins.MoaSkin}, if there is one and the player has a Moa Skin.
	 * @param moa The {@link Moa} to retrieve the skin from.
	 * @return The {@link ResourceLocation} for the emissive texture.
	 */
	@Nullable
	private ResourceLocation getMoaSkinLocation(Moa moa) {
		UUID lastRiderUUID = moa.getLastRider();
		UUID moaUUID = moa.getMoaUUID();
		Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
		if (Minecraft.getInstance().screen instanceof MoaSkinsScreen moaSkinsScreen && moaSkinsScreen.getSelectedSkin() != null && moaSkinsScreen.getPreviewMoa() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID().equals(moaUUID)) {
			return moaSkinsScreen.getSelectedSkin().getSkinLocation();
		} else if (userSkinsData.containsKey(lastRiderUUID) && userSkinsData.get(lastRiderUUID).moaUUID() != null && userSkinsData.get(lastRiderUUID).moaUUID().equals(moaUUID)) {
			return userSkinsData.get(lastRiderUUID).moaSkin().getSkinLocation();
		}
		return null;
	}
}
