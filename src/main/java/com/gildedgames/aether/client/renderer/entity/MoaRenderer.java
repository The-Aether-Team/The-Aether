package com.gildedgames.aether.client.renderer.entity;

import java.util.UUID;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.moa.MoaTypes;
import com.gildedgames.aether.client.renderer.entity.layers.MoaSaddleLayer;
import com.gildedgames.aether.client.renderer.entity.model.MoaModel;
import com.gildedgames.aether.entity.passive.MoaEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoaRenderer extends MobRenderer<MoaEntity, MoaModel>{
	private static final ResourceLocation MOS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/moa/mos.png");
	private static final ResourceLocation RAPTOR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/moa/raptor.png");
	
	public MoaRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new MoaModel(0.0F), 0.7F);
		
		this.addLayer(new MoaSaddleLayer(this));
	}
	
	@Override
	protected float handleRotationFloat(MoaEntity moa, float partialTicks) {
		float f1 = moa.prevWingRotation + (moa.wingRotation - moa.prevWingRotation) * partialTicks;
		float f2 = moa.prevDestPos + (moa.destPos - moa.prevDestPos) * partialTicks;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}
	
	@Override
	protected void preRenderCallback(MoaEntity moa, MatrixStack matrixStackIn, float partialTickTime) {
		float moaScale = moa.isChild()? 1.0F : 1.8F;
		matrixStackIn.scale(moaScale, moaScale, moaScale);
	}

	@Override
	public ResourceLocation getEntityTexture(MoaEntity entity) {
		if (entity.hasCustomName()) {
			if (entity.getMoaType() == MoaTypes.ORANGE && "Mos".equals(entity.getCustomName().getUnformattedComponentText())) {
				return MOS_TEXTURE;
			}
			if (entity.getMoaType() == MoaTypes.BLUE && "Raptor__".equals(entity.getCustomName().getUnformattedComponentText())) {
				return RAPTOR_TEXTURE;
			}
		}
		if (entity.getMoaType() == MoaTypes.BLUE) {
			UUID ownerID = entity.getOwnerID();
			if (ownerID != null && ownerID.getMostSignificantBits() == -4330625426362709750L && ownerID.getLeastSignificantBits() == -8463060028814977532L) {
				return RAPTOR_TEXTURE;
			}
		}
		return entity.getMoaType().getMoaTexture();
	}

}
