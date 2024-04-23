package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherWoodTypes;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AetherAtlases {
	public static Material TREASURE_CHEST_MATERIAL;
	public static Material TREASURE_CHEST_LEFT_MATERIAL;
	public static Material TREASURE_CHEST_RIGHT_MATERIAL;

	/**
	 * Need to register these static values here from {@link AetherClient#clientSetup(FMLClientSetupEvent)},
	 * otherwise they'll be loaded too early from static initialization in the field.
	 */
	public static void registerTreasureChestAtlases() {
		TREASURE_CHEST_MATERIAL = getChestMaterial("treasure_chest");
		TREASURE_CHEST_LEFT_MATERIAL = getChestMaterial("treasure_chest_left");
		TREASURE_CHEST_RIGHT_MATERIAL = getChestMaterial("treasure_chest_right");
	}

	public static void registerWoodTypeAtlases() {
		addWoodType(AetherWoodTypes.SKYROOT);
	}

	public static void addWoodType(WoodType woodType) {
		Sheets.SIGN_MATERIALS.put(woodType, createSignMaterial(woodType));
		Sheets.HANGING_SIGN_MATERIALS.put(woodType, createHangingSignMaterial(woodType));
	}

	private static Material createSignMaterial(WoodType woodType) {
		ResourceLocation location = new ResourceLocation(woodType.name());
		return new Material(Sheets.SIGN_SHEET, new ResourceLocation(location.getNamespace(), "entity/signs/" + location.getPath()));
	}

	private static Material createHangingSignMaterial(WoodType woodType) {
		ResourceLocation location = new ResourceLocation(woodType.name());
		return new Material(Sheets.SIGN_SHEET, new ResourceLocation(location.getNamespace(), "entity/signs/hanging/" + location.getPath()));
	}

	public static Material getChestMaterial(String chestName) {
		return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Aether.MODID, "entity/tiles/chest/" + chestName));
	}
}
