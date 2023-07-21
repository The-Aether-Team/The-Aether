package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.MimicModel;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

import java.util.Calendar;

public class MimicRenderer extends MobRenderer<Mimic, MimicModel> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/normal.png");
	private static final ResourceLocation XMAS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/christmas.png");
	private static final ResourceLocation LOOTR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/lootr.png");

	private boolean isChristmas;
	
	public MimicRenderer(EntityRendererProvider.Context renderer) {
		super(renderer, new MimicModel(renderer.bakeLayer(AetherModelLayers.MIMIC)), 1.0F);
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(Calendar.DAY_OF_MONTH) <= 26) {
			this.isChristmas = true;
		}
	}

	@Override
	public ResourceLocation getTextureLocation(Mimic mimic) {
		return ModList.get().isLoaded("lootr") ? LOOTR_TEXTURE : this.isChristmas ? XMAS_TEXTURE : TEXTURE;
	}
}
