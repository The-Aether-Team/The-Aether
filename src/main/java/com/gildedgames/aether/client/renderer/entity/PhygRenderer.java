package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.PhygWingsLayer;
import com.gildedgames.aether.common.entity.passive.PhygEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.model.PigModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhygRenderer extends MobRenderer<PhygEntity, PigModel<PhygEntity>>
{
	private static final ResourceLocation PHYG_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/phyg/phyg.png");

	public PhygRenderer(EntityRendererProvider.Context context) {
		super(context, new PigModel(context.bakeLayer(AetherModelLayers.PHYG)), 0.7F);
		this.addLayer(new PhygWingsLayer(this, context.getModelSet()));
		this.addLayer(new SaddleLayer(this, new PigModel(context.bakeLayer(AetherModelLayers.PHYG)), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
	}

	@Override
	public ResourceLocation getTextureLocation(PhygEntity entity) {
		return PHYG_TEXTURE;
	}
}
