package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.Aether;
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

	public static RenderMaterial getChestMaterial(String chestName) {
		return new RenderMaterial(Atlases.CHEST_SHEET, new ResourceLocation(Aether.MODID, "entity/chest/" + chestName));
	}
	
	@SubscribeEvent
	public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(Atlases.CHEST_SHEET)) {
			event.addSprite(TREASURE_CHEST_MATERIAL.texture());
			event.addSprite(TREASURE_CHEST_LEFT_MATERIAL.texture());
			event.addSprite(TREASURE_CHEST_RIGHT_MATERIAL.texture());
		}
	}

}
