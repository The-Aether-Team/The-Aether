package com.gildedgames.aether.client.renderer.entity;

import java.util.Calendar;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.MimicModel;
import com.gildedgames.aether.common.entity.monster.dungeon.Mimic;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MimicRenderer extends MobRenderer<Mimic, MimicModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/normal.png");
	private static final ResourceLocation XMAS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/christmas.png");

	private boolean isChristmas;
	
	public MimicRenderer(EntityRendererProvider.Context renderer) {
		super(renderer, new MimicModel(renderer.bakeLayer(AetherModelLayers.MIMIC)), 1.0F);
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(Calendar.DAY_OF_MONTH) <= 26) {
			this.isChristmas = true;
		}
	}

	@Override
	public ResourceLocation getTextureLocation(Mimic entity) {
		return isChristmas? XMAS_TEXTURE : TEXTURE;
	}
	
}
