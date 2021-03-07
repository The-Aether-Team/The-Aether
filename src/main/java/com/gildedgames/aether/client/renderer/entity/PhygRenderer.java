package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.layers.PhygSaddleLayer;
import com.gildedgames.aether.client.renderer.entity.layers.PhygWingsLayer;
import com.gildedgames.aether.common.entity.passive.PhygEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhygRenderer extends MobRenderer<PhygEntity, PigModel<PhygEntity>> {
	private static final ResourceLocation PHYG_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/phyg/phyg.png");

	public PhygRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PigModel<>(0.0F), 0.7F);
		this.addLayer(new PhygWingsLayer(this));
		this.addLayer(new PhygSaddleLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(PhygEntity entity) {
		return PHYG_TEXTURE;
	}
}
