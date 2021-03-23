package com.gildedgames.aether.common.item.misc;

import com.gildedgames.aether.common.entity.tile.TreasureChestTileEntity;
import com.gildedgames.aether.core.api.registers.DungeonType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

public class DungeonKeyItem extends Item {
    private final Supplier<DungeonType> dungeonTypeGetter;

    public DungeonKeyItem(Supplier<DungeonType> dungeonTypeGetter, Item.Properties properties) {
        super(properties);
        this.dungeonTypeGetter = Validate.notNull(dungeonTypeGetter);
    }

    public DungeonType getDungeonType() {
        return dungeonTypeGetter.get();
    }
}
