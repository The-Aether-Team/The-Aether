package com.gildedgames.aether.client.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherWoodTypes;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class AetherAtlases
{
	public static final Material TREASURE_CHEST_MATERIAL = getChestMaterial("treasure_chest");
	public static final Material TREASURE_CHEST_LEFT_MATERIAL = getChestMaterial("treasure_chest_left");
	public static final Material TREASURE_CHEST_RIGHT_MATERIAL = getChestMaterial("treasure_chest_right");

	public static Material getChestMaterial(String chestName) {
		return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Aether.MODID, "entity/tiles/chest/" + chestName));
	}

	public static void registerWoodTypeAtlases() {
		Sheets.addWoodType(AetherWoodTypes.SKYROOT);
	}
	
	@SubscribeEvent
	public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
		if (event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
			event.addSprite(TREASURE_CHEST_MATERIAL.texture());
			event.addSprite(TREASURE_CHEST_LEFT_MATERIAL.texture());
			event.addSprite(TREASURE_CHEST_RIGHT_MATERIAL.texture());
		}
	}
}
