package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherWoodTypes;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherAtlases {
	public static Material TREASURE_CHEST_MATERIAL = getChestMaterial("treasure_chest");
	public static Material TREASURE_CHEST_LEFT_MATERIAL = getChestMaterial("treasure_chest_left");
	public static Material TREASURE_CHEST_RIGHT_MATERIAL = getChestMaterial("treasure_chest_right");

	public static void registerWoodTypeAtlases() {
		Sheets.addWoodType(AetherWoodTypes.SKYROOT);
	}

	public static Material getChestMaterial(String chestName) {
		return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Aether.MODID, "entity/tiles/chest/" + chestName));
	}

	@SubscribeEvent
	public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
		if (event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
			event.addSprite(TREASURE_CHEST_MATERIAL.texture());
			event.addSprite(TREASURE_CHEST_LEFT_MATERIAL.texture());
			event.addSprite(TREASURE_CHEST_RIGHT_MATERIAL.texture());
		}
		if (event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
			event.addSprite(new ResourceLocation(Aether.MODID, "block/dungeon/door"));
			event.addSprite(new ResourceLocation(Aether.MODID, "block/dungeon/exclamation"));
			event.addSprite(new ResourceLocation(Aether.MODID, "block/dungeon/lock"));
			event.addSprite(new ResourceLocation(Aether.MODID, "block/dungeon/treasure"));
			event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/cape"));
			event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/gloves"));
			event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/misc"));
			event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/pendant"));
			event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/ring"));
			event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/shield"));
		}
	}
}
