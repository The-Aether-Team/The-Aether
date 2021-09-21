package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.layers.MoaSaddleLayer;
import com.gildedgames.aether.client.renderer.entity.model.MoaModel;
import com.gildedgames.aether.common.entity.passive.MoaEntity;

import com.gildedgames.aether.core.registry.AetherMoaTypes;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class MoaRenderer extends MobRenderer<MoaEntity, MoaModel>
{
	private static final ResourceLocation MOS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/mos.png");
	private static final ResourceLocation RAPTOR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/raptor.png");
	
	public MoaRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new MoaModel(0.0F), 0.7F);
		this.addLayer(new MoaSaddleLayer(this));
	}
	
//	@Override
//	protected float getBob(MoaEntity moa, float partialTicks) {
//		float f1 = moa.prevWingRotation + (moa.wingRotation - moa.prevWingRotation) * partialTicks;
//		float f2 = moa.prevDestPos + (moa.destPos - moa.prevDestPos) * partialTicks;
//		return (MathHelper.sin(f1) + 1.0F) * f2;
//	}

	@Override
	protected void scale(MoaEntity moa, MatrixStack matrixStackIn, float partialTickTime) {
		float moaScale = moa.isBaby() ? 1.0F : 1.8F;
		matrixStackIn.scale(moaScale, moaScale, moaScale);
	}

	@Override
	public ResourceLocation getTextureLocation(MoaEntity moa) {
		if (moa.hasCustomName() && moa.getCustomName() != null && "Mos".equals(moa.getCustomName().getContents()) && moa.getMoaType() == AetherMoaTypes.ORANGE) {
			return MOS_TEXTURE;
		}
		if ((moa.hasCustomName() && moa.getCustomName() != null && "Raptor__".equals(moa.getCustomName().getContents()) && moa.getMoaType() == AetherMoaTypes.BLUE)
				|| (moa.getRider() != null && moa.getRider().equals(UUID.fromString("c3e6871e-8e60-490a-8a8d-2bbe35ad1604")))) {
			return RAPTOR_TEXTURE;
		}
		return moa.getMoaType().getMoaTexture();
	}
}
