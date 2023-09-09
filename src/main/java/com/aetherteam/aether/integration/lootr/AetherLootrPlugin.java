package com.aetherteam.aether.integration.lootr;

import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.api.LootrAPI;

public class AetherLootrPlugin {
    public static MenuProvider getTreasureMenu(ServerPlayer player, TreasureChestBlockEntity blockEntity) {
        return LootrAPI.getModdedMenu(blockEntity.getLevel(), blockEntity.getBlockEntityUuid(), blockEntity.getBlockPos(), player, blockEntity, (lootPlayer, inventory, table, seed) -> unpackLootTable(blockEntity, lootPlayer, inventory, table, seed), blockEntity::getLootTable, blockEntity::getLootSeed, AetherLootrPlugin::menuBuilder);
    }

    public static AbstractContainerMenu menuBuilder(int id, Inventory inventory, Container container, int rows) {
        return ChestMenu.threeRows(id, inventory, container);
    }

    public static void unpackLootTable(TreasureChestBlockEntity blockEntity, Player player, Container container, ResourceLocation location, long seed) {
        if (location != null) {
            LootTable lootTable = blockEntity.getLevel().getServer().getLootData().getLootTable(location);
            if (lootTable != LootTable.EMPTY && blockEntity.getLevel() instanceof ServerLevel serverLevel) {
                LootParams.Builder lootBuilder = new LootParams.Builder(serverLevel).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockEntity.getBlockPos()));
                if (player != null) {
                    lootBuilder.withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player);
                }
                lootTable.fill(container, lootBuilder.create(LootContextParamSets.CHEST), LootrAPI.getLootSeed(seed));
            } else {
                LootrAPI.LOG.error("Unable to fill loot treasure chest in " + blockEntity.getLevel().dimension() + " at " + blockEntity.getBlockPos() + " as the loot table '" + location + "' couldn't be resolved! Please search the loot table in `latest.log` to see if there are errors in loading.");
            }
        } else {
            LootrAPI.LOG.error("Unable to fill loot treasure chest in " + blockEntity.getLevel().dimension() + " at " + blockEntity.getBlockPos() + " as the loot table was null.");
        }
    }
}