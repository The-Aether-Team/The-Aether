package com.aetherteam.aether.integration.lootr;

import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;

/**
 * Wrapper class for Lootr API calls.
 * This class should only be loaded when Lootr is present to avoid ClassNotFoundException.
 */
public class LootrCompat {

    /**
     * Opens a treasure chest using Lootr's per-player inventory system.
     *
     * @param player The player opening the chest
     * @param pos    The position of the chest
     * @param level  The level containing the chest
     */
    public static void openTreasureChest(Player player, BlockPos pos, Level level) {
        if (player instanceof ServerPlayer serverPlayer) {
            ILootrInfoProvider provider = ILootrInfoProvider.of(pos, level);
            if (provider != null) {
                LootrAPI.handleProviderOpen(provider, serverPlayer);
            }
        }
    }

    /**
     * Handles shift-click on a treasure chest to mark it as unopened.
     *
     * @param player The player interacting with the chest
     * @param pos    The position of the chest
     * @param level  The level containing the chest
     */
    public static void handleTreasureChestSneak(Player player, BlockPos pos, Level level) {
        if (player instanceof ServerPlayer serverPlayer) {
            ILootrInfoProvider provider = ILootrInfoProvider.of(pos, level);
            if (provider != null) {
                LootrAPI.handleProviderSneak(provider, serverPlayer);
            }
        }
    }

    /**
     * Called when a treasure chest is destroyed to handle Lootr cleanup.
     *
     * @param level       The level
     * @param player      The player who destroyed the chest
     * @param pos         The position of the chest
     * @param blockEntity The block entity
     */
    public static void onTreasureChestDestroyed(Level level, Player player, BlockPos pos, TreasureChestBlockEntity blockEntity) {
        LootrAPI.playerDestroyed(level, player, pos, blockEntity);
    }

    /**
     * Checks if a player has already opened a specific treasure chest (client-side).
     *
     * @param blockEntity The treasure chest block entity
     * @param player      The player to check
     * @return true if the player has opened this chest
     */
    public static boolean hasClientOpened(TreasureChestBlockEntity blockEntity, Player player) {
        return blockEntity.hasClientOpened(player.getUUID());
    }

    /**
     * @return true if Lootr is configured to use vanilla textures
     */
    public static boolean isVanillaTextures() {
        return LootrAPI.isVanillaTextures();
    }

    /**
     * Gets the Lootr block entity ticker for decay/refresh mechanics.
     *
     * @return The Lootr ticker
     */
    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> BlockEntityTicker<T> getTicker() {
        return (BlockEntityTicker<T>) (BlockEntityTicker<TreasureChestBlockEntity>) ILootrBlockEntity::ticker;
    }
}
