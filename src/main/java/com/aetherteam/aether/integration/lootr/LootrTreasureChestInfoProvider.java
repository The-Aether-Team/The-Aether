package com.aetherteam.aether.integration.lootr;

import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.ILootrInfo;
import noobanidus.mods.lootr.common.api.data.ILootrSavedData;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * Lootr info provider for Aether's Treasure Chests.
 * This class wraps a TreasureChestBlockEntity and provides Lootr functionality.
 */
public class LootrTreasureChestInfoProvider implements ILootrBlockEntity {
    private final TreasureChestBlockEntity blockEntity;
    private final UUID infoId;
    private final String cachedId;

    public LootrTreasureChestInfoProvider(TreasureChestBlockEntity blockEntity, UUID id) {
        this.blockEntity = blockEntity;
        this.infoId = id;
        this.cachedId = ILootrInfo.generateInfoKey(id);
    }

    @Override
    @Deprecated
    public LootrBlockType getInfoBlockType() {
        return LootrBlockType.CHEST;
    }

    @Override
    public ILootrType getInfoNewType() {
        return BuiltInLootrTypes.CHEST;
    }

    @Override
    public @NotNull UUID getInfoUUID() {
        return infoId;
    }

    @Override
    public String getInfoKey() {
        return cachedId;
    }

    @Override
    public boolean hasBeenOpened() {
        return blockEntity.getLootrHasBeenOpened();
    }

    @Override
    public boolean isPhysicallyOpen() {
        return blockEntity.getOpenNess(1f) > 0;
    }

    @Override
    public @NotNull BlockPos getInfoPos() {
        return blockEntity.getBlockPos();
    }

    @Override
    public ResourceKey<LootTable> getInfoLootTable() {
        return blockEntity.getLootTable();
    }

    @Override
    public Component getInfoDisplayName() {
        return blockEntity.getDisplayName();
    }

    @Override
    public @NotNull ResourceKey<Level> getInfoDimension() {
        return blockEntity.getLevel().dimension();
    }

    @Override
    public int getInfoContainerSize() {
        return blockEntity.getContainerSize();
    }

    @Override
    public long getInfoLootSeed() {
        return blockEntity.getLootTableSeed();
    }

    @Override
    public @Nullable NonNullList<ItemStack> getInfoReferenceInventory() {
        return null;
    }

    @Override
    public boolean isInfoReferenceInventory() {
        return false;
    }

    @Override
    public Level getInfoLevel() {
        return blockEntity.getLevel();
    }

    @Override
    public Container getInfoContainer() {
        return blockEntity;
    }

    @Override
    public BlockEntity asBlockEntity() {
        return blockEntity;
    }

    @Override
    public void markChanged() {
        blockEntity.setChanged();
        blockEntity.setLootrHasBeenOpened(true);
    }

    @Override
    public void markDataChanged() {
        ILootrSavedData data = LootrAPI.getData(this);
        if (data != null) {
            data.markChanged();
        }
    }

    @Override
    public @Nullable Set<UUID> getClientOpeners() {
        return blockEntity.getLootrClientOpeners();
    }

    @Override
    public boolean isClientOpened() {
        return blockEntity.isLootrClientOpened();
    }

    @Override
    public void setClientOpened(boolean opened) {
        blockEntity.setLootrClientOpened(opened);
    }
}
