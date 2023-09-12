package com.aetherteam.aether.client.renderer.blockentity;

import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.client.AetherAtlases;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.ChestType;
import noobanidus.mods.lootr.util.Getter;

import java.util.UUID;

public class TreasureChestRenderer extends ChestRenderer<TreasureChestBlockEntity> {
	private UUID player = null;

	public TreasureChestRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected Material getMaterial(TreasureChestBlockEntity blockEntity, ChestType chestType) {
		if (blockEntity.useLootrLoot()) {
			if (this.player == null) {
				Player player = Getter.getPlayer();
				if (player == null) {
					return AetherAtlases.LOOTR_TREASURE_CHEST_UNOPENED;
				}
				this.player = player.getUUID();
			}
			return blockEntity.getOpeners().contains(this.player) ? AetherAtlases.LOOTR_TREASURE_CHEST_OPENED : AetherAtlases.LOOTR_TREASURE_CHEST_UNOPENED;
		} else {
			return switch (chestType) {
				case LEFT -> AetherAtlases.TREASURE_CHEST_LEFT_MATERIAL;
				case RIGHT -> AetherAtlases.TREASURE_CHEST_RIGHT_MATERIAL;
				case SINGLE -> AetherAtlases.TREASURE_CHEST_MATERIAL;
			};
		}
	}
}
