package com.gildedgames.aether.client.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherWoodTypes;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class AetherAtlases
{
	public static final RenderMaterial TREASURE_CHEST_MATERIAL = getChestMaterial("treasure_chest");
	public static final RenderMaterial TREASURE_CHEST_LEFT_MATERIAL = getChestMaterial("treasure_chest_left");
	public static final RenderMaterial TREASURE_CHEST_RIGHT_MATERIAL = getChestMaterial("treasure_chest_right");

	public static RenderMaterial getChestMaterial(String chestName) {
		return new RenderMaterial(Atlases.CHEST_SHEET, new ResourceLocation(Aether.MODID, "entity/tiles/chest/" + chestName));
	}

	public static RenderMaterial getSignMaterial(WoodType woodType) {
		ResourceLocation location = new ResourceLocation(woodType.name());
		return new RenderMaterial(Atlases.SIGN_SHEET, new ResourceLocation(location.getNamespace(), "entity/tiles/signs/" + location.getPath()));
	}

	public static void addWoodType(WoodType woodType) {
		Atlases.SIGN_MATERIALS.put(woodType, getSignMaterial(woodType));
	}

	public static void registerWoodTypeAtlases() {
		addWoodType(AetherWoodTypes.SKYROOT);
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
