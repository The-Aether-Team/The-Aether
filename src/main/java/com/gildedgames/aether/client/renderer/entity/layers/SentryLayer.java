package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.entity.monster.dungeon.SentryEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.resources.ResourceLocation;

public class SentryLayer<T extends SentryEntity, M extends SlimeModel<T>> extends EyesLayer<T, M> {
	private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/eye.png"));
	
	public SentryLayer(RenderLayerParent<T, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public RenderType renderType() {
		return RENDER_TYPE;
	}

}
