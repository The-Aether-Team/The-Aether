package com.gildedgames.aether.item.miscellaneous;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class DungeonKeyItem extends Item {
    private final ResourceLocation dungeonType;

    public DungeonKeyItem(ResourceLocation dungeonType, Item.Properties properties) {
        super(properties);
        this.dungeonType = dungeonType;
    }

    public ResourceLocation getDungeonType() {
        return this.dungeonType;
    }
}
