package com.aetherteam.aether.client.renderer.blockentity;

import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.client.AetherAtlases;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.fml.ModList;

public class TreasureChestRenderer extends ChestRenderer<TreasureChestBlockEntity> {
    public TreasureChestRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Material getMaterial(TreasureChestBlockEntity blockEntity, ChestType chestType) {
        // Lootr integration: use opened/unopened textures based on player's state
        if (ModList.get().isLoaded("lootr") && chestType == ChestType.SINGLE) {
            if (Minecraft.getInstance().player != null
                    && !blockEntity.getLocked()
                    && blockEntity.hasClientOpened(Minecraft.getInstance().player.getUUID())) {
                return AetherAtlases.LOOTR_TREASURE_CHEST_OPENED_MATERIAL;
            } else if (!blockEntity.getLocked()) {
                return AetherAtlases.LOOTR_TREASURE_CHEST_UNOPENED_MATERIAL;
            }
        }

        return switch (chestType) {
            case LEFT -> AetherAtlases.TREASURE_CHEST_LEFT_MATERIAL;
            case RIGHT -> AetherAtlases.TREASURE_CHEST_RIGHT_MATERIAL;
            case SINGLE -> AetherAtlases.TREASURE_CHEST_MATERIAL;
        };
    }
}
