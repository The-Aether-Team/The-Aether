package com.aether.client.renderer.entity;

import java.util.Calendar;

import com.aether.Aether;
import com.aether.client.renderer.entity.model.MimicModel;
import com.aether.entity.monster.MimicEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MimicRenderer extends MobRenderer<MimicEntity, MimicModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mimic/normal.png");
	private static final ResourceLocation XMAS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mimic/christmas.png");

	private boolean isChristmas;
	
	public MimicRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new MimicModel(), 1.0F);
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(Calendar.DAY_OF_MONTH) <= 26) {
			this.isChristmas = true;
		}
	}

	@Override
	public ResourceLocation getEntityTexture(MimicEntity entity) {
		return isChristmas? XMAS_TEXTURE : TEXTURE;
	}
	
}
