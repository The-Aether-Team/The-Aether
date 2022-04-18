package com.gildedgames.aether.core.capability.rankings;

import com.gildedgames.aether.core.capability.CapabilitySyncing;
import com.gildedgames.aether.core.network.AetherPacket;
import com.gildedgames.aether.core.network.packet.AetherRankingsSyncPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class AetherRankingsCapability extends CapabilitySyncing implements AetherRankings {
    private final Player player;

    private boolean sleeveGloves = false;
    private boolean renderHalo = true;
    private boolean renderDeveloperGlow = false;

    public AetherRankingsCapability(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("HatGloves", this.areSleeveGloves());
        tag.putBoolean("RenderHalo", this.shouldRenderHalo());
        tag.putBoolean("RenderDeveloperGlow", this.shouldRenderDeveloperGlow());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("HatGloves")) {
            this.setSleeveGloves(tag.getBoolean("HatGloves"));
        }
        if (tag.contains("RenderHalo")) {
            this.setRenderHalo(tag.getBoolean("RenderHalo"));
        }
        if (tag.contains("RenderDeveloperGlow")) {
            this.setRenderDeveloperGlow(tag.getBoolean("RenderDeveloperGlow"));
        }
    }

    @Override
    public CompoundTag serializeSynchableNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("HatGloves_Syncing", this.areSleeveGloves());
        tag.putBoolean("RenderHalo_Syncing", this.shouldRenderHalo());
        tag.putBoolean("RenderDeveloperGlow_Syncing", this.shouldRenderDeveloperGlow());
        return tag;
    }

    @Override
    public void deserializeSynchableNBT(CompoundTag tag) {
        if (tag.contains("HatGloves_Syncing")) {
            this.setSleeveGloves(tag.getBoolean("HatGloves_Syncing"));
        }
        if (tag.contains("RenderHalo_Syncing")) {
            this.setRenderHalo(tag.getBoolean("RenderHalo_Syncing"));
        }
        if (tag.contains("RenderDeveloperGlow_Syncing")) {
            this.setRenderDeveloperGlow(tag.getBoolean("RenderDeveloperGlow_Syncing"));
        }
    }

    @Override
    public void copyFrom(AetherRankings other) {
        this.setSleeveGloves(other.areSleeveGloves());
        this.setRenderHalo(other.shouldRenderHalo());
        this.setRenderDeveloperGlow(other.shouldRenderDeveloperGlow());
    }

    @Override
    public void onUpdate() {
        this.updateSyncableNBT(this.getPlayer().getLevel());
    }

    @Override
    public void setSleeveGloves(boolean areHatGloves) {
        this.markDirty(true);
        this.sleeveGloves = areHatGloves;
    }

    @Override
    public boolean areSleeveGloves() {
        return this.sleeveGloves;
    }

    @Override
    public void setRenderHalo(boolean renderHalo) {
        this.markDirty(true);
        this.renderHalo = renderHalo;
    }

    @Override
    public boolean shouldRenderHalo() {
        return this.renderHalo;
    }

    @Override
    public void setRenderDeveloperGlow(boolean renderDeveloperGlow) {
        this.markDirty(true);
        this.renderDeveloperGlow = renderDeveloperGlow;
    }

    @Override
    public boolean shouldRenderDeveloperGlow() {
        return this.renderDeveloperGlow;
    }

    @Override
    public AetherPacket.AbstractAetherPacket getSyncPacket(CompoundTag tag) {
        return new AetherRankingsSyncPacket(this.getPlayer().getId(), tag);
    }
}
