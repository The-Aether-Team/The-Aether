package com.gildedgames.aether.core.capability.player;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

public abstract class AetherPlayerSyncing implements AetherPlayer {
    private CompoundTag storedTag;
    private boolean isDirty = false;

    public void updateSynchableNBT() {
        if (!this.getPlayer().level.isClientSide()) {
            if (this.isDirty()) {
                CompoundTag tag = this.serializeSynchableNBT();
                if (!NbtUtils.compareNbt(tag, this.getStoredTag(), true)) {
                    this.setStoredTag(tag);
                    AetherPacketHandler.sendToAll(this.getSyncPacket(this.getStoredTag()));
                    this.markDirty(false);
                }
            }
        }
    }

    public CompoundTag getStoredTag() {
        return this.storedTag;
    }

    public void setStoredTag(CompoundTag storedTag) {
        this.storedTag = storedTag;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void markDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }
}
