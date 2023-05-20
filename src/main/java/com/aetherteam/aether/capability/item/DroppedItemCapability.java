package com.aetherteam.aether.capability.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;

public class DroppedItemCapability implements DroppedItem {
    private final ItemEntity itemEntity;

    private Entity owner;

    public DroppedItemCapability(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }

    @Override
    public ItemEntity getItemEntity() {
        return this.itemEntity;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (this.getOwner() != null) {
            tag.putInt("Owner", this.getOwner().getId());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Owner")) {
            this.setOwner(this.getItemEntity().level.getEntity(tag.getInt("Owner")));
        }
    }

    @Override
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    @Override
    public Entity getOwner() {
        return this.owner;
    }

}
