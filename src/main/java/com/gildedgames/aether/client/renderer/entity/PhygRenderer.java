package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.layers.PhygWingsLayer;
import com.gildedgames.aether.common.entity.passive.PhygEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;

public class PhygRenderer extends MobRenderer<PhygEntity, PigModel<PhygEntity>>
{
	private static final ResourceLocation PHYG_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/phyg/phyg.png");

	public PhygRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PigModel<>(0.0F), 0.7F);
		this.addLayer(new PhygWingsLayer(this));
		this.addLayer(new SaddleLayer<>(this, new PigModel<>(0.5F), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
	}

	@Override
	public ResourceLocation getTextureLocation(PhygEntity entity) {
		return PHYG_TEXTURE;
	}
}
