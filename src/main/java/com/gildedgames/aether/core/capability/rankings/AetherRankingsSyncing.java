package com.gildedgames.aether.core.capability.rankings;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.AetherRankingsClientSyncPacket;
import com.gildedgames.aether.core.network.packet.server.AetherRankingsServerSyncPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

public abstract class AetherRankingsSyncing implements AetherRankings {
    private CompoundTag storedTag;
    private boolean isDirty = false;

    public void updateSynchableNBTFromServer() {
        if (!this.getPlayer().level.isClientSide()) {
            if (this.isDirty()) {
                CompoundTag tag = this.serializeSynchableNBT();
                if (!NbtUtils.compareNbt(tag, this.getStoredTag(), true)) {
                    this.setStoredTag(tag);
                    AetherPacketHandler.sendToAll(new AetherRankingsClientSyncPacket(this.getPlayer().getId(), this.getStoredTag()));
                    this.markDirty(false);
                }
            }
        }
    }

    public void updateSynchableNBTFromClient() {
        if (this.getPlayer().level.isClientSide()) {
            if (this.isDirty()) {
                CompoundTag tag = this.serializeSynchableNBT();
                if (!NbtUtils.compareNbt(tag, this.getStoredTag(), true)) {
                    this.setStoredTag(tag);
                    AetherPacketHandler.sendToServer(new AetherRankingsServerSyncPacket(this.getPlayer().getId(), this.getStoredTag()));
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
