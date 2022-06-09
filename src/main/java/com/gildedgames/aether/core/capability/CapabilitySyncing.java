package com.gildedgames.aether.core.capability;

import com.gildedgames.aether.core.network.AetherPacket;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;

public abstract class CapabilitySyncing implements INBTSynchable<CompoundTag> {
    private CompoundTag storedTag;
    private boolean isDirty = false;
    private boolean shouldForce = false;

    public void updateSyncableNBTFromServer(Level level) {
        if (!level.isClientSide()) {
            if (this.canSendPacket() || this.shouldForce()) {
                AetherPacketHandler.sendToAll(this.getSyncPacket(this.getStoredTag()));
                this.markForced(false);
            }
        }
    }

    public void updateSyncableNBTFromClient(Level level) {
        if (level.isClientSide()) {
            if (this.canSendPacket() || this.shouldForce()) {
                AetherPacketHandler.sendToServer(this.getSyncPacket(this.getStoredTag()));
                this.markForced(false);
            }
        }
    }

    private boolean canSendPacket() {
        if (this.isDirty()) {
            CompoundTag tag = this.serializeSynchableNBT();
            if (!NbtUtils.compareNbt(tag, this.getStoredTag(), true)) {
                this.setStoredTag(tag);
                return true;
            }
            this.markDirty(false);
        }
        return false;
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

    public boolean shouldForce() {
        return this.shouldForce;
    }

    public void markForced(boolean shouldForce) {
        this.shouldForce = shouldForce;
    }

    public abstract AetherPacket.AbstractAetherPacket getSyncPacket(CompoundTag tag);
}
