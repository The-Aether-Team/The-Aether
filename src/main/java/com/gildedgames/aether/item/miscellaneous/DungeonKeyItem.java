package com.gildedgames.aether.item.miscellaneous;

import com.gildedgames.aether.api.registers.DungeonType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class DungeonKeyItem extends Item {
    private final Supplier<DungeonType> dungeonTypeGetter;

    public DungeonKeyItem(Supplier<DungeonType> dungeonTypeGetter, Item.Properties properties) {
        super(properties);
        this.dungeonTypeGetter = dungeonTypeGetter;
    }

    public DungeonType getDungeonType() {
        return this.dungeonTypeGetter.get();
    }
}
