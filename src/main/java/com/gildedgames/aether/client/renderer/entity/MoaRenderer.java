package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.registers.MoaType;
import com.gildedgames.aether.client.renderer.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.MoaSaddleLayer;
import com.gildedgames.aether.client.renderer.entity.model.MoaModel;
import com.gildedgames.aether.entity.passive.Moa;
import com.gildedgames.aether.api.AetherMoaTypes;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.UUID;

public class MoaRenderer extends MobRenderer<Moa, MoaModel> {
	private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/white_moa.png");
	private static final ResourceLocation MOS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/mos.png");
	private static final ResourceLocation RAPTOR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/raptor.png");

	public MoaRenderer(EntityRendererProvider.Context context) {
		super(context, new MoaModel(context.bakeLayer(AetherModelLayers.MOA)), 0.7F);
		this.addLayer(new MoaSaddleLayer(this, new MoaModel(context.bakeLayer(AetherModelLayers.MOA_SADDLE))));
	}

	@Override
	protected void scale(Moa moa, PoseStack poseStack, float partialTickTime) {
		float moaScale = moa.isBaby() ? 1.0F : 1.8F;
		poseStack.scale(moaScale, moaScale, moaScale);
		if (moa.isSitting()) {
			poseStack.translate(0.0, 0.5, 0.0);
		}
	}

	@Override
	protected float getBob(@Nonnull Moa moa, float partialTicks) {
		return this.model.setupWingsAnimation(moa, partialTicks);
	}

	@Nonnull
	@Override
	public ResourceLocation getTextureLocation(Moa moa) {
		if (moa.hasCustomName() && moa.getName().getString().equals("Mos") && moa.getMoaType() == AetherMoaTypes.ORANGE.get()) {
			return MOS_TEXTURE;
		}
		if ((moa.hasCustomName() && moa.getName().getString().equals("Raptor__") && moa.getMoaType() == AetherMoaTypes.BLUE.get())
				|| (moa.getRider() != null && moa.getRider().equals(UUID.fromString("c3e6871e-8e60-490a-8a8d-2bbe35ad1604")))) { // Raptor__
			return RAPTOR_TEXTURE;
		}
		MoaType moaType = moa.getMoaType();
		return moaType == null ? DEFAULT_TEXTURE : moaType.getMoaTexture();
	}
}
