package com.aetherteam.aether.capability.item;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.common.util.LazyOptional;

public interface DroppedItem extends INBTSerializable<CompoundTag> {
    ItemEntity getItemEntity();

    static LazyOptional<DroppedItem> get(ItemEntity item) {
        return item.getCapability(AetherCapabilities.DROPPED_ITEM_CAPABILITY);
    }

    void setOwner(Entity owner);
    Entity getOwner();
}
