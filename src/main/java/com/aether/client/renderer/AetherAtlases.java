package com.aether.client.renderer;

import com.aether.Aether;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Aether.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class AetherAtlases {
	public static final RenderMaterial TREASURE_CHEST_MATERIAL = getChestMaterial("treasure");
	public static final RenderMaterial TREASURE_CHEST_LEFT_MATERIAL = getChestMaterial("treasure_left");
	public static final RenderMaterial TREASURE_CHEST_RIGHT_MATERIAL = getChestMaterial("treasure_right");

	public static RenderMaterial getChestMaterial(String p_228774_0_) {
		return new RenderMaterial(Atlases.CHEST_ATLAS, new ResourceLocation(Aether.MODID, "entity/chest/" + p_228774_0_));
	}
	
	@SubscribeEvent
	public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
		if (event.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS)) {
			event.addSprite(TREASURE_CHEST_MATERIAL.getTextureLocation());
			event.addSprite(TREASURE_CHEST_LEFT_MATERIAL.getTextureLocation());
			event.addSprite(TREASURE_CHEST_RIGHT_MATERIAL.getTextureLocation());
		}
	}

}
