package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.entity.monster.SentryEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SentryLayer<T extends SentryEntity, M extends SlimeModel<T>> extends AbstractEyesLayer<T, M> {
	private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/sentry/eye.png"));
	
	public SentryLayer(IEntityRenderer<T, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public RenderType renderType() {
		return RENDER_TYPE;
	}

}
