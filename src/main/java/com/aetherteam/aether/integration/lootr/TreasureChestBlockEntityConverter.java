package com.aetherteam.aether.integration.lootr;

import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;

/**
 * Converter that allows Lootr to work with Aether's Treasure Chests.
 * Registered via Java SPI (ServiceLoader) in META-INF/services.
 */
public class TreasureChestBlockEntityConverter implements ILootrBlockEntityConverter<TreasureChestBlockEntity> {

    @Override
    public ILootrBlockEntity apply(TreasureChestBlockEntity blockEntity) {
        return new LootrTreasureChestInfoProvider(blockEntity, blockEntity.getLootrInfoUUID());
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return AetherBlockEntityTypes.TREASURE_CHEST.get();
    }
}
